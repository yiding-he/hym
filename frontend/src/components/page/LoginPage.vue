<script setup lang="ts">
import {inject, nextTick, onMounted, ref} from 'vue';
import DialogTitle from "../dialog/DialogTitle.vue";
import Button from "../control/Button.vue";
import FormWrapper from "../form/FormWrapper.vue";
import FieldWrapper from "../form/FieldWrapper.vue";
import {AppConfig, DEFAULT_APP_CONFIG} from "../../common/AppConfig";
import {ApiList} from "../../common/ApiClient";
import {UserStatus, useUserStore} from "../../common/UserStore";

const appConfig = inject<AppConfig>('$appConfig', DEFAULT_APP_CONFIG);
const loginDialog = ref<HTMLDialogElement | null>(null);
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

const usernameTextField = ref<InstanceType<typeof HTMLInputElement> | null>(null);
const username = ref<string>('');
const password = ref<string>('');
const errorMessage = ref<string | null>(null);

const userStore = useUserStore();

onMounted(async () => {
  LoginDialog.open();
  username.value = "admin";
  password.value = "admin123";
})

function onLoginButtonClick(event: Event) {
  event.preventDefault();

  if (!username.value || !password.value) {
    errorMessage.value = '用户名和密码不能为空';
    return;
  }

  ApiList.Login.call({
    username: username.value,
    password: password.value,
  }).then(response => {
    const token = response.token;
    userStore.setToken(token);
    userStore.setUserStatus(UserStatus.LOGGED_IN)
    LoginDialog.close();
  }).catch(error => {
    errorMessage.value = error.message;
  })

}
</script>

<template>
  <dialog ref="loginDialog">
    <DialogTitle>登录{{ appConfig.applicationName }}</DialogTitle>
      <form action="" @submit="onLoginButtonClick">
        <div class="form-body">
          <FormWrapper>
            <FieldWrapper label="用户名 :">
              <input type="text" ref="usernameTextField" name="username" v-model="username"/>
            </FieldWrapper>
            <FieldWrapper label="密码 :">
              <input type="password" name="password" v-model="password"/>
            </FieldWrapper>
            <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
            <div class="form-buttons">
              <Button>登录</Button>
            </div>
          </FormWrapper>
        </div>
      </form>
  </dialog>
</template>

<style scoped>
.form-body {
  margin: 10px;
  width: 200px;
}

.form-buttons {
  display: flex;
  justify-content: start;
  margin-top: 5px;
}

.error-message {
  color: red;
  margin-top: 5px;
}
</style>