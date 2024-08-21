<template id="post-list">
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
          :description="item.content"
          @click="toDetails(item.id)"
        >
          <template #title>
            <a>{{ item.title }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="avatar" class="post-list-img" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<style scoped>
.post-list-img {
  height: 52px;
  width: 52px;
}
</style>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import avatar from "../assets/ice-snake.jpg";
import { useRouter } from "vue-router";
const router = useRouter();
interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});

const toDetails = (id) => {
  router.push({
    path: `/detail/${id}`,
  }); // 跳转到详情页
};
</script>
