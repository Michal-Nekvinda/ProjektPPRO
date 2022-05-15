<template>
  <div class="app">
    <div>
      <the-header label="Databáze šachových partií" />
      <games-table ref="gt" />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import TheHeader from "./components/TheHeader.vue";
import GamesTable from "./components/GamesTable.vue";

@Component({
  components: {
    TheHeader,
    GamesTable,
  },
})
export default class App extends Vue {}
</script>

<style>
.app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 30px;
}
</style>
