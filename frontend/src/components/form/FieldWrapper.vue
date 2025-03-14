<script setup lang="ts">
import {inject} from "vue";

defineProps({
  label: {
    type: String,
    required: false,
    default: '',
  }
})

const labelAlign = inject('labelAlign', 'left')
const fieldWidth = inject('fieldWidth', '200px')

</script>

<template>
  <div class="field-wrapper" :style="{width: fieldWidth}">
    <label :style="{textAlign: labelAlign}">{{ label }}</label>
    <slot></slot>
  </div>
</template>

<style scoped>
.field-wrapper {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  gap: 5px;
}
.field-wrapper label {
  display: block;
}
</style>