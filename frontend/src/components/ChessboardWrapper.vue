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
      <button class="buttonStyle" @click="moveBackward">Předchozí</button>
      <button class="buttonStyle" @click="moveForward">Další</button>
      <button class="buttonStyle" @click="newGame">Nová hra</button>
      <button class="buttonStyle" @click="addComment">
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
import { Chess } from "cm-chess";

export default {
  name: "ChessboardWrapper",
  mounted() {
    this.board = ChessBoard("chessboard", "start");
  },

  methods: {
    moveForward() {
      console.log();
      if (this.currentMoveIndex >= this.movesReader.history().length) {
        return;
      }
      const move = this.movesReader.history()[this.currentMoveIndex].san;
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
      this.movesReader = new Chess({ pgn: this.loadedGame });
      this.stateInBoard = new Chess();
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
}
.buttonStyle {
  display: inline-block;
  cursor: pointer;
  margin: 10px 10px;
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