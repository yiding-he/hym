<script setup lang="ts">

import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import TitledPane from "../../container/TitledPane.vue";
import FieldButtonWrapper from "../../form/FieldButtonWrapper.vue";
import DataTable from "../../table/DataTable.vue";
import {Header} from "../../table/DataTableCore";
import {ref} from "vue";
import {ApiList} from "../../../common/ApiClient";

// 原始表头定义
const headers: Header[] = [{
  name: 'hymUserId',
  label: 'ID'
}, {
  label: '基本信息', children: [
    {label: '用户名', name: 'userName'},
    {label: '手机号', name: 'mobile'},
    {label: '邮箱', name: 'email'},
  ]
}, {
  name: 'status', label: '状态'
}, {
  name: 'role', label: '角色'
}]

// 查询参数
const query = ref({
  user_name$like: '',
  mobile$like: '',
  status$eq: ''
})
const data = ref<Object[]>([])

const queryData = () => {
  ApiList.GetUserList.call({
    ...query.value
  }).then(response => {
    data.value = response.rows;
  })
}

</script>

<template>
  <TitledPane title="查询用户">
    <form id="query-user-form">
      <FormWrapper field-width="150px">
        <FieldWrapper label="用户名关键字: ">
          <input type="text" name="user_name$like" v-model="query.user_name$like">
        </FieldWrapper>
        <FieldWrapper label="手机号码关键字: ">
          <input type="text" name="mobile$like" v-model="query.mobile$like">
        </FieldWrapper>
        <FieldWrapper label="状态: ">
          <select name="status$eq" v-model="query.status$eq">
            <option value="">全部</option>
            <option value="1">正常</option>
            <option value="2">禁用</option>
          </select>
        </FieldWrapper>
        <FieldButtonWrapper>
          <button type="submit" @click.prevent="queryData">查询</button>
        </FieldButtonWrapper>
      </FormWrapper>
    </form>
  </TitledPane>
  <DataTable :headers="headers" :data="data"></DataTable>
</template>

<style scoped>

</style>