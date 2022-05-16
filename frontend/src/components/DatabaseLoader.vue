<template>
  <div>
    <form>
      <input
        id="fileChooser"
        type="file"
        accept=".pgn"
        @change="onFileSelected"
      />
    </form>
    <div class="fileLoader">
      <button @click="onUpload" v-if="selectedFile">Nahrát</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DatabaseLoader",
  data() {
    return {
      formData: null,
      selectedFile: null,
    };
  },
  methods: {
    onFileSelected(event) {
      this.selectedFile = event.target.files[0];
      this.formData = new FormData();
      this.formData.append("file", this.selectedFile);
    },

    onUpload() {
      axios({
        url: "/api/saveGames",
        method: "POST",
        data: this.formData,
        headers: {
          Accept: "application/json",
          "Content-Type": "multipart/form-data",
        },
      }).then((response) => {
        this.$parent.addGames(response.data);
        this.selectedFile = null;
        document.getElementById("fileChooser").value = null;
      });
    },
  },
};
</script>

<style>
.fileLoader {
  margin-top: 10px;
}
.button {
  display: inline-block;
  cursor: pointer;
  margin: 10px;
}
</style>