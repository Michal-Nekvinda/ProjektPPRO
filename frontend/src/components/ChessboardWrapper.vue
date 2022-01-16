<template>
  <div>
    <div class="chessBoardStyle" id="chessboard" style="width: 400px"></div>
    <textarea
      class="textAreaStyle"
      name="chessGameView"
      rows="20"
      cols="50"
      readonly
      v-model="chessGame"
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
    this.movesReader = new Chess();
  },
  props: {
    chessGame: String,
  },

  methods: {
    moveForward() {
      this.movesReader.move(this.moves[this.index]);
      this.board.position(this.movesReader.fen());
      this.index++;
    },
    moveBackward() {
      this.index--;
      this.movesReader.undo();
      this.board.position(this.movesReader.fen());
    },

    newGame() {
      var config = {
        draggable: true,
        dropOffBoard: "snapback", // this is the default
        position: "start",
      };
      this.board = ChessBoard("chessboard", config);
    },
    addComment() {},
    setupBoard() {
      this.board = ChessBoard("chessboard", "start");
    },
  },
  data() {
    return {
      board: null,
      movesReader: null,
      moves: ["e4", "d5", "exd5", "Nc6", "dxc6"],
      index: 0,
      moveComment: "",
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