<script setup lang="ts">

import {Header, parseHeaders} from "./DataTableCore";
import {onMounted, ref} from "vue";

const props = defineProps({
  data: {
    type: Array,
    required: true
  },
  headers: {
    type: Array<Header>,
    required: true
  }
})

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
    <tbody>
    <tr v-for="(row, index) in data" :key="index">
      <td v-for="(column, index) in columns" :key="index">
        {{ row[column.name] || '-' }}
      </td>
    </tr>
    </tbody>
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
</style>