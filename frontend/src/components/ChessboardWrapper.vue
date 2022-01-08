<template>
<div>
 <div id="chessboard" style="width: 400px"></div>
 <div class="controlPanel">
     <button class="buttonStyle" @click="moveBackward"> Předchozí </button>
     <button class="buttonStyle" @click="moveForward"> Další </button>
 </div>
</div>

</template>

<script>
import ChessBoard from "chessboardjs-vue";

export default {
    name: 'ChessboardWrapper',
    mounted() {
        this.board = ChessBoard('chessboard',"start");
    },
    methods: {
        moveForward() {
            this.board.move(this.moves[this.index]);
            this.index++;
        },
        moveBackward() {
            this.index--;
            this.board.move(this.reverseMove(this.moves[this.index]));
        },

        reverseMove(move){
            const fromTo = move.split('-');
            return fromTo[1]+'-'+fromTo[0];
        }
    },
    data() {
        return {
            board: null,
            moves: ['e2-e4','e7-e5','g1-f3','b8-c6'],
            index: 0,
        }
    }
}
</script>

<style>
.controlPanel{
    text-align: left;
}
.buttonStyle{
  display: inline-block;
  cursor: pointer;
  margin: 10px 10px;
}
</style>