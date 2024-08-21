<template>
  <a-list
    item-layout="horizontal"
    :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3 }"
    :data-source="props.userList"
  >
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable style="width: 240px">
          <template #cover>
            <img alt="example" :src="avatar" />
          </template>
          <a-card-meta
            :title="item.userName == null ? '匿名用户' : item.userName"
          >
            <template #description>
              {{ item.userProfile == null ? "" : item.userProfile }}
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>

<style scoped></style>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import avatar from "../assets/ice-snake.jpg";

interface Props {
  userList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  userList: () => [],
});
</script>
