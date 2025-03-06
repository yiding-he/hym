<script setup lang="ts">

import Form from "../../form/Form.vue";
import TextField from "../../form/TextField.vue";
import TitledPane from "../../container/TitledPane.vue";
</script>

<template>
  <TitledPane title="用户信息">
    <Form label-width="85px" label-align="left">
      <TextField :disabled="true" label="用户名:"/>
      <TextField :disabled="true" label="创建时间:"/>
      <TextField :disabled="true" label="上次登录:"/>
    </Form>
  </TitledPane>
</template>

<style scoped>

</style>