<template>
  <h1>Web Socket</h1>
  <div>
    <a-space direction="vertical">
      <a-input
        v-model:value="userAccount"
        placeholder="请输入你的账号"
        addonBefore="账号"
      />

      <a-input-password
        v-model:value="userPassword"
        placeholder="请输入你的密码"
        addonBefore="密码"
      />
      <a-button type="primary" @click="login">登录</a-button>
      <a-input-search
        v-model:value="message"
        placeholder="input search text"
        size="large"
        @search="onSearch"
      >
        <template #enterButton>
          <a-button>Custom</a-button>
        </template>
      </a-input-search>
    </a-space>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import Axios from "@/plugins/Axios";

const userAccount = ref("yefeng");
const userPassword = ref("123456789");
const message = ref();
const userLoginRequest = reactive({
  userAccount,
  userPassword,
});
let userVO = reactive({
  id: "",
  userName: "",
});
let ws = reactive([]);

const login = () => {
  Axios.post("http://localhost:8101/api/user/login", userLoginRequest)
    .then((res) => {
      userVO = {
        ...res,
      };
      console.log(userVO);
      init(userVO.id);
    })
    .catch((err) => {
      console.log(err);
    });
};

const init = (id) => {
  if (typeof WebSocket === "undefined") {
    alert("您的浏览器不支持socket");
  } else {
    ws = new WebSocket(`ws://localhost:8101/api/ws/${id}`);
    //  //连接发生错误的回调方法
    ws.onerror = function () {
      console.log("ws连接发生错误");
    };
    //连接成功建立的回调方法
    ws.onopen = function () {
      console.log("ws连接成功");
    };
    //接收到消息的回调方法
    ws.onmessage = function (event) {
      console.log(id + "的", event.data);
    };
    //连接关闭的回调方法
    ws.onclose = function () {
      console.log("ws连接关闭");
    };
  }
};

const onSearch = () => {
  console.log(message.value);
  //接收到消息的回调方法
  ws.send(message.value);
};
</script>

<style scoped></style>
