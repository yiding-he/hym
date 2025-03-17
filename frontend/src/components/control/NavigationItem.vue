<script setup lang="ts">
import {contentNavigator} from "../../common/ContentNavigator";
import {onMounted, ref} from "vue";

const props = defineProps({
  title: {
    type: String,
    default: ''
  },
  icon: {
    type: String,
    default: ''
  },
  pageName: {
    type: String,
    default: ''
  }
})

const activated = ref(false);
const navigator = contentNavigator();
navigator.onPageLoadFinished(pageName => {
  activated.value = pageName === props.pageName;
})

const onClick = () => {
  navigator.navigateTo(props.pageName)
}

onMounted(() => {
  // navigator 加载流程在渲染 NavigationItem 之前就已经完成，
  // 所以这里需要手动根据 navigator 来判断是否激活
  activated.value = navigator.currentPage === props.pageName;
})
</script>

<template>
<div class="navigation-item"
     @click="onClick"
     :class="{'activated': activated}"
>{{ props.title }}</div>
</template>

<style scoped>
.navigation-item {
  cursor: pointer;
  user-select: none;
  color: var(--text-color-main);
  padding: 0.5rem 1.5em;
}

.navigation-item:hover {
  background: var(--main-background-color-hover);
}

.navigation-item.activated {
  background: var(--main-background-color-active);
}

</style>