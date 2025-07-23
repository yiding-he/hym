<script setup lang="ts">

import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import TitledPane from "../../container/TitledPane.vue";
import FieldButtonWrapper from "../../form/FieldButtonWrapper.vue";
import DataTable from "../../table/DataTable.vue";
import {Header, PageResult} from "../../table/DataTableCore";
import {getCurrentInstance, onMounted, onUnmounted, ref} from "vue";
import {ApiList, CallOptions} from "../../../common/ApiClient";
import {EventBus} from "../../../common/EventBus";

// 数据表格
const dataTableRef = ref<InstanceType<typeof DataTable>>();

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
  const options = CallOptions
    .of({
      ...query.value,
      pageIndex: data.value.pageIndex,
      pageSize: data.value.pageSize
    })
    .apply(dataTableRef.value?.addHooksToCallOptions)
    .addElementsDisabledWhenCalling([event?.target]);

  ApiList.GetRoleList.callByOptions(options).then(response => {
    data.value = response;
  })
}
const queryDataWithPage0 = (event: any) => {
  data.value.pageIndex = 0;
  queryData(event);
}

const onPageIndexChanged = (pageIndex: number) => {
  console.log('onPageIndexChanged', pageIndex);
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
          <button type="submit" @click.prevent="queryDataWithPage0">查询</button>
        </FieldButtonWrapper>
      </FormWrapper>
    </form>
  </TitledPane>
  <DataTable ref="dataTableRef" :headers="headers" :data="data" @pageIndexChanged="onPageIndexChanged"></DataTable>
</template>

<style scoped>

</style>