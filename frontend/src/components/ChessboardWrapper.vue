<template>
  <div>
    <div class="chessBoardStyle" id="chessboard" style="width: 400px"></div>
    <textarea
      class="textAreaStyle"
      id="w3review"
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

    reverseMove(move) {
      const fromTo = move.split("-");
      return fromTo[1] + "-" + fromTo[0];
    },
  },
  data() {
    return {
      board: null,
      movesReader: null,
      moves: ["e4", "d5", "exd5", "Nc6", "dxc6"],
      index: 0,
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