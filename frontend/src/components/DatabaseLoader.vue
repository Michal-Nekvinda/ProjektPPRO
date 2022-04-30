<template>
  <div class="fileLoader">
    <form>
      <input
        id="fileChooser"
        class="buttonStyle"
        type="file"
        accept=".pgn"
        @change="onFileSelected"
      />
    </form>
    <div>
      <button class="buttonStyle" @click="onUpload" v-if="selectedFile != null">
        Nahrát
      </button>
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
        url: "http://localhost:8080/api/database",
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
  text-align: left;
  margin: 2px, 5px;
}
.buttonStyle {
  display: inline-block;
  text-align: left;
  cursor: pointer;
}
</style>