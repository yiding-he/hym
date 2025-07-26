<script setup lang="ts">

import TitledPane from "../../container/TitledPane.vue";
import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import {onMounted, ref} from "vue";
import {FieldConfig, FieldType, FormConfig} from "../../form/FormConfig";
import FieldButtonWrapper from "../../form/FieldButtonWrapper.vue";

const formRef = ref<InstanceType<typeof FormWrapper>>()

const formConfig = new FormConfig({
  fieldWidth: '200px',
  fields: [
    new FieldConfig({label: '用户名:', name: 'username', type: FieldType.Text, disabled: true}),
    new FieldConfig({label: '创建时间:', name: 'createdAt', type: FieldType.Text, disabled: true}),
    new FieldConfig({label: '上次登录:', name: 'lastLogin', type: FieldType.Text, disabled: true}),
  ]
})

onMounted(() => {
  formRef.value?.setQuery({
    username: 'admin',
    createdAt: '2023-01-01',
    lastLogin: '2023-01-01',
  })
})

</script>

<template>
  <TitledPane title="用户信息">
    <FormWrapper ref="formRef" :config="formConfig">
      <FieldButtonWrapper><button>修改</button></FieldButtonWrapper>
    </FormWrapper>
  </TitledPane>
</template>

<style scoped>

</style>