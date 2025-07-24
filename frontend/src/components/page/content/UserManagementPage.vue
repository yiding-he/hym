<script setup lang="ts">
import CrudPane from "../../container/CrudPane.vue";
import {ref} from "vue";
import {FieldType, NewFieldConfig, NewFormConfig} from "../../form/FormConfig";
import {CrudConfig} from "../../container/CrudConfig";

const crudConfig = ref<CrudConfig>({
  title: "用户管理",
  form: NewFormConfig({
    fieldWidth: '200px',
    fields: [
      NewFieldConfig({
        label: '用户名关键字',
        name: 'user_name$like',
        type: FieldType.Text,
        maxLength: 20,
      }),
      NewFieldConfig({
        label: '手机号关键字',
        name: 'mobile$like',
        type: FieldType.Text,
        maxLength: 11,
      }),
    ]
  })
})

</script>

<template>
  <CrudPane :config="crudConfig"></CrudPane>
</template>

<style scoped>

</style>