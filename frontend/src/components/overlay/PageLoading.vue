<script setup lang="ts">
/**
 * 显示一个加载中对话框，并执行一个后台任务。
 * 当任务完成时，关闭对话框并触发 success 事件。
 * 当任务失败时，关闭对话框并触发 error 事件。
 * 可以通过 backgroundTask 属性传递一个函数，该函数将在对话框打开时执行。
 * 可以通过 success 和 error 事件传递任务的结果。
 *
 * @param {Function} backgroundTask - 后台任务函数
 * @emits {Object} success - 任务成功时触发，传递任务结果
 * @emits {Object} error - 任务失败时触发，传递错误信息
 * @example
 * <PageLoading :background-task="loadInitConfig" @success="onInitSuccess" @error="onInitError"/>
 */
import {onMounted, ref} from 'vue';

const props = defineProps({
  backgroundTask: {
    type: Function,
    required: true
  }
});

const emit = defineEmits<{
  (event: 'success', result: any): void;
  (event: 'error', error: any): void;
}>();
const message = ref<string>('加载中...');

const pageLoadingDialog = ref<HTMLDialogElement | null>(null);
const PageLoadingDialog = {
  value: pageLoadingDialog.value,
  open: () => {
    if (pageLoadingDialog) {
      pageLoadingDialog.value?.showModal();
    }
  },
  close: () => {
    if (pageLoadingDialog) {
      pageLoadingDialog.value?.close();
    }
  }
}

onMounted(() => {
  PageLoadingDialog.open();
  props.backgroundTask()
    .then(result => {
      message.value = "加载完成"
      PageLoadingDialog.close()
      emit('success', result);
    })
    .catch(error => {
      message.value = "加载失败"
      PageLoadingDialog.close()
      emit('error', error);
    });
});
</script>

<template>
  <dialog ref="pageLoadingDialog">{{ message }}</dialog>
</template>

<style scoped>
dialog {
  padding: 1rem 3rem 1.1rem;
}
</style>