<template>
  <div>
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
        <th>Tahy</th>
        <th>Zahájení</th>
        <th>Turnaj</th>
        <th>Datum</th>
      </tr>

      <tr
        class="dataRow"
        @click="onGameClick(game.id)"
        v-for="game in games"
        :key="game.id"
      >
        <td>{{ game.white }}</td>
        <td>{{ game.whiteElo }}</td>
        <td>{{ game.black }}</td>
        <td>{{ game.blackElo }}</td>
        <td>{{ game.result }}</td>
        <td>{{ game.moves }}</td>
        <td>{{ game.opening }}</td>
        <td>{{ game.tournament }}</td>
        <td>{{ game.date }}</td>
      </tr>
    </table>
    <chessboard-wrapper v-bind:chess-game="this.chosenGame" />
  </div>
</template>


<script>
import ChessboardWrapper from "./ChessboardWrapper.vue";

export default {
  name: "GamesTable",
  components: {
    ChessboardWrapper,
  },
  methods: {
    onGameClick(id) {
      console.log("game" + id + " chosen");
      this.chosenGame = this.games[id].pgnFile;
    },
    filterGames() {
      console.log("filtering games");
    },
  },
  data() {
    return {
      nameSearch: "",
      tournamentSearch: "",
      openingSearch: "",
      ignoreColours: false,
      chosenGame: "",
      games: [
        {
          id: 1,
          white: "Ja",
          whiteElo: "1600",
          black: "John Smith",
          blackElo: "1545",
          result: "1-0",
          moves: "50",
          opening: "",
          tournament: "krajsky prebor zakovskych druzstev Hradec Kralove",
          date: "12.5.2020",
          pgnFile: "e4, e5, Jf3, Jc6",
        },
        {
          id: 2,
          white: "Ja",
          whiteElo: "1700",
          black: "Jack",
          blackElo: "1750",
          result: "0,5-0,5",
          moves: "60",
          opening: "C40",
          tournament: "krajsky prebor",
          date: "12.5.2020",
          pgnFile: "Se4, e5, Jf3, Jc6",
        },
      ],
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