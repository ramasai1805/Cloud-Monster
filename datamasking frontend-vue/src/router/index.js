import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue"
import PaymentsInfo from "../views/PaymentsInfo.vue"

const routes = [

  {
    path: "/",
    name: "LoginView",
    component: LoginView,
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
  },

  {
    path: "/paymentsInfo",
    name: "PaymentsInfo",
    component: PaymentsInfo,
    beforeEnter: function (to, from, next) {
      if (localStorage.ActUser) {
        next({ name: "login" });
      } else next();
    },
  },

  {
    path: '/paymentpreview',
    name: "PaymentPreview",    
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/PaymentPreview.vue"),
  },

  {
    path: '/cardDetailsSuccess',
    name: "CardDetailsSuccess",    
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/CardDetailsSuccess.vue"),
  },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
