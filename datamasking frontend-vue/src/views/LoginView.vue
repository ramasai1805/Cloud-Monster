<template>
<div class="col-md-4 mt-5">
    <AlertView :alertType="luser.type" :alertMsg="luser.msg" v-if="luser.name === ''" />
    <form @submit.prevent="loginUSer" class="login">
        <span class="login100-form-title">
            Welcome
        </span>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">User</label>
            <input type="text" v-model="luser.name" class="form-control" id="exampleInputEmail1" />
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Password</label>
            <input type="password" v-model="luser.password" class="form-control" id="exampleInputPassword1" />
        </div>
        <button type="submit" class="btn btn-primary login100-form-btn">Login</button>
    </form>
</div>
</template>

<script>
import {
    reactive
} from "@vue/reactivity";
import {
    useRouter
} from "vue-router";
import AlertView from "@/components/AlertView.vue";


export default {
    setup() {
        const router = useRouter();
        const luser = reactive({
            name: "",
            password: "",
            currentUser: "",
            type: "",
            msg: "",
        });
        const alertView = (type, msg) => {
            luser.type = type;
            luser.msg = msg;
        };
   
        const loginUSer = () => {
            let user = {
                name: luser.name,
                password: luser.password,
            };
            //console.log(user)
            if (luser.name === "" || luser.password === "") {
                alertView("warning", "Please provide valid details")
            } else {
                let ltuser = localStorage.USERDATA;
                ltuser = JSON.parse(ltuser);
                console.log(ltuser);
                router.push("/paymentsInfo");
                let userindex = ltuser.findIndex((item) => {
                    return item.name === user.name;
                });
                console.log(userindex);
                if (userindex > -1) {
                    let passwordIndex = ltuser.findIndex((item) => {
                        if (ltuser[userindex].password === user.password) {
                            return item.password === user.password;
                        }
                    });
                    console.log("pass", passwordIndex);
                    if (passwordIndex > -1) {
                        let activeUSer = ltuser.find((currentuser) => {
                            return currentuser.name === user.name;
                        });
                        localStorage.setItem("ActUser", JSON.stringify(activeUSer));
                        (luser.name = ""), (luser.password = "");
                        router.push("/paymentsInfo");
                        window.location.reload();
                    }
                }
            }
        };
        return {
            luser,
            loginUSer,
            alertView
        };
    },
    components: {
        AlertView
    }
};
</script>

<style scoped>
.login {
   
    background: #fff;
    border-radius: 10px;
    overflow: hidden;
    padding: 20px 55px 33px;
    box-shadow: 0 5px 10px 0 rgb(0 0 0 / 10%);
}

.login100-form-btn {
    font-size: 16px;
    color: #fff;
    line-height: 1.2;
    text-transform: uppercase;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: -ms-flexbox;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px 10px;
    width: 100%;
    height: 44px;
}

.login100-form-title {
    display: block;
    padding: 25px 0;
    font-size: 30px;
    color: #333;
    line-height: 1.2;
    text-align: center;
}
</style>
