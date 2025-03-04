<script setup lang="ts">
import {provide} from "vue";

const props = defineProps({
  labelWidth: {
    type: String,
    default: '100px'
  },
  labelAlign: {
    type: String,
    default: 'left'
  }
})

provide('labelWidth', props.labelWidth)
provide('labelAlign', props.labelAlign)
</script>

<template>
  <div class="form">
    <slot></slot>
  </div>
</template>

<style scoped>
.form {
  padding-top: 5px;
  padding-bottom: 10px;
  background: var(--main-background-color);
}
</style>