<script setup lang="ts">
import {computed} from "vue";

const props = defineProps({
  width: {type: String, default: ''}
})

const dynamicStyle = computed(() => {
  const style: Record<string, string> = {}
  if (props.width) {
    style.width = props.width
  } else {
    style.flex = '1'
  }
  return style
})
</script>

<template>
  <div class="field-fill-wrapper" :style="dynamicStyle">
    <label>&nbsp;</label>
    <label>&nbsp;</label>
  </div>
</template>

<style scoped>
.field-fill-wrapper {
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>