<script setup lang="ts">

</script>

<template>
  <div class="dialog-title">
    <slot></slot>
  </div>
</template>

<style scoped>
.dialog-title {
  font-weight: bold;
  display: flex;
  padding: 5px 10px;
  border-bottom: 1px solid var(--main-color-light);
  background: var(--main-color);
  color: white;
}
</style>