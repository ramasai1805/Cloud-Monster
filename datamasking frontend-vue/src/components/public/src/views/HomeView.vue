<template>
<div class="home my-5">
    {{ user }}
</div>
</template>

<script>
import {
    ref
} from "vue";
export default {
    name: "HomeView",
    setup() {
        const user = ref("");
        const useremail = ref("");
        const restoName = ref("");
        const bookDate = ref("");
        if (localStorage.ActUser) {
            let atuser = localStorage.ActUser;
            atuser = JSON.parse(atuser);
            user.value = atuser.name;
            useremail.value = atuser.email;
        }
        const bookRestorant = () => {};
        return {
            user,
            useremail,
            restoName,
            bookDate,
            bookRestorant,
        };
    },
};
</script>
