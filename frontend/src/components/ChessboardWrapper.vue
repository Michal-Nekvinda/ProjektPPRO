<template>
  <div class="left">
    <label class="labelBold">{{ this.createHeader() }}</label>
    <div class="labelFixed">
      <label v-show="this.currentMoveIndex > 0">{{
        this.createLastMoveLabel()
      }}</label>
    </div>
    <div class="left">
      <div class="chessBoardStyle" id="chessboard" style="width: 400px"></div>
      <textarea
        class="textArea"
        name="chessGameView"
        rows="26"
        cols="50"
        readonly
        v-model="loadedGame"
      >
      </textarea>
    </div>
    <div class="controlPanel">
      <button class="button" @click="newGame">Nová hra</button>
      <button class="button" @click="moveBackward" v-show="this.loadedGame">
        Předchozí
      </button>
      <button
        class="button"
        @click="moveForward"
        v-show="this.loadedGame && notInCustom()"
      >
        Další
      </button>

      <button
        class="button"
        @click="addComment"
        v-show="this.showModalButtons && this.currentMoveIndex > 0"
      >
        Přidat komentář k tahu
      </button>
      <button
        class="button"
        @click="saveGame"
        v-show="this.showModalButtons && this.currentMoveIndex > 0"
      >
        Uložit
      </button>
    </div>
    <comment-form v-show="showModalComment" />
    <save-game-form
      v-bind:rawGameData="this.loadedGame"
      v-show="showModalSaveGame"
    />
  </div>
</template>

<script lang="ts">
import ChessBoard from "chessboardjs-vue";
import { Chess } from "chess.js";
import { ChessGame } from "@/api/backendApi";
import SaveGameForm from "./SaveGameForm.vue";
import CommentForm from "./CommentForm.vue";
import Vue from "vue";

export default Vue.extend({
  components: { SaveGameForm, CommentForm },
  name: "ChessboardWrapper",
  mounted() {
    this.changeState(States.Start);
  },
  methods: {
    createLastMoveLabel(): string {
      if (!this.lastMove) {
        return "";
      }
      const moveNumber = Math.floor((this.currentMoveIndex + 1) / 2);
      const modulo = (this.currentMoveIndex + 1) % 2;
      return (
        "Poslední tah: " +
        moveNumber +
        "." +
        (modulo === 1 ? "... " : " ") +
        this.lastMove
      );
    },
    notInCustom(): boolean {
      return this.actualState !== States.InCustomGame;
    },
    createHeader(): string {
      if (this.actualState === States.Start) {
        return "";
      }
      if (this.actualState === States.InCustomGame) {
        return "Nová hra ? - ? ";
      }

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

    saveGame() {
      this.showModalSaveGame = true;
    },
    addComment() {
      this.showModalComment = true;
    },

    onCloseModalComment(comment: string, save: boolean) {
      if (comment && save) {
        this.movesReader.set_comment(comment);
      }
      this.showModalComment = false;
      this.loadedGame = this.movesReader.pgn();
    },
    onCloseModalSaveGame(createdGame: ChessGame | undefined) {
      this.showModalSaveGame = false;
      if (createdGame) {
        this.$parent.addGames([createdGame]);
        this.changeState(States.Start);
      }
    },

    loadGame(chosenGamePgn: string, chosenGameHeader: ChessGame) {
      const config = {
        position: "start",
      };
      this.setupBoard(config, chosenGamePgn);
      this.movesReader.load_pgn(this.loadedGame);
      this.chosenGameHeader = chosenGameHeader;
      this.changeState(States.InDbGame);
    },
    newGame() {
      const config = {
        draggable: true,
        dropOffBoard: "snapback",
        position: "start",
        onDragStart: this.onDragStart,
        onDrop: this.onDrop,
        onSnapEnd: this.onSnapEnd,
      };
      this.setupBoard(config, "");
      this.changeState(States.InCustomGame);
    },

    setupBoard(boardConfig: any, chosenGamePgn: string) {
      this.movesReader = new Chess();
      this.board = ChessBoard("chessboard", boardConfig);
      this.stateInBoard = new Chess();
      this.currentMoveIndex = 0;
      this.loadedGame = chosenGamePgn;
    },

    changeState(newState: number) {
      this.actualState = newState;
      switch (newState) {
        case States.Start:
          this.showHeader = false;
          this.showModalButtons = false;
          this.board = ChessBoard("chessboard", "start");
          this.loadedGame = "";
          this.currentMoveIndex = 0;
          break;
        case States.InCustomGame:
          this.showHeader = true;
          this.showModalButtons = true;
          break;
        case States.InDbGame:
          this.showHeader = true;
          this.showModalButtons = false;
          break;
      }
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

      if (move === null) {
        return "snapback";
      }
      this.stateInBoard.move(move);
      this.movesReader = this.stateInBoard;
    },
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
      showModalSaveGame: false,
      chosenGameHeader: new ChessGame(),
      lastMove: "",
      showModalComment: false,
      showModalButtons: false,
      actualState: States.Start,
      showHeader: false,
    };
  },
});

export const States = {
  Start: 0,
  InCustomGame: 1,
  InDbGame: 2,
};
</script>

<style>
.controlPanel {
  text-align: left;
}
.chessBoardStyle {
  display: inline-block;
  text-align: left;
}
.textArea {
  display: inline-block;
  margin-left: 5px;
  resize: none;
}
.labelFixed {
  text-align: left;
  margin-left: 10px;
  height: 10px;
}
.left2 {
  text-align: left;
}
</style>