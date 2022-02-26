<template>
  <div class="fileLoader">
    <input
      class="hide"
      type="file"
      accept=".pgn"
      @change="onFileSelected"
      ref="fileInput"
    />
    <div>
      <button class="buttonStyle" @click="$refs.fileInput.click()">
        Vybrat soubor
      </button>
      <button class="buttonStyle" @click="onUpload">Nahrát</button>
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
      filexData: {
        name: "myName",
        data: "",
      },
    };
  },
  methods: {
    onFileSelected(event) {
      let file = event.target.files[0];
      this.formData = new FormData();
      this.formData.append("file", file);
      console.log(file.name);
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
        console.log(response.data);
      });
    },
  },
};
</script>

<style>
.fileLoader {
  text-align: left;
  padding-left: 10px;
}
.buttonStyle {
  display: inline-block;
  text-align: center;
  cursor: pointer;
  margin: 10px 10px;
}
.hide {
  display: none;
}
</style>