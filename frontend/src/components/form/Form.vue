<script setup lang="ts">

import {formProperties} from "./FormProperties";
import {provideProperties} from "../../common/ComponentPropertiesFactory";

const props = defineProps(formProperties);
provideProperties(props)

</script>

<template>
  <div class="form">
    <slot></slot>
  </div>
</template>

<style scoped>
</style>