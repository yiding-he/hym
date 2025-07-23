<script setup lang="ts">

import {Header, PageResult, parseHeaders} from "./DataTableCore";
import {computed, onMounted, ref} from "vue";
import {CallOptions} from "../../common/ApiClient";

enum Status {
  Ready = "ready",
  Loading = "loading",
}

const props = withDefaults(defineProps<{
  data: PageResult;
  headers: Header[];
}>(), {
  data: () => ({total: 0, pageIndex: 1, pageSize: 10, totalPage: 1, rows: []}),
  headers: () => [],
});

const emits = defineEmits<{
  (e: 'pageIndexChanged', pageIndex: number): void,
  (e: 'statusChanged', status: Status): void,
}>()

const status = ref<Status>(Status.Ready)

const gotoPage = (pageIndex: number | string) => {
  if (status.value === Status.Loading) {
    return
  }
  pageIndex = parseInt(pageIndex as string)
  if (pageIndex < 0 || pageIndex >= props.data.totalPage) {
    return
  }
  emits('pageIndexChanged', pageIndex)
}

// 解析出来的表头布局，用于渲染表头
const headerLayout = ref()
// 解析出来的列定义，用于渲染数据行
const columns = ref()

onMounted(() => {
  // 解析原始表头定义
  const parseResult = parseHeaders(props.headers)
  headerLayout.value = parseResult.headerLayout
  columns.value = parseResult.columns
})

defineExpose({
  addHooksToCallOptions: (options: CallOptions) => {
    options.hooks?.push({
      onStart: () => {
        status.value = Status.Loading
        emits('statusChanged', status.value)
      },
      onFinish: () => {
        status.value = Status.Ready
        emits('statusChanged', status.value)
      }
    })
  }
})

const displayPageIndex = computed({
  get: () => props.data.pageIndex + 1,
  set: (value) => {
    const newPageIndex = parseInt(value.toString()) - 1;
    if (!isNaN(newPageIndex)) {
      emits('pageIndexChanged', newPageIndex);
    }
  }
})

</script>

<template>
  <table class="data-table">
    <thead>
    <tr v-for="(cells, index) in headerLayout" :key="index">
      <th v-for="(cell, index) in cells" :key="index" :colspan="cell.colSpan" :rowspan="cell.rowSpan">
        {{ cell.label }}
      </th>
    </tr>
    </thead>
    <tbody v-if="data.rows.length && status === Status.Ready">
    <tr v-for="(row, index) in data.rows" :key="index">
      <td v-for="(column, index) in columns" :key="index">
        {{ row[column.name] || '-' }}
      </td>
    </tr>
    </tbody>
    <tbody v-else>
    <tr>
      <td colspan="100%" class="no-data-placeholder" v-if="status === Status.Ready && !data.rows.length">暂无数据</td>
      <td colspan="100%" class="loading-placeholder" v-if="status === Status.Loading">查询中...</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
      <td colspan="100%">
        <div class="pagination">
          <button :disabled="data.pageIndex<=0" @click="gotoPage(data.pageIndex-1)">上一页</button>
          <button :disabled="data.pageIndex>=data.totalPage-1" @click="gotoPage(data.pageIndex+1)">下一页</button>
          <span>第 {{ displayPageIndex }} 页 / 共 {{ data.totalPage }} 页</span>
          <input type="number" v-model="displayPageIndex" style="width: 5em" :min="1" :max="data.totalPage">
        </div>
      </td>
    </tr>
    </tfoot>
  </table>
</template>

<style scoped>
.data-table {
  width: 100%;
  border-collapse: collapse; /* 合并边框 */
}

.data-table th, .data-table td {
  border: 1px solid var(--main-color-light); /* 单元格边框 */
  padding: 3px; /* 内边距 */
  text-align: center; /* 文本左对齐 */
}

.data-table th {
  background-color: var(--datatable-header-bgcolor); /* 表头背景色 */
  color: var(--datatable-header-textcolor);
  font-weight: bold; /* 表头字体加粗 */
}

.pagination {
  display: flex;
  justify-content: start;
  align-items: center;
  gap: 10px;
  padding: 5px;
}

td.no-data-placeholder, td.loading-placeholder {
  text-align: center;
  color: var(--text-color-light2);
  padding: 15px;
}
</style>