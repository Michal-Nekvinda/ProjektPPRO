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
    <div class="formPopup" id="popupForm">
      <textarea name="comment" rows="10" cols="20" v-model="moveComment">
      </textarea>
      <button class="btn">Vložit</button>
      <button type="button" class="btn" onclick="closeForm()">Zavřít</button>
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
      this.board = ChessBoard("chessboard", config);
      this.currentMoveIndex = 0;
      this.movesReader = new Chess();
      this.currentMoveIndex = 0;
      this.loadedGame = "";
    },
    addComment() {},

    setupBoard(chosenGame) {
      this.loadedGame = chosenGame;
      this.board = ChessBoard("chessboard", "start");
      this.movesReader = new Chess();
      this.movesReader.load_pgn(this.loadedGame);
      this.stateInBoard = new Chess();
    },

    onDragStart(piece) {
      if (this.movesReader.game_over()) return false;

      if (
        (this.movesReader.turn() === "w" && piece.search(/^b/) !== -1) ||
        (this.movesReader.turn() === "b" && piece.search(/^w/) !== -1)
      ) {
        return false;
      }
    },

    onDrop(source, target) {
      var move = this.movesReader.move({
        from: source,
        to: target,
        promotion: "q",
      });

      // illegal move
      if (move === null) return "snapback";
    },

    // update the board position after the piece snap
    // for castling, en passant, pawn promotion
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