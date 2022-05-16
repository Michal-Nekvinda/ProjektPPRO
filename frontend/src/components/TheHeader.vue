<template>
  <h1 class="center">{{ label }}</h1>
</template>

<script>
export default {
  name: "TheHeader",
  props: {
    label: String,
  },
};
</script>
<style>
.center {
  text-align: center;
}
</style>

