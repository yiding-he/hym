<script setup lang="ts">

import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import TitledPane from "../../container/TitledPane.vue";
import FieldButtonWrapper from "../../form/FieldButtonWrapper.vue";
import DataTable from "../../table/DataTable.vue";
import {Header, PageResult} from "../../table/DataTableCore";
import {onMounted, ref} from "vue";
import {ApiList} from "../../../common/ApiClient";

// 表头定义
const headers: Header[] = [{
  name: 'hymRoleId',
  label: 'ID'
}, {
  name: 'roleName', label: '角色名称'
}, {
  name: 'roleDescription', label: '角色描述'
}]

// 查询参数
const query = ref({
  role_name$like: ''
})
// 查询结果
const data = ref<PageResult>({
  total: 0, pageIndex: 0, pageSize: 10, rows: [], totalPage: 0
})
// 查询方法
const queryData = (event: any) => {
  ApiList.GetRoleList.call({
    ...query.value
  }, event?.target).then(response => {
    console.log("查询结果，总记录数=", response.total, "总页数=", response.totalPage)
    data.value = {
      ...response,
      pageIndex: response.pageIndex + 1,  // 查询参数是第 0 页，展示出来需是第 1 页
    };
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
  <TitledPane title="查询角色">
    <form id="query-user-form">
      <FormWrapper field-width="150px">
        <FieldWrapper label="角色名关键字: ">
          <input type="text" name="user_name$like" v-model="query.role_name$like">
        </FieldWrapper>
        <FieldButtonWrapper>
          <button type="submit" @click.prevent="queryData">查询</button>
        </FieldButtonWrapper>
      </FormWrapper>
    </form>
  </TitledPane>
  <DataTable :headers="headers" :data="data" @pageIndexChanged="onPageIndexChanged"></DataTable>
</template>

<style scoped>

</style>