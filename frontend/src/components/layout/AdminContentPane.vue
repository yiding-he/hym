<script setup lang="ts">
import {nextTick, onMounted, ref, shallowRef, watch} from "vue";
import {contentNavigator} from "../../common/ContentNavigator";

const currentPage = shallowRef(null);
const pageStatus = ref("" as string)

const navigator = contentNavigator()
navigator.onPageLoadStarted((name: string) => {
  console.log("Loading page: " + name)
  pageStatus.value = "started"
})
navigator.onPageLoadFinished((name, component) => {
  console.log("Page loaded and start mounting: " + name)
  if (component) {
    currentPage.value = component;
  } else {
    currentPage.value = null;
    pageStatus.value = "";
  }
})

onMounted(() => {
  navigator.init()
})

watch(currentPage, async (newCurrentPage) => {
  if (newCurrentPage) {
    await nextTick();
    pageStatus.value = "finished"
    console.log("Component page mounted.")
  }
})
</script>

<template>
  <div class="overlay" v-if="pageStatus === 'started'">
    <div class="loading-spinner"></div>
  </div>
  <component :is="currentPage" v-if="currentPage"/>
</template>

<style scoped>
.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
}
.loading-spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  animation: spin 1s linear infinite;
}
/* 添加 spin 关键帧动画 */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>