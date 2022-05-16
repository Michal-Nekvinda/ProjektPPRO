<template>
  <div class="marginTop">
    <label class="labelWide">Výběr statistiky pro partie:</label>
    <select v-model="selected" @change="onStatChange">
      <option
        v-for="stat in statisticsNames"
        v-bind:value="stat.id"
        :key="stat.id"
      >
        {{ stat.name }}
      </option>
    </select>
    <div v-show="showNumberOfRecordsChoice()">
      <label class="labelWide">Počet záznamů: </label>
      <input
        type="number"
        min="1"
        step="1"
        v-model="numberOfRecords"
        @change="onStatChange"
      />
    </div>
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
    <div>
      <button
        class="buttonNoMargin"
        @click="onStatChange"
        v-show="this.selected != 0"
      >
        Obnovit
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { ChessGame, Result } from "./../api/backendApi";
import Vue from "vue";

export default Vue.extend({
  name: "GamesStatistics",
  props: {
    games: Array,
  },
  methods: {
    onStatChange() {
      const tableHeader: string[] = this.createHeader();
      const tableData: Array<Array<string>> = this.createData();

      this.displayedStatistics = new Statistics();
      this.displayedStatistics.addHeader(tableHeader);

      tableData.forEach((row) => {
        this.displayedStatistics.addDataRow(row);
      });
    },

    createHeader() {
      switch (this.selected) {
        case StatisticsType.Openings:
          return ["Zahájení", "Počet", "Podíl (%)"];
        case StatisticsType.Piece:
          return ["Výhra bílý (%)", "Remíza (%)", "Výhra černý (%)"];
        case StatisticsType.Players:
          return ["Hráč", "Body", "Úspěšnost (%)"];
      }
    },

    createData(): Array<Array<string>> {
      switch (this.selected) {
        case StatisticsType.Openings:
          return this.getMostPlayedOpenings();
        case StatisticsType.Piece:
          return this.getSuccessRateByPieceColor();
        case StatisticsType.Players:
          return this.getBestPlayers();
      }
    },

    getMostPlayedOpenings() {
      let openingsCount = {};
      let games = 0;
      this.games.forEach((game: ChessGame) => {
        if (game.eco) {
          openingsCount[game.eco] = openingsCount[game.eco]
            ? openingsCount[game.eco] + 1
            : 1;
          games++;
        }
      });

      const rows = Object.keys(openingsCount).map(function (key) {
        return [key, openingsCount[key], (openingsCount[key] / games) * 100];
      });

      return this.sortAndSliceTableRows(rows);
    },

    getSuccessRateByPieceColor() {
      let pointsPerPiece = {};
      pointsPerPiece[Result.WHITE_WIN] = 0;
      pointsPerPiece[Result.DRAW] = 0;
      pointsPerPiece[Result.BLACK_WIN] = 0;

      if (this.games.length === 0) {
        return [];
      }

      this.games.forEach((game: ChessGame) => {
        if (game.result) {
          pointsPerPiece[game.result] = pointsPerPiece[game.result] + 1;
        }
      });
      const successRate =
        100 /
        (pointsPerPiece[Result.WHITE_WIN] +
          pointsPerPiece[Result.DRAW] +
          pointsPerPiece[Result.BLACK_WIN]);
      return [
        [
          pointsPerPiece[Result.WHITE_WIN] * successRate,
          pointsPerPiece[Result.DRAW] * successRate,
          pointsPerPiece[Result.BLACK_WIN] * successRate,
        ],
      ];
    },

    getBestPlayers() {
      let playersPoints = {};
      this.games.forEach((game: ChessGame) => {
        if (game.result) {
          const points = this.getPointsForPlayers(game.result);
          playersPoints[game.whiteName] = playersPoints[game.whiteName]
            ? [
                playersPoints[game.whiteName][0] + points[0],
                playersPoints[game.whiteName][1] + 1,
              ]
            : [points[0], 1];
          playersPoints[game.blackName] = playersPoints[game.blackName]
            ? [
                playersPoints[game.blackName][0] + points[1],
                playersPoints[game.blackName][1] + 1,
              ]
            : [points[1], 1];
        }
      });

      const rows = Object.keys(playersPoints).map(function (key) {
        return [
          key,
          playersPoints[key][0] + "/" + playersPoints[key][1],
          (playersPoints[key][0] / playersPoints[key][1]) * 100,
        ];
      });

      return this.sortAndSliceTableRows(rows);
    },

    getPointsForPlayers(result: string) {
      if (result === Result.DRAW) {
        return [0.5, 0.5];
      }
      return result === Result.WHITE_WIN ? [1, 0] : [0, 1];
    },

    sortAndSliceTableRows(rows: any[][]) {
      rows.sort(function (first, second) {
        return second[2] - first[2];
      });

      return rows.slice(0, this.numberOfRecords);
    },

    showNumberOfRecordsChoice() {
      if (
        this.selected === StatisticsType.Openings ||
        this.selected === StatisticsType.Players
      ) {
        return true;
      }
      return false;
    },
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
      numberOfRecords: 5,
    };
  },
});

const StatisticsType = {
  None: 0,
  Openings: 1,
  Piece: 2,
  Players: 3,
};

class Statistics {
  header: string[];
  data: string[];
  constructor() {
    this.header = [];
    this.data = [];
  }
  addHeader(header: string[]) {
    this.header = header;
  }
  addDataRow(rowData: string) {
    this.data.push(rowData);
  }
}
</script>

<style>
.labelWide {
  text-align: left;
  margin-right: 5px;
  margin-bottom: 15px;
  display: inline-block;
  padding: 2px, 2px, 2px, 2px;
  width: 200px;
}
.buttonNoMargin {
  display: inline-block;
  cursor: pointer;
}
</style>

