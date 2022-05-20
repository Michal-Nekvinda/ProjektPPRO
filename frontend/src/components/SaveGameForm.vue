<template>
  <div class="modalOverlay">
    <div class="modalSaveGame">
      <h4 class="center">Uložit partii</h4>
      <h5>Povinné položky</h5>
      <div>
        <label class="label">Bílý:</label>
        <input
          type="text"
          placeholder="Příjmení, jméno..."
          v-model="createdGame.whiteName"
        />
      </div>
      <div>
        <label class="label">Černý:</label>
        <input
          type="text"
          placeholder="Příjmení, jméno..."
          v-model="createdGame.blackName"
        />
      </div>
      <div>
        <label class="label">Výsledek:</label>
        <select v-model="createdGame.result">
          <option value="1-0">1-0</option>
          <option value="1/2-1/2">1/2-1/2</option>
          <option value="0-1">0-1</option>
        </select>
      </div>
      <div>
        <label class="labelError">{{ this.errorMessage }}</label>
      </div>
      <h5>Nepovinné položky</h5>
      <div>
        <label class="label">Zahájení:</label>
        <input type="text" placeholder="Eco kód..." v-model="createdGame.eco" />
      </div>
      <div>
        <label class="label">Elo bílý:</label>
        <input
          type="text"
          placeholder="Elo..."
          v-model="createdGame.whiteElo"
        />
      </div>
      <div>
        <label class="label">Elo černý:</label>
        <input
          type="text"
          placeholder="Elo..."
          v-model="createdGame.blackElo"
        />
      </div>
      <div>
        <label class="label">Turnaj:</label>
        <input
          type="text"
          placeholder="Název..."
          v-model="createdGame.tournament"
        />
      </div>
      <div>
        <label class="label">Datum:</label>
        <input
          type="text"
          placeholder="DD.MM.YYYY..."
          v-model="createdGame.date"
        />
      </div>
      <div class="center">
        <button class="button" @click="onCloseModal(true)">Vytvořit</button>
        <button class="button" @click="onCloseModal(false)">Zrušit</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ChessGame, PgnTag } from "@/api/backendApi";
import { Chess } from "chess.js";
import axios from "axios";
export default {
  name: "SaveGameForm",
  props: {
    rawGameData: String,
  },
  data() {
    return {
      createdGame: new ChessGame(),
      errorMessage: "",
    };
  },
  methods: {
    clearError() {
      this.errorMessage = "";
    },
    onCloseModal(save: boolean) {
      if (!save) {
        this.clearError();
        this.$parent.onCloseModalSaveGame(undefined);
        return;
      }
      const pgn: string = this.createPgn(this.createdGame);
      this.createdGame = new ChessGame();

      axios({
        url: "/api/saveNewGame",
        method: "POST",
        data: pgn,
        headers: {
          "Content-Type": "text/plain",
        },
      }).then((response) => {
        const gameInResponse: ChessGame = response.data;

        if (gameInResponse.errorMessage) {
          this.errorMessage = gameInResponse.errorMessage;
        } else {
          this.clearError();
          this.$parent.onCloseModalSaveGame(gameInResponse);
        }
      });
    },
    createPgn(createdGame: ChessGame): string {
      let chess = new Chess();
      chess.load_pgn(this.rawGameData);
      chess.header(PgnTag.WHITE_NAME, createdGame.whiteName);
      chess.header(PgnTag.BLACK_NAME, createdGame.blackName);
      chess.header(PgnTag.WHITE_ELO, createdGame.whiteElo);
      chess.header(PgnTag.BLACK_ELO, createdGame.blackElo);
      chess.header(PgnTag.RESULT, createdGame.result);
      chess.header(PgnTag.TOURNAMENT, createdGame.tournament);
      chess.header(PgnTag.ECO, createdGame.eco);
      chess.header(PgnTag.DATE, createdGame.date);

      return chess.pgn();
    },
  },
};
</script>

<style>
.modalSaveGame {
  text-align: left;
  background-color: white;
  height: 500px;
  width: 600px;
  margin-top: 10%;
  padding: 10px 10px 10px 10px;
  margin-right: 50px;
  display: inline-block;
}
.label {
  margin-right: 20px;
  margin-bottom: 10px;
  width: 100px;
  display: inline-block;
  padding: 2px, 2px, 2px, 2px;
}
.center {
  text-align: center;
}
.labelError {
  margin-right: 20px;
  margin-bottom: 10px;
  margin-top: 20px;
  color: red;
  font-weight: bold;
}
</style>