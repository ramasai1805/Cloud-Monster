import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

//default user
if (localStorage.USERDATA === undefined) {
    let user = [{
        name : "admin",
        email: "admin@gmail.com",
        password: "admin"
    }]
    localStorage.setItem('USERDATA', JSON.stringify(user))
}


createApp(App).use(store).use(router).mount("#app");
