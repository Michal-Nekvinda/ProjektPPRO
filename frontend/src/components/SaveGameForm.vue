<template>
  <div class="modal-overlay">
    <div class="modalNewGame">
      <h4>Vytvoření nové partie</h4>
      <label>Bílý</label>
      <input type="text" placeholder="Bílý" />
      <label>Bílý</label>
      <input type="text" placeholder="Bílý" />
      <label>Černý</label>
      <input type="text" placeholder="Černý" />
      <label>Bílý</label>
      <input type="text" placeholder="Bílý" />
      <label>Bílý</label>
      <input type="text" placeholder="Bílý" />
      <label>Bílý</label>
      <input type="text" placeholder="Bílý" />

      <button @click="onCloseModal(true)">Vytvořit</button>
      <button @click="onCloseModal(false)">Zrušit</button>
    </div>
  </div>
</template>

<script lang="ts">
import { ChessGame } from "@/api/backendApi";
export default {
  name: "SaveGameForm",
  data() {
    return {
      createdGame: new ChessGame(),
    };
  },
  methods: {
    onCloseModal(createNewGame: boolean) {
      if (!createNewGame) {
        this.$parent.onCloseModalNewGame(undefined);
        return;
      }

      return this.$parent.onCloseModalNewGame(this.createdGame);
    },
  },
};
</script>

<style>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: rgba(211, 211, 211, 0.7);
}

.modalNewGame {
  text-align: center;
  background-color: white;
  height: 400px;
  width: 600px;
  margin-top: 10%;
  padding: 10px 0;
}
</style>