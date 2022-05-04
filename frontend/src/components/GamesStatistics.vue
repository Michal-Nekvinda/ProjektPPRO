<template>
  <div>
    <label>Výběr statistiky pro partie:</label>
    <select v-model="selected">
      <option
        v-for="stat in statisticsNames"
        v-bind:value="stat.id"
        :key="stat.id"
      >
        {{ stat.name }}
      </option>
    </select>
    <button @click="onStatChange" v-if="this.selected != 0">Obnovit</button>
    <label
      >Počet záznamů<input
        type="number"
        min="1"
        step="1"
        v-model="numberOfRecords"
    /></label>
    <table>
      <tr>
        <th
          v-for="(columnName, index) in displayedStatistics.header"
          :key="index"
        >
          {{ columnName }}
        </th>
      </tr>
      <tr v-for="(rowData, index) in displayedStatistics.data" :key="index">
        <td v-for="(columnData, index) in rowData" :key="index">
          {{ columnData }}
        </td>
      </tr>
    </table>
  </div>
</template>

<script lang="ts">
import { ChessGame } from "./../api/backendApi";
export default {
  name: "GamesStatistics",
  props: {
    games: Array,
  },
  methods: {
    onStatChange() {
      const tableHeader: string[] = this.createHeader();
      this.displayedStatistics = new Statistics();
      this.displayedStatistics.addHeader(tableHeader);
      const tableData: ChessGame[] = this.createData();
      tableData.forEach((row) => {
        this.displayedStatistics.addDataRow(row);
      });
    },
    createHeader() {
      switch (this.selected) {
        case StatisticsType.Openings:
          return ["Zahájení", "Počet", "Podíl"];
        case StatisticsType.Piece:
          return ["Výhra bílý", "Výhra černý", "Remíza"];
        case StatisticsType.Players:
          return ["Hráč", "Body", "Úspěšnost"];
      }
    },
    createData(): Array<Array<string>> {
      switch (this.selected) {
        case StatisticsType.Openings:
          return this.getMostPlayedOpenings();
        /*
        case StatisticsType.Piece:
          return this.getSuccessRateByPieceColor();
        case StatisticsType.Players:
          return this.getBestPlayers();
          */
      }
    },
    getMostPlayedOpenings() {
      let dict: any = {};
      let gamesCount = 0;
      this.games.forEach((game: ChessGame) => {
        if (game.eco) {
          dict[game.eco] = dict[game.eco] ? dict[game.eco] + 1 : 1;
          gamesCount++;
        }
      });
      // Create items array
      var items = Object.keys(dict).map(function (key) {
        return [key, dict[key], dict[key] / gamesCount];
      });

      // Sort the array based on the second element
      items.sort(function (first, second) {
        return second[1] - first[1];
      });

      // Create a new array with only the first 5 items
      return items.slice(0, this.numberOfRecords);
    },
    getSuccessRateByPieceColor() {},
    getBestPlayers() {},
  },
  data() {
    return {
      selected: StatisticsType.None,
      statisticsNames: [
        { id: StatisticsType.Openings, name: "Nejčastěji hraná zahájení" },
        { id: StatisticsType.Piece, name: "Úspěšnost dle barvy figur" },
        { id: StatisticsType.Players, name: "Úspěšnost hráčů" },
      ],
      displayedStatistics: new Statistics(),
      numberOfRecords: 10,
    };
  },
};
const StatisticsType = {
  None: 0,
  Openings: 1,
  Piece: 2,
  Players: 3,
};

class Statistics {
  header: string[];
  data: ChessGame[];
  constructor() {
    this.header = [];
    this.data = [];
  }
  addHeader(header: string[]) {
    this.header = header;
  }
  addDataRow(rowData: ChessGame) {
    this.data.push(rowData);
  }
}
</script>

