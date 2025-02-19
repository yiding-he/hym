<script setup lang="ts">
import {onMounted, ref} from 'vue';
import DialogTitle from "../dialog/DialogTitle.vue";
import Form from "../form/Form.vue";
import TextField from "../form/TextField.vue";
import PasswordField from "../form/PasswordField.vue";
import Button from "../control/Button.vue";

const loginDialog = ref<HTMLDialogElement | null>(null);
const LoginDialog = {
  value: loginDialog.value,
  open: () => {
    loginDialog.value.showModal();
  },
  close: () => {
    loginDialog.value.close();
  }
}

const userNameField = ref<TextField | null>(null);

const emit = defineEmits({
  loginSuccess: () => true,
})

onMounted(() => {
  LoginDialog.open();
  userNameField.value.focus();
})

function onLoginButtonClick() {
  LoginDialog.close();
  emit('loginSuccess');
}
</script>

<template>
  <dialog ref="loginDialog">
    <DialogTitle>登录</DialogTitle>
    <Form label-width="60px" label-align="right">
      <TextField ref="userNameField" label="用户名 :"/>
      <PasswordField label="密码 :"/>
      <div class="form-buttons">
        <Button @click="onLoginButtonClick">登录</Button>
      </div>
    </Form>
  </dialog>
</template>

<style scoped>
  .form-buttons {
    display: flex;
    justify-content: flex-end;
    padding: 10px 10px 5px;
  }
</style>