<script setup lang="ts">
import {provide} from "vue";

const props = defineProps({
  labelAlign: {
    type: String,
    required: false,
    default: 'left'
  },
  fieldWidth: {
    type: String,
    required: false,
    default: '200px'
  }
})

provide('labelAlign', props.labelAlign)
provide('fieldWidth', props.fieldWidth)

</script>

<template>
  <div class="form-wrapper">
    <slot></slot>
  </div>
</template>

<style scoped>

</style>