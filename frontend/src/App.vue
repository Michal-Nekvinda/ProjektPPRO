<template>
  <div id="app">
    <div>
      <the-header label="Databaze sachovych partii" />
      <database-loader />
      <games-table />
    </div>
  </div>
</template>

<script>
import TheHeader from "./components/TheHeader.vue";
import DatabaseLoader from "./components/DatabaseLoader.vue";
import GamesTable from "./components/GamesTable.vue";

export default {
  name: "App",
  components: {
    TheHeader,
    DatabaseLoader,
    GamesTable,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
