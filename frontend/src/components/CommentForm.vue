<template>
  <div class="modalOverlay">
    <div class="modalComment">
      <h4 class="center">Vložit komentář</h4>
      <input
        class="wideInput"
        type="text"
        placeholder="Vložit komentář k tahu..."
        v-model="comment"
      />
      <div class="center">
        <button class="button" @click="onCloseModal(true)">Vložit</button>
        <button class="button" @click="onCloseModal(false)">Zrušit</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "SaveGameForm",
  data() {
    return {
      comment: "",
    };
  },
  methods: {
    onCloseModal(save: boolean) {
      this.$parent.onCloseModalComment(this.comment, save);
      this.comment = "";
    },
  },
});
</script>

<style>
.modalOverlay {
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
  background-color: white;
  height: 200px;
  width: 400px;
  margin-top: 10%;
  padding: 10px;
}
.wideInput {
  width: 380px;
}
</style>