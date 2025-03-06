<script setup lang="ts">
import {ref} from "vue";
import FieldLabel from "./FieldLabel.vue";
import FieldDescription from "./FieldDescription.vue";
import {exposeFieldFunctions, fieldProperties} from "./FieldProperties";

const props = defineProps(fieldProperties)
const input = ref<HTMLInputElement | null>(null);
defineExpose({
  focus: () => {
    input.value?.focus();
  }
})
exposeFieldFunctions(input.value)
</script>

<template>
  <div class="field field-single-line">
    <label>
      <FieldLabel :label="props.label??''"><input
        ref="input" type="text"
        :disabled="!!props.disabled"
        :maxlength="props.maxLength??''"></FieldLabel>
    </label>
    <FieldDescription v-if="props.description"></FieldDescription>
  </div>
</template>

<style scoped>

</style>