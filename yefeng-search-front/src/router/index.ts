import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "../views/IndexPage.vue";
import DetailPage from "@/components/DetailPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: IndexPage,
  },
  {
    path: "/:category",
    component: IndexPage,
  },
  {
    path: "/detail/:id",
    component: DetailPage,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
