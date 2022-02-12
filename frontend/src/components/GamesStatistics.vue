<template>
  <div>
    <label>Výběr statistiky pro partie:</label>
    <select v-model="selected" @change="showTable">
      <option v-for="stat in stats" v-bind:value="stat.id" :key="stat.id">
        {{ stat.name }}
      </option>
    </select>
    <div>
      <label for="dd">{{ selected }}</label>
    </div>
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

      <tr class="dataRow" v-for="game in games" :key="game.id">
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
  </div>
</template>

<script>
class Game {
  constructor(
    id,
    white,
    whiteElo,
    black,
    blackElo,
    result,
    moves,
    opening,
    tournament,
    date
  ) {
    this.id = id;
    this.white = white;
    this.whiteElo = whiteElo;
    this.black = black;
    this.blackElo = blackElo;
    this.result = result;
    this.moves = moves;
    this.opening = opening;
    this.tournament = tournament;
    this.date = date;
  }
}

export default {
  name: "GamesStatistics",
  props: {
    games: Array[Game],
  },
  methods: {
    showTable() {
      console.log(this.selected);
    },
  },
  data() {
    return {
      selected: "",
      stats: [
        { id: 1, name: "volba 1" },
        { id: 2, name: "volba 2" },
      ],
    };
  },
};
</script>

