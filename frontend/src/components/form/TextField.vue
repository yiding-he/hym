<script setup lang="ts">
import {inject, ref} from "vue";
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

defineExpose({
  focus: () => {
    input.value.focus();
  }
})

const input = ref<HTMLInputElement | null>(null);
</script>

<template>
  <div class="field field-single-line">
    <label>
      <FieldLabel :label="label"><input
        ref="input" type="text"
        :maxlength="maxLength > 0? maxLength: ''"></FieldLabel>
    </label>
  </div>
</template>

<style scoped>

</style>