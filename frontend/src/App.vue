<script setup lang="ts">
import './style.css'
import {provide, ref, onMounted} from 'vue';
import {apiClient} from './common/ApiClient';
import PageLoading from "./components/overlay/PageLoading.vue";
import AdminPage from "./components/page/AdminPage.vue";
import LoginPage from "./components/page/LoginPage.vue";

// 定义枚举
enum AppStatus {
  LOADING,
  LOGGED_OUT,
  LOGGED_IN,
}

// 定义本地属性
const appStatus = ref(AppStatus.LOADING);
const applicationName = ref('某某系统');
provide('$applicationName', applicationName);

// 页面初始化过程
const loadInitConfig = () => apiClient.get('/init-config');
const onInitSuccess = (response: any) => {
  applicationName.value = response.data.applicationName
  appStatus.value = AppStatus.LOGGED_OUT;
};
const onInitError = (error: any) => {
  console.error('Failed to load init config: ', error);
};

// 登录成功后的处理
const onLoginSuccess = () => {
  appStatus.value = AppStatus.LOGGED_IN;
};

</script>

<template>
  <PageLoading v-if="appStatus == AppStatus.LOADING"
               :background-task="loadInitConfig"
               @success="onInitSuccess"
               @error="onInitError"/>
  <login-page v-if="appStatus == AppStatus.LOGGED_OUT" @login-success="onLoginSuccess"/>
  <admin-page v-if="appStatus == AppStatus.LOGGED_IN"/>
</template>

<style scoped>
</style>