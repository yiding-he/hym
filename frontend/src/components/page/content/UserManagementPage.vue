<script setup lang="ts">
import CrudPane from "../../container/CrudPane.vue";
import {FieldConfig, FieldType, FormConfig} from "../../form/FormConfig";
import {CrudConfig, TableConfig} from "../../container/CrudConfig";
import {ApiList} from "../../../common/ApiClient";

const formConfig = new FormConfig({
  fieldWidth: '200px',
  fields: [
    new FieldConfig({
      label: '用户名关键字',
      name: 'user_name$like',
      type: FieldType.Text,
      maxLength: 20,
    }),
    new FieldConfig({
      label: '手机号关键字',
      name: 'mobile$like',
      type: FieldType.Text,
      maxLength: 11,
    }),
  ]
});

let tableConfig = new TableConfig({
  headers: [{
    name: 'hymUserId',
    label: 'ID'
  }, {
    label: '基本信息', children: [
      {name: 'userName', label: '用户名'},
      {name: 'mobile', label: '手机号'},
      {name: 'email', label: '邮箱'},
    ]
  }, {
    name: 'status', label: '状态'
  }, {
    name: 'role', label: '角色'
  }]
});

const crudConfig = new CrudConfig({
  title: "用户管理",
  form: formConfig,
  table: tableConfig,
  api: ApiList.GetUserList,
})

</script>

<template>
  <CrudPane :config="crudConfig"></CrudPane>
</template>

<style scoped>

</style>