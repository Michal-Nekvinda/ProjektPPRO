<template>
  <div>
    <div class="chessBoardStyle" id="chessboard" style="width: 400px"></div>
    <textarea
      class="textAreaStyle"
      name="chessGameView"
      rows="20"
      cols="50"
      readonly
      v-model="loadedGame"
    >
    </textarea>
    <div class="controlPanel">
      <button class="buttonStyle" @click="newGame">Nová hra</button>
      <button
        class="buttonStyle"
        @click="moveBackward"
        v-if="this.loadedGame != ''"
      >
        Předchozí
      </button>
      <button
        class="buttonStyle"
        @click="moveForward"
        v-if="this.loadedGame != ''"
      >
        Další
      </button>

      <button
        class="buttonStyle"
        @click="addComment"
        v-if="this.loadedGame != ''"
      >
        Přidat komentář k tahu
      </button>
    </div>
    <div class="form-popup" id="myForm" v-if="showNewGameForm">
      <form class="form-container">
        <h1>Login</h1>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" required />

        <label for="psw"><b>Password</b></label>
        <input
          type="password"
          placeholder="Enter Password"
          name="psw"
          required
        />

        <button type="submit" class="btn">Login</button>
        <button type="button" class="btn cancel" @click="onCloseForm()">
          Close
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import ChessBoard from "chessboardjs-vue";
import { Chess } from "chess.js";

export default {
  name: "ChessboardWrapper",
  mounted() {
    this.board = ChessBoard("chessboard", "start");
  },
  methods: {
    moveForward() {
      if (this.currentMoveIndex >= this.movesReader.history().length) {
        return;
      }
      const move = this.movesReader.history()[this.currentMoveIndex];
      this.stateInBoard.move(move);
      this.board.position(this.stateInBoard.fen());
      this.currentMoveIndex++;
    },
    moveBackward() {
      if (this.currentMoveIndex <= 0) {
        return;
      }
      this.currentMoveIndex--;
      this.stateInBoard.undo();
      this.board.position(this.stateInBoard.fen());
    },

    newGame() {
      var config = {
        draggable: true,
        dropOffBoard: "snapback",
        position: "start",
        onDragStart: this.onDragStart,
        onDrop: this.onDrop,
        onSnapEnd: this.onSnapEnd,
      };
      this.setupBoard(config, "");
      this.showNewGameForm = true;
    },
    addComment() {},

    loadGame(chosenGame) {
      var config = {
        position: "start",
      };
      this.setupBoard(config, chosenGame);
      this.movesReader.load_pgn(this.loadedGame);
    },

    setupBoard(boardConfig, chosenGame) {
      this.movesReader = new Chess();
      this.board = ChessBoard("chessboard", boardConfig);
      this.stateInBoard = new Chess();
      this.currentMoveIndex = 0;
      this.loadedGame = chosenGame;
    },
    onCloseForm() {
      this.showNewGameForm = false;
    },

    //metody pro ovladani sachovnice
    onDragStart() {
      if (this.movesReader.game_over()) return false;
    },

    onDrop(source, target) {
      var move = this.movesReader.move({
        from: source,
        to: target,
        promotion: "q",
      });

      // illegal move
      if (move === null) {
        return "snapback";
      }
      this.stateInBoard.move(move);
      this.movesReader = this.stateInBoard;
    },

    // update the board position after the piece snap for castling, en passant, pawn promotion
    onSnapEnd() {
      this.board.position(this.movesReader.fen());
      this.loadedGame = this.movesReader.pgn();
      this.currentMoveIndex++;
    },
  },

  data() {
    return {
      board: null,
      movesReader: null,
      currentMoveIndex: 0,
      moveComment: "",
      loadedGame: "",
      stateInBoard: null,
      showNewGameForm: false,
    };
  },
};
</script>

<style>
.controlPanel {
  text-align: left;
  margin: 0px 70px;
}
.buttonStyle {
  display: inline-block;
  cursor: pointer;
  margin: 10px;
}
.chessBoardStyle {
  display: inline-block;
}
.textAreaStyle {
  display: inline-block;
  margin: 30px;
  resize: none;
}
</style>