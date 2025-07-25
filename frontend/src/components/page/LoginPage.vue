<script setup lang="ts">
import {inject, onMounted, ref} from 'vue';
import DialogTitle from "../dialog/DialogTitle.vue";
import Button from "../control/Button.vue";
import FormWrapper from "../form/FormWrapper.vue";
import {AppConfig, DEFAULT_APP_CONFIG} from "../../common/AppConfig";
import {ApiList} from "../../common/ApiClient";
import {UserStatus, useUserStore} from "../../common/UserStore";
import {FieldConfig, FieldType, FormConfig} from "../form/FormConfig";

const appConfig = inject<AppConfig>('$appConfig', DEFAULT_APP_CONFIG);
const loginDialog = ref<HTMLDialogElement | null>(null);
const LoginDialog = {
  value: loginDialog.value,
  open: () => {
    if (loginDialog.value) {
      loginDialog.value.showModal();
    }
  },
  close: () => {
    if (loginDialog.value) {
      loginDialog.value.close();
    }
  }
}

const loginFormRef = ref<InstanceType<typeof FormWrapper>>();
const errorMessage = ref<string | null>(null);

const userStore = useUserStore();

onMounted(async () => {
  LoginDialog.open();
  loginFormRef.value?.setQuery({username: "admin", password: "admin123"})
})

function onLoginButtonClick(event: Event) {
  event.preventDefault();

  const query = loginFormRef.value?.getQuery()
  const username = query?.username;
  const password = query?.password;

  if (!username || !password) {
    errorMessage.value = '用户名和密码不能为空';
    return;
  }
  ApiList.Login.call({
    username: username,
    password: password,
  }).then(response => {
    console.log("登录成功，token=", response.token)
    const token = response.token;
    userStore.setToken(token);
    userStore.setUserStatus(UserStatus.LOGGED_IN)
    LoginDialog.close();
  }).catch(error => {
    console.log("登录失败", error)
    errorMessage.value = error.message;
  })
}

const formConfig = new FormConfig({
  fieldWidth: "300px",
  fields: [
    new FieldConfig({label: "用户名 :", name: "username", type: FieldType.Text, autofocus: true}),
    new FieldConfig({label: "密码 :", name: "password", type: FieldType.Password})
  ]
})

</script>

<template>
  <dialog ref="loginDialog">
    <DialogTitle>登录{{ appConfig.applicationName }}</DialogTitle>
      <form action="" @submit="onLoginButtonClick">
        <div class="form-body">
          <FormWrapper ref="loginFormRef" :config="formConfig">
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
  width: 300px;
}

.form-buttons {
  display: flex;
  justify-content: start;
  margin-top: 5px;
}

.error-message {
  width: 300px;
  color: red;
  margin-top: 5px;
}
</style>