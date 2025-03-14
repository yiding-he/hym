<script setup lang="ts">
import {inject, nextTick, onMounted, ref} from 'vue';
import DialogTitle from "../dialog/DialogTitle.vue";
import Button from "../control/Button.vue";
import FormWrapper from "../form/FormWrapper.vue";
import FieldWrapper from "../form/FieldWrapper.vue";
import {AppConfig, DEFAULT_APP_CONFIG} from "../../common/AppConfig";

const appConfig = inject<AppConfig>('$appConfig', DEFAULT_APP_CONFIG);
const loginDialog = ref<HTMLDialogElement | null>(null);
const usernameTextField = ref<InstanceType<typeof HTMLInputElement> | null>(null); // Ref for the TextField component
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
    <DialogTitle>登录{{appConfig.applicationName}}</DialogTitle>
    <FormWrapper>
      <form action="">
        <div class="form-body">
          <FieldWrapper label="用户名 :">
            <input type="text" ref="usernameTextField" name="username"/>
          </FieldWrapper>
          <FieldWrapper label="密码 :">
            <input type="password" name="password"/>
          </FieldWrapper>
          <div class="form-buttons">
            <Button @click="onLoginButtonClick">登录</Button>
          </div>
        </div>
      </form>
    </FormWrapper>
  </dialog>
</template>

<style scoped>
.form-body {
  padding: 10px;
}

.form-buttons {
  display: flex;
  justify-content: start;
  margin-top: 5px;
}
</style>