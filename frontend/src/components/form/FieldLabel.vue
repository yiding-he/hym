<script setup lang="ts">
import {injectFormProperties} from './FormProperties';

const props = defineProps({
  label: {
    type: String,
    default: '',
  },
});

const { labelWidth, labelAlign } = injectFormProperties();
</script>

<template>
  <label>
    <span class="field-label" :style="{ width: labelWidth, textAlign: labelAlign }">
      {{ props.label }}
    </span>
    <slot></slot>
  </label>
</template>

<style scoped>
span.field-label {
  display: inline-block;
  padding-right: 10px;
  color: #505050;
}
</style>