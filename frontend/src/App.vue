<script setup lang="ts">
import './style.css'
import {provide, ref, watch} from 'vue';
import {apiClient, ApiList} from './common/ApiClient';
import PageLoading from "./components/overlay/PageLoading.vue";
import AdminPage from "./components/page/AdminPage.vue";
import LoginPage from "./components/page/LoginPage.vue";
import {AppConfig} from "./common/AppConfig";
import {createUserStoreSyncToLocalStorage, UserStatus, useUserStore} from "./common/UserStore";

// 定义枚举
enum AppStatus {
  LOADING,
  LOGGED_OUT,
  LOGGED_IN,
}

// 定义本地属性
// 其中 applicationName 初始化了一个默认值，然后在页面初始化过程中，会从服务端获取一个真实值
const appStatus = ref(AppStatus.LOADING);
const appConfig = ref<AppConfig>({applicationName: '某某系统'});
provide('$appConfig', appConfig);

// 页面初始化过程
const loadInitConfig = () => ApiList.InitConfig.call({});
const onInitSuccess = (response: AppConfig) => {
  appConfig.value = response
  appStatus.value = AppStatus.LOGGED_OUT;
};
const onInitError = (error: any) => {
  console.error('Failed to load init config: ', error);
};

// userStore 是使用 pinia 框架创建出来的
createUserStoreSyncToLocalStorage();
const userStore = useUserStore();
userStore.$subscribe((mutation, state) => {
  console.log("User store changed: ", state.userStatus);
  const newStatus = state.userStatus;
  if (newStatus == UserStatus.LOGGED_IN) {
    appStatus.value = AppStatus.LOGGED_IN;
  } else {
    appStatus.value = AppStatus.LOGGED_OUT;
  }
})

</script>

<template>
  <PageLoading v-if="appStatus == AppStatus.LOADING"
               :background-task="loadInitConfig"
               @success="onInitSuccess"
               @error="onInitError"/>
  <login-page v-if="appStatus == AppStatus.LOGGED_OUT"/>
  <admin-page v-if="appStatus == AppStatus.LOGGED_IN"/>
</template>

<style scoped>
</style>