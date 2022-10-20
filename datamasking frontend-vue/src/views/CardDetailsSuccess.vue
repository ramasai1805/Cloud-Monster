<template>
<div class="card col-md-6 mt-3 container">
    <div class="d-flex align-items-center justify-content-center">
    <div class="text-center">
        <!-- <h2 class="display-1 fw-bold">Success!!</h2> -->
        <p class="fs-3"><img src="../assets/success.png" class="img-success"/>Card Details added successfuly!!</p>
        <router-link to="/paymentsInfo">Click here</router-link>
        <span> to go back to adding card details page.</span>
    </div>
</div>
</div>
</template>

<script>

export default {
    setup() {
        
    },
}
</script>
<style scoped>
.container {
    padding: 0;
    box-shadow: 0 5px 10px 0 rgb(0 0 0 / 10%);

}

.padding {
    padding: 5rem !important;
    margin-left: 300px;
}

.card {
    margin-bottom: 1.5rem;
}

.card {
    position: relative;
    display: -ms-flexbox;
    display: flex;
    -ms-flex-direction: column;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 1px solid #c8ced3;
    border-radius: .25rem;
}
.img-success {
    width: 5%;
    height: 5%;
    vertical-align: text-top !important;
}
</style>