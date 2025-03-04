<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import AdminHeaderTitle from "./AdminHeaderTitle.vue";
import AdminHeaderUser from "./AdminHeaderUser.vue";
</script>

<template>
  <header class="admin-header">
    <font-awesome-icon :icon="['fa', 'fa-bars']" class="bars"/>
    <admin-header-title></admin-header-title>
    <div class="spacer"></div>
    <admin-header-user/>
  </header>
</template>

<style scoped>
.admin-header {
  border-bottom: 1px solid var(--main-color);
  display: flex;
  flex-direction: row;
  align-items: center;
}

.bars {
  width: 17px;
  height: 18px;
  padding: 16px;
  background: var(--main-color);
  color: white;
}
</style>

