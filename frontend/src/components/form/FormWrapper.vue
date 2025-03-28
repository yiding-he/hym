<script setup lang="ts">
import {h, provide, render, renderSlot, useSlots} from "vue";

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

<style>
  .form-wrapper {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 10px;
  }
</style>