<template>
  <div>
    <div v-if="this.chosenGameHeader.whiteName">
      <h3>{{ this.createHeader() }}</h3>
    </div>
    <div class="chessBoardStyle" id="chessboard" style="width: 400px"></div>
    <h3 class="textAreaStyle">{{ this.showLastMove() }}</h3>
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
    <div class="modal-overlay" v-show="showModalNewGame">
      <div class="modal">
        <h6>Saved!</h6>
        <p>Your Details have been saved Successfully</p>
        <button @click="onCloseModalNewGame">Go Home</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import ChessBoard from "chessboardjs-vue";
import { Chess } from "chess.js";
import { ChessGame } from "@/api/backendApi";

export default {
  name: "ChessboardWrapper",
  mounted() {
    this.board = ChessBoard("chessboard", "start");
  },
  methods: {
    createHeader(): string {
      const g: ChessGame = this.chosenGameHeader;
      const whiteElo = g.whiteElo ? "(" + g.whiteElo + ")" : "";
      const blackElo = g.blackElo ? "(" + g.blackElo + ")" : "";
      return (
        g.whiteName +
        " " +
        whiteElo +
        " - " +
        g.blackName +
        " " +
        blackElo +
        " " +
        g.result
      );
    },
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
      this.showModalNewGame = true;
    },
    addComment() {},
    showLastMove(): string {
      if (this.stateInBoard && this.stateInBoard.history()) {
        return this.stateInBoard.history()[
          this.stateInBoard.history().length - 1
        ];
      }
      return "";
    },
    loadGame(chosenGamePgn: string, chosenGameHeader: ChessGame) {
      const config = {
        position: "start",
      };
      this.setupBoard(config, chosenGamePgn);
      this.movesReader.load_pgn(this.loadedGame);
      this.chosenGameHeader = chosenGameHeader;
    },

    setupBoard(boardConfig: any, chosenGamePgn: string) {
      this.movesReader = new Chess();
      this.board = ChessBoard("chessboard", boardConfig);
      this.stateInBoard = new Chess();
      this.currentMoveIndex = 0;
      this.loadedGame = chosenGamePgn;
    },
    onCloseModalNewGame() {
      this.showModalNewGame = false;
    },

    //metody pro ovladani sachovnice
    onDragStart() {
      if (this.movesReader.game_over()) return false;
    },

    onDrop(source: any, target: any) {
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
      showModalNewGame: false,
      chosenGameHeader: new ChessGame(),
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
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: #000000da;
}

.modal {
  text-align: center;
  background-color: white;
  height: 500px;
  width: 500px;
  margin-top: 10%;
  padding: 60px 0;
  border-radius: 20px;
}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}
</style>