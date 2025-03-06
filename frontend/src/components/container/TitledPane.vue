<script setup lang="ts">
defineProps({
  title: {type: String, default: ''},

})
</script>

<template>
  <div class="titled-pane">
    <div class="titled-pane-title"><span>{{ title }}</span></div>
    <div class="titled-pane-body">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped>
.titled-pane .titled-pane-title {
  font-weight: bold;
  padding: 0.5em 0;
  border-bottom: 1px solid var(--main-color-light);
}
.titled-pane .titled-pane-body {
  padding: 0.5em 0;
}
</style>