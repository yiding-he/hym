<script setup>

import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import Dropdown from "../control/Dropdown.vue";
import Menu from "../control/Menu.vue";
import MenuItem from "../control/MenuItem.vue";
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
          <menu-item>个人资料</menu-item>
          <menu-item>个人资料</menu-item>
          <menu-item>个人资料</menu-item>
          <menu-item>个人资料</menu-item>
          <menu-item>退出登录</menu-item>
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