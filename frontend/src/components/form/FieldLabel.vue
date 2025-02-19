<script setup lang="ts">
import {inject} from "vue";

const labelWidth = inject('labelWidth')
const labelAlign = inject('labelAlign')

defineProps({
  label: {
    type: String,
    default: ''
  }
})
</script>

<template>
  <label><span class="field-label" :style="{width: labelWidth, textAlign: labelAlign}">{{ label }}</span>
    <slot></slot>
  </label>
</template>

<style scoped>
span.field-label {
  display: inline-block;
  padding-right: 10px;
}
</style>