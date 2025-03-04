<script setup lang="ts">
import AdminHeader from "../layout/AdminHeader.vue";
import AdminSidebar from "../layout/AdminSidebar.vue";
import AdminContentPane from "../layout/AdminContentPane.vue";
import {computed, ref} from "vue";

const sidebarOpen = ref(true);
const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
}
const sidebarWidth = computed(() => {
  return sidebarOpen.value ? "200px" : "1px";
})
const toggleButtonText = computed(() => {
  return sidebarOpen.value ? "<" : ">";
})
</script>

<template>
  <div class="admin-scaffold">
    <admin-header class="admin-header"></admin-header>
    <div class="admin-scaffold-body">
      <admin-sidebar class="admin-sidebar" :style="{width: sidebarWidth}"></admin-sidebar>
      <div class="toggle-button">
        <div class="toggle-button-content" @click="toggleSidebar">{{ toggleButtonText }}</div>
      </div>
      <div class="admin-content-pane">
        <admin-content-pane/>
      </div>
    </div>
  </div>
</template>


<style scoped>
.admin-scaffold {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
  margin: 0;
  padding: 0;
}

.admin-header {
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.22);
  z-index: 1000;
  height: 50px;
}

.toggle-button {
  order: 0;
  flex: 0 1 auto;
  height: 100%;
  width: 0;
  position: relative;
  z-index: 901;
}

.toggle-button-content {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: -1px;
  width: 12px;
  height: 34px;
  cursor: pointer;
  padding-top: 12px;
  padding-left: 0;
  border-style: solid;
  border-width: 1px 1px 1px 0;
  border-color: #AAAAAA;
  border-top-right-radius: 3px;
  border-bottom-right-radius: 3px;
  color: #AAAAAA;
  background: #ffffff;
}

.toggle-button-content:hover {
  background-color: #eee;
}

.admin-sidebar {
  z-index: 900;
}

.admin-scaffold-body {
  display: flex;
  flex-direction: row;
  flex: 1 1 auto;
  z-index: 999;
}

.admin-content-pane {
  flex: 1 1 auto;
  z-index: 998;
  position: relative;
}

</style>
