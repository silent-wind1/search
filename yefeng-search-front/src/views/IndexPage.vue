<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostList :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList :pictureList="pictureList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>
<style></style>
<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "@/plugins/Axios";

let postList = ref([]);
let pictureList = ref([]);
let userList = ref([]);
const router = useRouter();
const route = useRoute(); //拿到当前路由参数，或者说查询条件？
const activeKey = route.params.category; //动态路由同步回标签
const initSearchParams = {
  text: "",
  pageSize: 10,
  pageNumber: 1,
};

const searchParams = ref(initSearchParams);
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});

const onSearch = (searchValue: string) => {
  router.push({
    query: searchParams.value,
  });
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};

myAxios.post("/post/list/page/vo", {}).then((res: any) => {
  postList.value = res.records;
});
myAxios.post("/post/list/page/vo", {}).then((res: any) => {
  console.log(res);
  pictureList.value = [
    {
      url: "http://",
      title: "yefeng",
    },
  ];
});

myAxios.post("/user/list/page/vo", {}).then((res: any) => {
  userList.value = res.records;
});
</script>
