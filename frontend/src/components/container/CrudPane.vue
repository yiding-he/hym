<script setup lang="ts">

import FormWrapper from "../form/FormWrapper.vue";
import TitledPane from "../container/TitledPane.vue";
import FieldButtonWrapper from "../form/FieldButtonWrapper.vue";
import DataTable from "../table/DataTable.vue";
import {PageResult} from "../table/DataTableCore";
import {onMounted, ref} from "vue";
import {CallOptions} from "../../common/ApiClient";
import {CrudConfig} from "./CrudConfig";

const props = defineProps({
  config: {type: CrudConfig, required: true}
})

const dataTableRef = ref<InstanceType<typeof DataTable>>();
const formRef = ref<InstanceType<typeof FormWrapper>>();

// 查询结果
const data = ref<PageResult>({
  total: 0, pageIndex: 0, pageSize: 10, rows: [], totalPage: 0
})

// 查询方法
const queryData = (event: any) => {
  if (!props.config.api) {
    return;
  }

  const options = CallOptions
    .of({
      ...formRef.value?.getQuery(),
      pageIndex: data.value.pageIndex,
      pageSize: data.value.pageSize,
    })
    .apply(dataTableRef.value?.addHooksToCallOptions)
    .addElementsDisabledWhenCalling([event?.target])

  props.config.api
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
  <DataTable ref="dataTableRef" :headers="config.table.headers" :data="data" @pageIndexChanged="onPageIndexChanged"></DataTable>
</template>

<style scoped>

</style>