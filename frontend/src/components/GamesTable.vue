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
        @click="onGameClick(game.id)"
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
    <chessboard-wrapper ref="chessboardWrapper" />
    <games-statistics v-bind:games="this.displayedGames" />
  </div>
</template>


<script>
import ChessboardWrapper from "./ChessboardWrapper.vue";
import GamesStatistics from "./GamesStatistics.vue";
import DatabaseLoader from "./DatabaseLoader.vue";
import axios from "axios";

export default {
  name: "GamesTable",
  components: {
    ChessboardWrapper,
    GamesStatistics,
    DatabaseLoader,
  },
  methods: {
    addGames(gamesFromDb) {
      for (let i = 0; i < gamesFromDb.length; i++) {
        this.games.push(gamesFromDb[i]);
      }
      this.filterGames();
    },

    onGameClick(id) {
      axios({
        url: "http://localhost:8080/api/getChessGame",
        method: "GET",
        params: {
          ID: id,
        },
      }).then((response) => {
        this.$refs.chessboardWrapper.setupBoard(response.data);
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
    shouldDisplayGame(game) {
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
</style>