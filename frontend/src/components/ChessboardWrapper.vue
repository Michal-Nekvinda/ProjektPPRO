<template>
  <div>
    <div v-if="this.chosenGameHeader.whiteName">
      <h3>{{ this.createHeader() }}</h3>
    </div>
    <div v-if="this.currentMoveIndex > 0">
      <h4>{{ this.showLastMove() }}</h4>
    </div>
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
    <comment-form v-show="showModalComment" />
    <save-game-form v-show="showModalNewGame" />
  </div>
</template>

<script lang="ts">
import ChessBoard from "chessboardjs-vue";
import { Chess } from "chess.js";
import { ChessGame } from "@/api/backendApi";
import SaveGameForm from "./SaveGameForm.vue";
import CommentForm from "./CommentForm.vue";

export default {
  components: { SaveGameForm, CommentForm },
  name: "ChessboardWrapper",
  mounted() {
    this.board = ChessBoard("chessboard", "start");
  },
  methods: {
    showLastMove(): string {
      if (this.lastMove) {
        const moveNumber: number = Math.floor((this.currentMoveIndex + 1) / 2);
        const modulo = (this.currentMoveIndex + 1) % 2;
        return moveNumber + "." + (modulo === 1 ? "... " : " ") + this.lastMove;
      }
      return "";
    },
    createHeader(): string {
      //if in state new game, then return header with Nova hra or something
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
      this.lastMove = this.movesReader.history()[this.currentMoveIndex];
      this.stateInBoard.move(this.lastMove);
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
      this.lastMove = this.stateInBoard.history()[this.currentMoveIndex - 1];
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
      this.chosenGameHeader = "Nová hra";
      //spis az na save
      this.showModalNewGame = true;
    },
    onCloseModalComment(comment: string, save: boolean) {
      if (comment && save) {
        this.movesReader.set_comment(comment);
      }
      this.showModalComment = false;
      this.loadedGame = this.movesReader.pgn();
    },
    addComment() {
      this.showModalComment = true;
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
    onCloseModalNewGame(createdGame: ChessGame | undefined) {
      this.showModalNewGame = false;
      if (createdGame) {
        console.log("a");
      }
      console.log("b");
    },

    //metody pro ovladani sachovnice
    onDragStart() {
      if (this.movesReader.game_over()) return false;
    },

    onDrop(source: any, target: any) {
      const move = this.movesReader.move({
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
      this.lastMove = this.stateInBoard.history()[this.currentMoveIndex - 1];
    },
  },

  data() {
    return {
      board: null,
      movesReader: null,
      currentMoveIndex: 0,
      loadedGame: "",
      stateInBoard: null,
      showModalNewGame: false,
      chosenGameHeader: new ChessGame(),
      lastMove: "",
      showModalComment: false,
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