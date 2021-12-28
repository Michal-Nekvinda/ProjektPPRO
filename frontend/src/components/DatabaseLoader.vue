<template>
 <div>
  <div class="fileLoader">
   <input type="file" accept=".pgn" @change="onFileSelected">
  </div> 
    <input type="text">
 </div>
</template>

<script>
export default {
    name: 'DatabaseLoader',
    methods: {
      onFileSelected(event){
        console.log(event);
      }
    }
}
</script>

<style>
.fileLoader{
  text-align: left;
  padding-left: 50px;
}
</style>