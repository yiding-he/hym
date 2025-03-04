<script setup lang="ts">
import { inject, onMounted } from 'vue';

const applicationName = inject('$applicationName', '某某系统');

onMounted(() => {
  // 空的 mount 方法
});
</script>

<template>
  <div class="title">{{ applicationName }}</div>
</template>

<style scoped>
.title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  padding-left: 0.5em;
}
</style>