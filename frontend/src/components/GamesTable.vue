<template>
  <div>
    <database-loader />
    <div class="marginTop">
      <label class="labelBold">Seznam partií:</label>
    </div>
    <div>
      <label>Najít:</label>
      <input
        type="text"
        placeholder="Jméno..."
        class="input"
        v-model="nameSearch"
        v-on:keyup="filterGames"
      />

      <input
        type="text"
        placeholder="Turnaj..."
        class="input"
        v-model="tournamentSearch"
        v-on:keyup="filterGames"
      />
      <input
        type="text"
        placeholder="Zahájení..."
        class="input"
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
        v-for="game in gamesOnPage"
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
    <div v-show="displayedGames.length > gamesPerPage">
      <paginate
        v-model="pageSelected"
        :page-count="pageCount"
        :page-range="pageCount"
        :prev-text="'Předchozí'"
        :next-text="'Další'"
        :container-class="'pagination'"
        :page-class="'page-item'"
        :prev-class="'page-item'"
        :next-class="'page-item'"
        :click-handler="onPageClick"
      >
      </paginate>
    </div>
    <div>
      <button @click="onDelete">Smazat vybrané partie</button>
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
import Vue from "vue";
import Paginate from "vuejs-paginate";

export default Vue.extend({
  name: "GamesTable",
  components: {
    ChessboardWrapper,
    GamesStatistics,
    DatabaseLoader,
    Paginate,
  },
  methods: {
    addGames(gamesFromDb: ChessGame[]) {
      gamesFromDb.forEach((game: ChessGame) => {
        this.games.push(game);
      });
      this.filterGames();
    },

    onGameClick(game: ChessGame) {
      axios({
        url: "/api/getGame",
        method: "GET",
        params: {
          ID: game.id,
        },
      }).then((response) => {
        this.$refs.chessboardWrapper.loadGame(response.data, game);
      });
    },

    onDelete() {
      let ids = [];
      this.displayedGames.forEach((game: ChessGame) => {
        ids.push(game.id);
      });

      axios({
        url: "/api/deleteGames",
        method: "POST",
        data: ids,
      }).then(() => {
        this.reloadGames();
      });
    },

    reloadGames() {
      axios({
        url: "/api/getGames",
        method: "GET",
      }).then((response) => {
        this.games = [];
        this.addGames(response.data);
      });
    },

    filterGames() {
      let newDisplayedGames = [];

      this.games.forEach((game: ChessGame) => {
        if (this.shouldDisplayGame(game)) {
          newDisplayedGames.push(game);
        }
      });
      this.displayedGames = newDisplayedGames;
      this.pageCount = Math.ceil(
        this.displayedGames.length / this.gamesPerPage
      );
      this.pageSelected = 1;
      this.gamesOnPage = this.displayedGames.slice(0, this.gamesPerPage);
    },

    shouldDisplayGame(game: ChessGame): boolean {
      return (
        this.containsName(game) &&
        this.containsEco(game) &&
        this.containsTournament(game)
      );
    },

    containsTournament(game: ChessGame): boolean {
      if (!this.tournamentSearch) {
        return true;
      }
      if (
        game.tournament &&
        game.tournament
          .toLowerCase()
          .includes(this.tournamentSearch.toLowerCase())
      ) {
        return true;
      }
      return false;
    },
    containsEco(game: ChessGame): boolean {
      if (!this.openingSearch) {
        return true;
      }
      if (
        game.eco &&
        game.eco.toLowerCase().includes(this.openingSearch.toLowerCase())
      ) {
        return true;
      }
      return false;
    },
    containsName(game: ChessGame): boolean {
      if (
        !game.whiteName.toLowerCase().includes(this.nameSearch.toLowerCase()) &&
        !game.blackName.toLowerCase().includes(this.nameSearch.toLowerCase())
      ) {
        return false;
      }
      return true;
    },

    onPageClick(pageNum: number) {
      this.pageSelected = pageNum;
      this.gamesOnPage = this.displayedGames.slice(
        (pageNum - 1) * this.gamesPerPage,
        pageNum * this.gamesPerPage
      );
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
      games: [] as ChessGame[],
      displayedGames: [] as ChessGame[],
      gamesOnPage: [] as ChessGame[],
      pageCount: 10,
      gamesPerPage: 25,
      pageSelected: 1,
    };
  },
});
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
.input {
  margin: 10px 10px;
  width: 200px;
}
.labelBold {
  font-weight: bold;
}
table {
  margin: 0px 0px 10px;
  border-collapse: collapse;
  width: 70%;
}
.dataRow {
  cursor: pointer;
}
.dataRow:hover {
  background-color: lightgray;
}
.marginTop {
  margin-top: 20px;
}
.pagination {
  display: inline-block;
  margin-left: -40px;
  margin-top: -7px;
}
.page-item {
  list-style: none;
  color: black;
  float: left;
  padding: 5px 10px;
  text-decoration: none;
  transition: background-color 0.3s;
  border: 1px solid lightgrey;
}
.page-item:hover:not(.active) {
  background-color: lightgrey;
}
.active {
  background-color: lightblue;
}
</style>