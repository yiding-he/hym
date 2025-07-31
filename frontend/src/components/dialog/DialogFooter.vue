<script setup lang="ts">

</script>

<template>
  <div class="form-footer">
    <slot></slot>
  </div>
</template>

<style scoped>
.form-footer {
  display: flex;
  justify-content: flex-end;
  padding: 0 10px 10px;
  gap: 10px;
}
</style>