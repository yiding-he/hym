<script setup lang="ts">

import TitledPane from "../../container/TitledPane.vue";
import FormWrapper from "../../form/FormWrapper.vue";
import FieldWrapper from "../../form/FieldWrapper.vue";
import {ref} from "vue";

const username = ref('');
const createdAt = ref('');
const lastLogin = ref('');

</script>

<template>
  <TitledPane title="用户信息">
    <FormWrapper>
      <form action="">
        <FieldWrapper label="用户名:" label-width="85px">
          <input type="text" disabled name="username" v-model="username">
        </FieldWrapper>
        <FieldWrapper label="创建时间:" label-width="85px">
          <input type="text" disabled name="createdAt" v-model="createdAt">
        </FieldWrapper>
        <FieldWrapper label="上次登录:" label-width="85px">
          <input type="text" disabled name="lastLogin" v-model="lastLogin">
        </FieldWrapper>
      </form>
    </FormWrapper>
  </TitledPane>
</template>

<style scoped>

</style>