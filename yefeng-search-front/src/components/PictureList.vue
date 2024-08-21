<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import avatar from "../assets/ice-snake.jpg";
import router from "@/router";

interface Props {
  pictureList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  pictureList: () => [],
});

// eslint-disable-next-line @typescript-eslint/no-empty-function
function toPushDetails() {}
</script>

<template>
  <a-list
    item-layout="horizontal"
    :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3 }"
    :data-source="props.pictureList"
  >
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable @click="toPushDetails">
          <template #cover>
            <img alt="example" :src="item.urlImg" />
          </template>
          <a-card-meta :title="item.title" />
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>

<style scoped></style>
