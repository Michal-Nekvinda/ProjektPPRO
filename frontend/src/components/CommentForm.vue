<template>
  <div class="modal-overlay">
    <div class="modalComment">
      <h4>Vložit komentář</h4>
      <input
        type="text"
        placeholder="Vložit komentář k tahu..."
        class="tableSearchFilters"
        v-model="comment"
      />
      <button @click="onCloseModal(true)">Vložit</button>
      <button @click="onCloseModal(false)">Zrušit</button>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "SaveGameForm",
  data() {
    return {
      comment: "",
    };
  },
  methods: {
    onCloseModal(save: boolean) {
      return this.$parent.onCloseModalComment(this.comment, save);
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

.modalComment {
  text-align: center;
  background-color: white;
  height: 200px;
  width: 400px;
  margin-top: 10%;
  padding: 10px 0;
}
</style>