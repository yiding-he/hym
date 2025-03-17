<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import Dropdown from "../control/Dropdown.vue";
import Menu from "../control/Menu.vue";
import MenuItem from "../control/MenuItem.vue";
import {contentNavigator} from "../../common/ContentNavigator";
import {UserStatus, useUserStore} from "../../common/UserStore";

const navigator = contentNavigator();
const userStore = useUserStore();
const userLogout = () => {
  userStore.setUserStatus(UserStatus.LOGGED_OUT);
}


</script>

<template>
  <div class="user">
    <dropdown trigger-method="MOUSE_ENTER" :auto-hide="true">
      <template #trigger>
        <div class="icon-box">
          <font-awesome-icon :icon="['fa', 'fa-user']"/>
        </div>
      </template>
      <template #content>
        <Menu>
          <menu-item @click="navigator.navigateTo('UserInfoPage')">个人资料</menu-item>
          <menu-item @click="userLogout()">退出登录</menu-item>
        </Menu>
      </template>
    </dropdown>
  </div>
</template>

<style scoped>
.icon-box {
  height: 50px;
  line-height: 50px;
  padding: 0 16px;
  font-size: 20px;
  color: #DDD;
  background: var(--main-color);
  cursor: pointer;
}
</style>