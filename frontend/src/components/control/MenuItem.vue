<script setup>

</script>

<template>
  <div class="menu-item">
    <slot/>
  </div>
</template>

<style scoped>
.menu-item {
  padding: 5px 20px;
  cursor: pointer;
}
.menu-item:first-child {
  padding-top: 10px;
}
.menu-item:last-child {
  padding-bottom: 10px;
}
.menu-item:hover {
  background: var(--main-background-color-hover);
}
</style>