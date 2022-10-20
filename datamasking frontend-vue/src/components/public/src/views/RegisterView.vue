<template>
<div class="col-md-4 mt-5">
    <form @submit.prevent="registerUSER">
        <div class="mb-3">
            <label for="username" class="form-label">User Name</label>
            <input type="text" class="form-control" v-model="resUser.username" id="username" />
        </div>
        <div class="mb-3">
            <label for="emailid" class="form-label">Email address</label>
            <input type="email" class="form-control" id="emailid" v-model="resUser.email" />
        </div>
        <div class="mb-3">
            <label for="firstpassword" class="form-label">Password</label>
            <input type="password" class="form-control" v-model="resUser.password" id="firstpassword" />
        </div>
        <div class="mb-3">
            <label for="cpassword" class="form-label">Confirm Password</label>
            <input type="password" class="form-control" v-model="resUser.cpassword" id="cpassword" />
        </div>
        <p>If You have an account please <router-link to="/"> LogIn </router-link>
        </p>
        <button type="submit" class="btn btn-primary">ResisterUSer</button>
    </form>
</div>
</template>

<script>
import {
    reactive
} from "@vue/reactivity";

export default {
    setup() {
        const resUser = reactive({
            users: [],
            username: "",
            email: "",
            password: "",
            cpassword: "",
        });

        const registerUSER = () => {
            let user = {
                name: resUser.username,
                email: resUser.email,
                password: resUser.password,
                cpassword: resUser.cpassword,
            };
            console.log(user)

            if (localStorage.USERDATA) {
                let getalluser = localStorage.USERDATA
                getalluser = JSON.parse(getalluser)
                resUser.users = getalluser

            }

            if (resUser.username !== "" || resUser.email !== "" || resUser.password !== "" || resUser.cpassword !== "") {
                resUser.users.push(user)
                localStorage.setItem("USERDATA", JSON.stringify(resUser.users))
            } else {
                alert("please provide valid user")
            }
            resUser.username = "",
                resUser.email = "",
                resUser.password = "",
                resUser.cpassword = ""
        };
        return {
            resUser,
            registerUSER,
        };
    },
};
</script>

<style scoped>
</style>
