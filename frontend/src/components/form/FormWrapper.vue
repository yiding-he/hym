<script setup lang="ts">
import {computed, getCurrentInstance, onMounted, PropType, provide, ref} from "vue";
import {FieldType, FormConfig} from "./FormConfig";
import FieldWrapper from "./FieldWrapper.vue";

const props = defineProps({
  config: {type: FormConfig, required: true}
})

provide('labelAlign', props.config?.labelAlign)
provide('fieldWidth', props.config?.fieldWidth)

const query = ref<Record<string, any>>(
  Object.fromEntries(props.config.fields.map(field => [field.name, '']))
);

defineExpose({
  setQuery: (_query: any) => {
    Object.keys(_query).forEach(key => {
      query.value[key] = _query[key]
    })
  },
  getQuery: () => query.value,
})

onMounted(() => {
  const autoFocusFieldName = props.config?.fields.filter(f => f.autofocus).map(f => f.name)
  if (autoFocusFieldName.length > 0) {
    const field = getCurrentInstance()?.vnode?.el?.querySelector(`input[name="${autoFocusFieldName[0]}"]`)
    if (field instanceof HTMLInputElement) {
      field.focus()
    }
  }
})

const dynamicStyle = computed(() => {
  const style: Record<string, string> = {}
  if (props.config.direction) {
    style.flexDirection = props.config.direction
  } else {
    style.flexDirection = 'row'
  }
  return style
})

</script>

<template>
  <div class="form-wrapper" :style="dynamicStyle">
    <template v-for="field in config.fields">
      <FieldWrapper :config="field" :form="config">
        <input v-if="[FieldType.Text, FieldType.Password, FieldType.Number].includes(field.type)"
               v-model="query[field.name]"
               :disabled="field.disabled" :type="field.type" :placeholder="field.placeholder"
               :maxlength="field.maxLength" :name="field.name">
      </FieldWrapper>
    </template>
    <slot></slot>
  </div>
</template>

<style>
  .form-wrapper {
    display: flex;
    flex-wrap: wrap;
    column-gap: 10px;
  }
</style>