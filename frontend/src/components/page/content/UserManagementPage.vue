<script setup lang="ts">

import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import TitledPane from "../../container/TitledPane.vue";
import FieldButtonWrapper from "../../form/FieldButtonWrapper.vue";
import DataTable from "../../table/DataTable.vue";
import {Header, PageResult} from "../../table/DataTableCore";
import {onMounted, ref} from "vue";
import {ApiList, CallOptions} from "../../../common/ApiClient";

// 数据表格
const dataTableRef = ref<InstanceType<typeof DataTable>>();

// 表头定义
const headers: Header[] = [{
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

// 查询参数
const query = ref({
  user_name$like: '',
  mobile$like: '',
  status$eq: ''
})
// 查询结果
const data = ref<PageResult>({
  total: 0, pageIndex: 0, pageSize: 10, rows: [], totalPage: 0
})
// 查询方法
const queryData = (event: any) => {
  const options = CallOptions.of(query.value)
    .apply(dataTableRef.value?.addHooksToCallOptions)
    .addElementsDisabledWhenCalling([event?.target])

  ApiList.GetUserList
    .callByOptions(options)
    .then(response => {
      console.log("查询结果 ", response)
      data.value = response;
    })
}
const onPageIndexChanged = (pageIndex: number) => {
  data.value.pageIndex = pageIndex;
  queryData(null);
}

onMounted(async () => {
  queryData(null);
})
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
  <DataTable ref="dataTableRef" :headers="headers" :data="data" @pageIndexChanged="onPageIndexChanged"></DataTable>
</template>

<style scoped>

</style>