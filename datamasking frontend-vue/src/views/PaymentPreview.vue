<template>
<div class="container mb-5">
    <div class="row justify-content-start">
        <div class="col-lg-4 mb-lg-0 mb-3" v-if="card.cardType === 'Visa'">
            <div class="card p-4 py-5 visa">
                <div class="img-box">
                    <img src="../assets/visa-card-logo.png" alt="">
                </div>
                <div class="number">
                    <label class="fw-bold">{{numbercard}}</label>
                </div>
                <div class="d-flex align-items-center justify-content-between my-3">
                    <small><span class="fw-bold">Expiry date:</span><span>{{card.month}}/{{card.year}}</span></small>
                    <small><span class="fw-bold">Name:</span><span>{{card.nameCard}}</span></small>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-lg-0 mb-3" v-else>
            <div class="card p-4 py-5 master">
                <div class="img-box">
                    <img src="../assets/master-card-logo.png" alt="">
                </div>
                <div class="number">
                    <label class="fw-bold">{{numbercard}}</label>
                </div>
                <div class="d-flex align-items-center justify-content-between my-3">
                    <small><span class="fw-bold">Expiry date:</span><span>{{card.month}}/{{card.year}}</span></small>
                    <small><span class="fw-bold">Name:</span><span>{{card.nameCard}}</span></small>
                </div>
            </div>
        </div>

        <div class="col-lg-8">
            <div class="card p-3">
                <div class="card-body  p-0">
                    <p>
                        <a class="btn btn-primary p-2 w-100 h-100 d-flex align-items-center justify-content-between" data-bs-toggle="collapse">
                            <span class="fw-bold">Credit Card Detail</span>
                        </a>
                    </p>
                    <div class=" show p-3 pt-0">
                        <div class="row">
                            <div class="col-lg-7 mb-lg-0 mb-3">
                                <p class="mb-0 my-2">
                                    <span class="fw-bold">Card Holder Name: </span>
                                    <span class="c-green"> {{card.nameCard}} </span>
                                </p>
                                <p class="mb-0 my-2">
                                    <span class="fw-bold">Card No:</span>
                                    <span class="c-green"> {{numbercard}}</span>
                                </p>

                                <p class="mb-0 my-2">
                                    <span class="fw-bold">ExpiryDate :</span>
                                    <span class="c-green"> {{card.month}}/{{card.year}}</span>
                                </p>

                                <p class="mb-0 my-2">
                                    <span class="fw-bold">Email :</span>
                                    <span class="c-green"> {{card.emailAdd}} </span>
                                </p>
                                <p class="mb-0 my-2">
                                    <span class="fw-bold">Mobile No :</span>
                                    <span class="c-green">{{card.phoneNo}}</span>
                                </p>

                                <p class="mt-3">
                                    <button class="btn btn-success px-3 " @click="saveCardDetail">Save</button>
                                    <router-link to="/paymentsInfo"> <button class="btn btn-primary px-3 mx-3">Edit</button> </router-link>
                                </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="overlay" v-if="isLoading">
    <div  class="d-flex justify-content-center">
      <div class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>
    </div>
</div>
</template>

<script>
import {
    computed,
    onMounted,
    reactive
} from "vue";
import axios from "axios";
import router from '@/router';
export default {
    setup() {

        const card = reactive({
            nameCard: "",
            carNo: "",
            emailAdd: "",
            month: "",
            year: "",
            data: "",
            phoneNo: '',
            cardType: ""
        });

        let isLoading = false;

        const numbercard = computed(() => {
            return card.carNo ? card.carNo.match(/.{1,4}/g).join(' ') : '';
        })

        onMounted(() => {
            const cardInfo = localStorage.getItem('cardDetails')
            if (cardInfo) {
                let parsedData = JSON.parse(cardInfo);
                card.data = parsedData
                card.cardType = parsedData.cardType
                card.nameCard = parsedData.cardHolderName,
                card.carNo = parsedData.cardNumber,
                card.emailAdd = parsedData.emailAddress
                card.phoneNo = parsedData.phoneNo
                card.month = parsedData.expiryDate.month
                card.year = parsedData.expiryDate.year
            }
        });

        // const maskCardNo = (cardNumber) => {
        //     let maskedString = cardNumber.substr(0, 4) + ('' + cardNumber).slice(4, 12).replace(/./g, "*")
        //     + (' ' + cardNumber).slice(-4);
        //     return maskedString
        // }

        const saveCardDetail = async () => {
            localStorage.setItem("LatestCard", JSON.stringify(card.data));
            // const stringifiedObj = JSON.stringify(card.data)
            // const encodedData = btoa(JSON.stringify(card.data));
            isLoading = true;
            await axios.post("http://localhost:8085/createcard",card.data).then(response => {
                console.log(response);
                isLoading = false;
                localStorage.removeItem('cardDetails');
                router.push("/cardDetailsSuccess")
            }).catch(err => {
                console.log('api err', err)
                isLoading = false
            })
        }
        return {
            saveCardDetail,
            card,
            numbercard,
            isLoading
        };
    },
};
</script>

<style scoped>
.container {
    margin: 30px auto;
}

.visa {
    background: #06c1ff !important;
}

.master {
    background: #ffcd61d6 !important;
}

.container .card {
    width: 100%;
    box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
    background: #fff;
    border-radius: 0px;
    border: 0px !important;
}

.container .card .img-box {
    width: 80px;
    height: 50px;
}

.container .card img {
    width: 100%;
    object-fit: fill;
}

.container .card .number {
    font-size: 24px;
}

.box {
    height: 40px;
    width: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ddd;
}

.btn.btn-primary.payment {
    background-color: #1c6acf;
    color: white;
    border-radius: 0px;
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 24px;
}

.form__div {
    height: 50px;
    position: relative;
    margin-bottom: 24px;
}

.form-control {
    width: 100%;
    height: 45px;
    font-size: 14px;
    border: 1px solid #DADCE0;
    border-radius: 0;
    outline: none;
    padding: 2px;
    background: none;
    z-index: 1;
    box-shadow: none;
}

.form__label {
    position: absolute;
    left: 16px;
    top: 10px;
    background-color: #fff;
    color: #80868B;
    font-size: 16px;
    transition: .3s;
    text-transform: uppercase;
}

.form-control:focus+.form__label {
    top: -8px;
    left: 12px;
    color: #1A73E8;
    font-size: 12px;
    font-weight: 500;
    z-index: 10;
}

.form-control:not(:placeholder-shown).form-control:not(:focus)+.form__label {
    top: -8px;
    left: 12px;
    font-size: 12px;
    font-weight: 500;
    z-index: 10;
}

.form-control:focus {
    border: 1.5px solid #1A73E8;
    box-shadow: none;
}

 .overlay {
    position: absolute;
    width: 100%;
    background: rgba(0,0,0,0.7);
    height: 100%;
    z-index: 1000;
    top: 0%;
    left: 0px;
    opacity: 0.5;
    filter: alpha(opacity=50);
 }
</style>
