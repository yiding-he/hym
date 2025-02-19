<script setup lang="ts">
import {ref} from "vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

const items = ref([])
for (let i = 0; i < 100; i++) {
  items.value.push({
    id: i,
    name: "item " + i,
  })
}
</script>

<template>
  <div class="sidebar">
    <div class="sidebar-content">
      <div class="item" v-for="item in items" :key="item.id">
        <font-awesome-icon :icon="['fas', 'user']"/>
        {{ item.name }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.sidebar {
  border-right: 1px solid #ccc;
  height: calc(100vh - 52px);
  overflow-y: auto;
  white-space: nowrap;
  overflow-x: hidden;
  text-overflow: ellipsis;
}

.sidebar-content {
  padding: 12px 0 12px 12px;
}

.item {
  padding: 10px;
}
</style>