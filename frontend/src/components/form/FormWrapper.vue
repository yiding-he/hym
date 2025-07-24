<script setup lang="ts">
import {PropType, provide, ref} from "vue";
import {FieldType, FormConfig} from "./FormConfig";
import FieldWrapper from "./FieldWrapper.vue";

const props = defineProps({
  config: {type: Object as PropType<FormConfig>, required: true}
})

provide('labelAlign', props.config?.labelAlign)
provide('fieldWidth', props.config?.fieldWidth)

const query = ref<Record<string, any>>(
  Object.fromEntries(props.config.fields.map(field => [field.name, '']))
);

defineExpose({
  setQuery: (query: any) => {
    Object.keys(query).forEach(key => {
      query.value[key] = query[key]
    })
  },
  getQuery: () => query.value,
})

</script>

<template>
  <div class="form-wrapper">
    <template v-for="field in config.fields">
      <FieldWrapper :config="field" :form="config">
        <input v-if="[FieldType.Text, FieldType.Password, FieldType.Number].includes(field.type)"
               v-model="query[field.name]"
               :type="field.type" :placeholder="field.placeholder" :maxlength="field.maxLength" :name="field.name">
      </FieldWrapper>
    </template>
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