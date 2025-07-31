<script setup lang="ts">
import NavigationBlock from "../control/NavigationBlock.vue";
import {ref} from "vue";
import NavigationItem from "../control/NavigationItem.vue";

import {ApiList} from "../../common/ApiList";

const functions = ref([])
ApiList.GetFunctions.call({}).then(resp => {
  functions.value = resp.functions;
})

</script>

<template>
  <div class="sidebar">
    <div class="navigation-block" v-for="category in functions" :key="category.title">
      <NavigationBlock :title="category.title">
        <NavigationItem v-for="item in category.functions"
                        :key="item.title" :title="item.title"
                        :page-name="item.pageName"></NavigationItem>
      </NavigationBlock>
      <div class="separator"></div>
    </div>
  </div>
</template>

<style scoped>
.sidebar {
  border-right: 1px solid #ccc;
  height: calc(100vh - 52px);
  overflow-y: auto;
  white-space: nowrap;
  overflow-x: hidden;
  text-overflow: ellipsis;
  padding-top: 10px;
  box-sizing: content-box;
}

.navigation-block {
}

.separator {
  margin: 10px;
  border-bottom: 1px solid #ccc;
}

.navigation-block:last-child .separator {
  display: none;
}

</style>