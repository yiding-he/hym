<script setup lang="ts">
import CrudPane from "../../container/CrudPane.vue";
import {FieldConfig, FieldType, FormConfig} from "../../form/FormConfig";
import {CrudConfig, OperationsConfig, TableConfig} from "../../container/CrudConfig";
import {FormDialogConfig} from "../../dialog/FormDialog";
import FormDialog from "../../dialog/FormDialog.vue";
import {ref} from "vue";
import {ApiList} from "../../../common/ApiList";
import {CallOptions} from "../../../common/ApiClient";

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

const newUserDialogRef = ref<InstanceType<typeof FormDialog>>();

const operationsConfig = new OperationsConfig({
  operations: [
    {
      label: '新增用户', func: () => {
        newUserDialogRef.value?.open();
      }
    },
    {
      label: '删除', func: () => {
      }, forRow: true
    },
  ]
})

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
  operations: operationsConfig,
})

const newUserDialogConfig = new FormDialogConfig({
  title: '创建用户',
  form: new FormConfig({
    fieldWidth: '300px',
    direction: 'column',
    fields: [
      new FieldConfig({
        label: '用户名',
        name: 'userName',
        type: FieldType.Text,
        maxLength: 40,
      }),
      new FieldConfig({
        label: '手机号',
        name: 'mobile',
        type: FieldType.Text,
        maxLength: 11,
      }),
      new FieldConfig({
        label: '邮箱',
        name: 'email',
        type: FieldType.Text,
        maxLength: 50,
      }),
    ]
  }),
  onSubmit: (context) => {
    ApiList.AddUser.callByOptions(CallOptions
      .of(context.formData)
      .addElementsDisabledWhenCalling([context.actionButton])
    )
    newUserDialogRef.value?.close();
  }
})

</script>

<template>
  <CrudPane :config="crudConfig"></CrudPane>
  <FormDialog :config="newUserDialogConfig" ref="newUserDialogRef"></FormDialog>
</template>

<style scoped>

</style>