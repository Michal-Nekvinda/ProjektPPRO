<template>
  <div>
    <database-loader />
    Najít
    <input
      type="text"
      placeholder="Jméno..."
      class="table-search-filters"
      v-model="nameSearch"
      v-on:keyup="filterGames"
    />
    <input type="checkbox" v-model="ignoreColours" v-on:change="filterGames" />
    Ignorovat barvu figur <br />

    <input
      type="text"
      placeholder="Turnaj..."
      class="table-search-filters"
      v-model="tournamentSearch"
      v-on:keyup="filterGames"
    />
    <input
      type="text"
      placeholder="Zahájení..."
      class="table-search-filters"
      v-model="openingSearch"
      v-on:keyup="filterGames"
    />
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
        @click="
          onGameClick(game.id);
          $refs.chessboardWrapper.setupBoard();
        "
        v-for="game in displayedGames"
        :key="game.id"
      >
        <td>{{ game.whiteName }}</td>
        <td>{{ game.whiteElo }}</td>
        <td>{{ game.blackName }}</td>
        <td>{{ game.blackElo }}</td>
        <td>{{ game.result }}</td>
        <td>{{ game.eco }}</td>
        <td>{{ game.tournamentName }}</td>
      </tr>
    </table>
    <chessboard-wrapper
      v-bind:chess-game="this.chosenGame"
      ref="chessboardWrapper"
    />
    <games-statistics v-bind:games="this.displayedGames" />
  </div>
</template>


<script>
import ChessboardWrapper from "./ChessboardWrapper.vue";
import GamesStatistics from "./GamesStatistics.vue";
import DatabaseLoader from "./DatabaseLoader.vue";

export default {
  name: "GamesTable",
  props: {},
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
      console.log("game" + id + " chosen");
      this.chosenGame = this.games[id - 1].pgnFile;
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
        game.tournamentName == null ||
        !game.tournamentName
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

table {
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