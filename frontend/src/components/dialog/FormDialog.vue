<script setup lang="ts">

import DialogTitle from "./DialogTitle.vue";
import FormWrapper from "../form/FormWrapper.vue";
import DialogBody from "./DialogBody.vue";
import DialogFooter from "./DialogFooter.vue";
import {PropType, ref} from "vue";
import {FormDialogConfig} from "./FormDialog";

const props = defineProps({
  config: {type: Object as PropType<FormDialogConfig>, required: true}
})

const formConfig = props.config?.form;
const formRef = ref<typeof FormWrapper>();
const dialogRef = ref<HTMLDialogElement>();

defineExpose({
  open: () => {
    dialogRef.value?.showModal();
  },
  close: () => {
    dialogRef.value?.close();
  }
})

const closeDialog = () => {
  dialogRef.value?.close();
}
</script>

<template>
<dialog ref="dialogRef">
  <DialogTitle>{{config.title}}</DialogTitle>
  <form>
    <DialogBody>
      <FormWrapper :config="formConfig" ref="formRef">
      </FormWrapper>
    </DialogBody>
    <DialogFooter>
      <button type="submit" @click.prevent="config.onSubmit">确定</button>
      <button type="button" @click.prevent="closeDialog">取消</button>
    </DialogFooter>
  </form>
</dialog>
</template>

<style scoped>

</style>