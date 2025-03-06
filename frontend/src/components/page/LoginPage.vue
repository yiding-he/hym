<script setup lang="ts">
import {nextTick, onMounted, ref} from 'vue';
import DialogTitle from "../dialog/DialogTitle.vue";
import Form from "../form/Form.vue";
import TextField from "../form/TextField.vue";
import PasswordField from "../form/PasswordField.vue";
import Button from "../control/Button.vue";

const loginDialog = ref<HTMLDialogElement | null>(null);
const usernameTextField = ref<InstanceType<typeof TextField> | null>(null); // Ref for the TextField component
const LoginDialog = {
  value: loginDialog.value,
  open: () => {
    if (loginDialog.value) {
      loginDialog.value.showModal();
      nextTick(() => {
        if (usernameTextField.value) {
          usernameTextField.value.focus();
        }
      })
    }
  },
  close: () => {
    if (loginDialog.value) {
      loginDialog.value.close();
    }
  }
}

const emit = defineEmits({
  loginSuccess: () => true,
})

onMounted(async () => {
  LoginDialog.open();
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
      <div class="form-body">
        <TextField ref="usernameTextField" label="用户名 :"/>
        <PasswordField label="密码 :"/>
        <div class="form-buttons">
          <Button @click="onLoginButtonClick">登录</Button>
        </div>
      </div>
    </Form>
  </dialog>
</template>

<style scoped>
.form-body {
  padding: 10px;
}

.form-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 5px;
}
</style>