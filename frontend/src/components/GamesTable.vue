<template>
  <div>
    <database-loader />
    <div class="filterPanel">
      <span class="tableSearchFilters"> Najít: </span>
      <input
        type="text"
        placeholder="Jméno..."
        class="tableSearchFilters"
        v-model="nameSearch"
        v-on:keyup="filterGames"
      />

      <input
        type="text"
        placeholder="Turnaj..."
        class="tableSearchFilters"
        v-model="tournamentSearch"
        v-on:keyup="filterGames"
      />
      <input
        type="text"
        placeholder="Zahájení..."
        class="tableSearchFilters"
        v-model="openingSearch"
        v-on:keyup="filterGames"
      />
    </div>

    <table>
      <tr id="row-1">
        <th>Bílý</th>
        <th>Elo</th>
        <th>Černý</th>
        <th>Elo</th>
        <th>Výsledek</th>
        <th>Zahájení</th>
        <th>Turnaj</th>
      </tr>

      <tr
        class="dataRow"
        @click="onGameClick(game)"
        v-for="game in displayedGames"
        :key="game.id"
      >
        <td>{{ game.whiteName }}</td>
        <td>{{ game.whiteElo }}</td>
        <td>{{ game.blackName }}</td>
        <td>{{ game.blackElo }}</td>
        <td>{{ game.result }}</td>
        <td>{{ game.eco }}</td>
        <td>{{ game.tournament }}</td>
      </tr>
    </table>
    <div>
      <button class="buttonStyle" @click="onDelete">
        Smazat vybrané partie
      </button>
    </div>
    <chessboard-wrapper ref="chessboardWrapper" />
    <games-statistics v-bind:games="this.displayedGames" />
  </div>
</template>


<script lang="ts">
import ChessboardWrapper from "./ChessboardWrapper.vue";
import GamesStatistics from "./GamesStatistics.vue";
import DatabaseLoader from "./DatabaseLoader.vue";
import axios from "axios";
import { ChessGame } from "../api/backendApi";

export default {
  name: "GamesTable",
  components: {
    ChessboardWrapper,
    GamesStatistics,
    DatabaseLoader,
  },
  methods: {
    addGames(gamesFromDb: ChessGame[]) {
      for (let i = 0; i < gamesFromDb.length; i++) {
        this.games.push(gamesFromDb[i]);
      }
      this.filterGames();
    },

    onGameClick(game: ChessGame) {
      axios({
        url: "http://localhost:8080/api/getChessGame",
        method: "GET",
        params: {
          ID: game.id,
        },
      }).then((response) => {
        this.$refs.chessboardWrapper.loadGame(response.data, game);
      });
    },

    onDelete() {
      var ids = [];
      for (let i = 0; i < this.displayedGames.length; i++) {
        ids.push(this.displayedGames[i].id);
      }
      axios({
        url: "http://localhost:8080/api/deleteGames",
        method: "POST",
        data: ids,
      }).then(() => {
        this.reloadGames();
      });
    },

    reloadGames() {
      axios({
        url: "http://localhost:8080/api/getChessGames",
        method: "GET",
      }).then((response) => {
        this.games = [];
        this.addGames(response.data);
      });
    },

    filterGames() {
      var newDisplayedGames = [];
      for (let i = 0; i < this.games.length; i++) {
        const game = this.games[i];

        if (this.shouldDisplayGame(game)) {
          newDisplayedGames.push(game);
        }
      }

      this.displayedGames = newDisplayedGames;
    },

    shouldDisplayGame(game: ChessGame) {
      if (
        game.tournament == null ||
        !game.tournament
          .toLowerCase()
          .includes(this.tournamentSearch.toLowerCase())
      ) {
        return false;
      }
      if (
        game.eco == null ||
        !game.eco.toLowerCase().includes(this.openingSearch.toLowerCase())
      ) {
        return false;
      }
      if (
        !game.whiteName.toLowerCase().includes(this.nameSearch.toLowerCase()) &&
        !game.blackName.toLowerCase().includes(this.nameSearch.toLowerCase())
      ) {
        return false;
      }
      return true;
    },
  },
  mounted() {
    this.reloadGames();
  },

  data() {
    return {
      nameSearch: "",
      tournamentSearch: "",
      openingSearch: "",
      ignoreColours: false,
      chosenGame: "",
      games: [],
      displayedGames: [],
    };
  },
};
</script>

<style>
table,
td,
th {
  border: 1px solid darkgray;
  text-align: left;
  padding: 5px;
  font-size: 12px;
  margin: 5px;
}
.tableSearchFilters {
  margin: 10px 10px;
}
.filterPanel {
  margin: 5px, 5px;
  text-align: left;
}
table {
  margin: 0px 0px 20px 10px;
  border-collapse: collapse;
  width: 70%;
}
.dataRow {
  cursor: pointer;
}
.dataRow:hover {
  background-color: lightgray;
}
.buttonStyle {
  display: inline-block;
  cursor: pointer;
  margin: 10px;
}
</style>