<script setup lang="ts">
import {CrudConfig, TableConfig} from "../../container/CrudConfig";
import {FieldConfig, FieldType, FormConfig} from "../../form/FormConfig";
import CrudPane from "../../container/CrudPane.vue";

import {ApiList} from "../../../common/ApiList";

const formConfig = new FormConfig({
  fieldWidth: '200px',
  fields: [
    new FieldConfig({
      label: '角色名关键字',
      name: 'role_name$like',
      type: FieldType.Text,
      maxLength: 20,
    }),
  ]
});

const tableConfig = new TableConfig({
  headers: [{
    name: 'hymRoleId',
    label: 'ID',
  }, {
    name: 'roleName', label: '角色名称',
  }, {
    name: 'roleDescription', label: '角色描述',
  }]
});

const crudConfig = new CrudConfig({
  title: "角色管理",
  form: formConfig,
  table: tableConfig,
  api: ApiList.GetRoleList,
})

</script>

<template>
  <CrudPane :config="crudConfig"></CrudPane>
</template>

<style scoped>

</style>