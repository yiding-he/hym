<script setup lang="ts">
import {inject} from "vue";
import FieldLabel from "./FieldLabel.vue";

defineProps({
  label: {
    type: String,
    default: ''
  },
  maxLength: {
    type: Number,
    default: 0
  }
})
const labelWidth = inject('labelWidth')
</script>

<template>
  <div class="field field-single-line">
    <FieldLabel :label="label"><input
      type="password" :maxlength="maxLength > 0? maxLength: ''"></FieldLabel>
  </div>
</template>

<style scoped>

</style>