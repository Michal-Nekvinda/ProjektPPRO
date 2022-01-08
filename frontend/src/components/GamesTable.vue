<template>
 <div>
  <table>
   <div class="tableRow">
    <tr id='row-1'>
     <th>
         Item 1 
         Item 2 
         Item 3
     </th>
    </tr>
   </div>
   <tr id='row-2'>
    <td>Item 7</td>
   </tr>
  </table>
 </div>    
</template>

<script>
export default {
    name: 'GamesTable'
}
</script>

<style>
.tableRow {
  border: 2px solid black;
  cursor: pointer;
}
.tableRow :hover{
    background-color: lightgray;
}

</style>