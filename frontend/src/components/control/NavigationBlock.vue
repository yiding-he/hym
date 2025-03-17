<script setup lang="ts">
  import {ref} from "vue";
  import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

  defineProps({
    title: {
      type: String,
      default: ''
    }
  })

  const collapsed = ref(false)
</script>

<template>
  <div class="title" @click="collapsed = !collapsed">
    <span style="flex-grow: 1">{{ title }}</span>
    <font-awesome-icon :icon="['fa', (collapsed ? 'fa-angle-down' : 'fa-angle-up')]" class="icon"/>
  </div>
  <div class="collapsable" :style="{display: collapsed ? 'none' : 'block'}">
    <slot></slot>
  </div>
</template>

<style scoped>
  .title {
    cursor: pointer;
    user-select: none;
    color: var(--text-color-main);
    font-weight: bold;
    padding: 0.5rem;
    display: flex;
  }

  .title:hover {
    background: var(--main-background-color-hover);
  }

  .icon {
    position: relative;
    top: 4px;
    padding-right: 5px;
  }
</style>