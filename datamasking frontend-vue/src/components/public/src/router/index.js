import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import RegisterView from "../views/RegisterView.vue"
import LoginView from "../views/LoginView.vue"
import PaymentsInfo from "../views/PaymentsInfo.vue"

const routes = [

  {
    path: "/",
    name: "LoginView",
    component: LoginView,
    beforeEnter: function (to, from, next) {
      if (localStorage.ActUser) {
        next({ name: "HomeView" });
      } else next();
    },
  },
  {
    path: "/register",
    name: "RegisterView",
    component: RegisterView,
    beforeEnter: function (to, from, next) {
      if (localStorage.ActUser) {
        next({ name: "HomeView" });
      } else next();
    },
  },

  {
    path: "/",
    name: "HomeView",
    component: HomeView,
    beforeEnter: function (to, from, next) {
      if (localStorage.ActUser) {
        next();
      } else next({ name: "LoginView" });
    },
  },
  {
    path: "/paymentsInfo",
    name: "PaymentsInfo",
    component: PaymentsInfo,
    beforeEnter: function (to, from, next) {
      if (localStorage.ActUser) {
        next({ name: "PaymentsInfo" });
      } else next();
    },
  },
  {
    path: '/:NotfoundView(.*)*',
    name: "NotfoundView",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/NotfoundView.vue"),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
