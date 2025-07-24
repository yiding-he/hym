<script setup lang="ts">
import {FieldConfig, FormConfig} from "./FormConfig";
import {onMounted, PropType} from "vue";

const props = defineProps({
  config: {type: Object as PropType<FieldConfig>, required: true},
  form: {type: Object as PropType<FormConfig>, required: true}
})

onMounted(() => {
})

</script>

<template>
  <div class="field-wrapper" :style="{width: form.fieldWidth}">
    <label :style="{textAlign: form.labelAlign}">{{ config.label }}</label>
    <slot></slot>
  </div>
</template>

<style scoped>
.field-wrapper {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  gap: 3px;
}
.field-wrapper label {
  display: block;
}
</style>