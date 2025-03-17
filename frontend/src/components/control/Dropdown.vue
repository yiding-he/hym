<script setup lang="ts">
import {computed, nextTick, ref} from 'vue';

// 使用 ref 来追踪下拉框的显示状态
const isOpen = ref(false);
const triggerRef = ref(null);
const contentRef = ref(null);
const props = defineProps({
  triggerMethod: {
    type: String,
    default: 'MOUSE_ENTER',
    validator: (value: string) => ['MOUSE_ENTER', 'CLICK'].includes(value)
  },
  autoHide: {
    type: Boolean,
    default: true
  }
});

// 定义一个函数来切换下拉框的显示状态
const toggleDropdown = () => {
  if (isOpen.value) {
    closeDropdown();
  } else {
    openDropdown();
  }
};
const closeDropdown = () => {
  isOpen.value = false;
}
const openDropdown = async () => {
  isOpen.value = true;
  // 要等待 isOpen 相关事件处理完毕后再调整元素位置
  await nextTick();
  updateContentPosition();
}

const updateContentPosition = () => {
  if (triggerRef.value && contentRef.value) {
    const triggerRect = triggerRef.value.getBoundingClientRect();
    contentRef.value.style.left = `${triggerRect.left}px`;
    contentRef.value.style.top = `${triggerRect.bottom + 1}px`;

    const viewportWidth = window.innerWidth;
    const viewportHeight = window.innerHeight;
    const contentRect = contentRef.value.getBoundingClientRect();
    if (contentRect.right > viewportWidth) {
      contentRef.value.style.left = `${viewportWidth - contentRect.width}px`;
    }
    if (contentRect.bottom > viewportHeight) {
      contentRef.value.style.top = `${viewportHeight - contentRect.height - triggerRect.height - 1}px`;
    }
  }
};

let closeDropdownTimer = null;
const delayCloseDropdown = () => {
  if (!props.autoHide) {
    return;
  }
  closeDropdownTimer = setTimeout(() => {
    closeDropdown();
  }, 300)
}
const onTriggerMouseEnter = () => {
  if (props.triggerMethod === 'MOUSE_ENTER') {
    openDropdown();
  }
  clearTimeout(closeDropdownTimer);
}
const onTriggerMouseLeave = () => {
  onContentMouseLeave()
}
const onTriggerMouseClick = () => {
  toggleDropdown();
}
const onContentMouseEnter = () => {
  clearTimeout(closeDropdownTimer);
}
const onContentMouseLeave = () => {
  delayCloseDropdown()
}
const onContentMouseClick = () => {
  closeDropdown();
}

const _contentVisibility = computed(() => {
  return isOpen.value ? 'block' : 'none';
})

</script>

<template>
  <div class="trigger-wrapper"
       ref="triggerRef"
       @mouseenter="onTriggerMouseEnter"
       @mouseleave="onTriggerMouseLeave"
       @click="onTriggerMouseClick">
    <slot name="trigger"></slot>
  </div>
  <div class="content-wrapper"
       ref="contentRef"
       @mouseenter="onContentMouseEnter"
       @mouseleave="onContentMouseLeave"
       @click="onContentMouseClick"
       :style="{ display: _contentVisibility }">
    <slot name="content"></slot>
  </div>
</template>

<style scoped>
.content-wrapper {
  position: absolute;
  display: none;
  border: 1px solid #DDD;
  min-width: 100px;
  width: auto;
  z-index: 1000;
}
</style>