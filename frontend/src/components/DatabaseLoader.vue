<template>
 <div class="fileLoader">
  <input 
  class="hide"
  type="file" 
  accept=".pgn" 
  @change="onFileSelected" 
  ref="fileInput">
  <div>
   <button class="buttonStyle" @click="$refs.fileInput.click()"> Vybrat soubor </button>
   <button class="buttonStyle" @click="onUpload"> Nahrát </button>
  </div>
 </div> 
</template>

<script>
export default {
    name: 'DatabaseLoader',
    data() {
      return {
        selectedFile: null
      }
    },
    methods: {
      onFileSelected(event){
        this.selectedFile = event.target.files[0]
      },
      onUpload(){
        const fd = new FormData();
        fd.append('database', this.selectedFile, this.selectedFile.name);
        console.log("send data to BE...")
      }
    }
}
</script>

<style>
.fileLoader{
  text-align: left;
  padding-left: 10px;
}
.buttonStyle{
  display: inline-block;
  text-align: center;
  cursor: pointer;
  margin: 10px 10px;
}
.hide{
  display: none;
}
</style>