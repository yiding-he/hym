<script setup lang="ts">

import FormWrapper from "../form/FormWrapper.vue";
import TitledPane from "../container/TitledPane.vue";
import FieldButtonWrapper from "../form/FieldButtonWrapper.vue";
import DataTable from "../table/DataTable.vue";
import {Header, PageResult} from "../table/DataTableCore";
import {onMounted, PropType, ref} from "vue";
import {ApiList, CallOptions} from "../../common/ApiClient";
import {CrudConfig} from "./CrudConfig";

defineProps({
  config: {type: Object as PropType<CrudConfig>, required: true}
})

const dataTableRef = ref<InstanceType<typeof DataTable>>();
const formRef = ref<InstanceType<typeof FormWrapper>>();

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

// 查询结果
const data = ref<PageResult>({
  total: 0, pageIndex: 0, pageSize: 10, rows: [], totalPage: 0
})
// 查询方法
const queryData = (event: any) => {
  const options = CallOptions
    .of(formRef.value?.getQuery())
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
  <TitledPane :title="config.title">
    <form id="query-user-form">
      <FormWrapper ref="formRef" :config="config.form">
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