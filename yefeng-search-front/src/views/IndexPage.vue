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
      <a-tab-pane key="message" tab="消息">
        <MessageList />
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
import { message } from "ant-design-vue";
import MessageList from "@/components/MessageList.vue";

let postList = ref([]);
let pictureList = ref([]);
let userList = ref([]);
const router = useRouter();
const route = useRoute(); //拿到当前路由参数，或者说查询条件？
const activeKey = route.params.category; //动态路由同步回标签
const initSearchParams = {
  type: activeKey,
  text: "",
  pageNumber: 1,
  pageSize: 10,
};
const searchText = ref(route.query.text || "");

/**
 * 加载数据
 * @param params
 */
const loadData = (params: any) => {
  let { type } = params;
  if (!type) {
    router.push({
      path: `/post`,
    });
  }
  const query = {
    ...params,
    searchText: params.text,
  };

  // myAxios.post("/post/list/page/vo", query).then((res: any) => {
  //   postList.value = res.records;
  // });
  //
  // myAxios.post("/picture/list/page/vo", query).then((res: any) => {
  //   pictureList.value = res.records;
  // });
  //
  // myAxios.post("/user/list/page/vo", query).then((res: any) => {
  //   userList.value = res.records;
  // });

  myAxios.post("/search/all", query).then((res: any) => {
    if (type === "post") {
      postList.value = res.postList;
    } else if (type === "user") {
      userList.value = res.userList;
    } else if (type === "picture") {
      pictureList.value = res.pictureList;
    }
  });
};

const searchParams = ref(initSearchParams);
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
    type: route.params.category,
  } as any;
  loadData(searchParams.value);
});

// 首次请求
// loadData(initSearchParams);

const onSearch = (value: string) => {
  router.push({
    query: {
      ...searchParams.value,
      text: value,
    },
  });
  // 根据条件查询
  loadData(searchParams.value);
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>
