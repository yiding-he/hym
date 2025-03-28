<script setup lang="ts">

</script>

<template>
<div class="field-button-wrapper">
  <label>&nbsp;</label>
  <slot></slot>
</div>
</template>

<style scoped>
.field-button-wrapper {
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>