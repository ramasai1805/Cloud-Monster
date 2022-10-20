<template>
    <div class="col-md-6 mt-3">
    <button class="btn btn-primary mb-3 float-end" @click="downloadAllDetails" :disabled="isDownloading">
        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" v-if="isDownloading"></span>
        Download All Details</button>
    <div class="card container">
    <h5 class="card-header text-white bg-primary">
        Credit/Debit Card Details
    </h5>
    <div class="card-body">
        <form @submit.prevent="savePaymentInfo" class="row">
            <div class="mb-3 col-md-6">
                <label for="cardNumber" class="form-label">Card Number</label>
                <input type="text" pattern="\d*" maxlength="16" required v-model="cardDetails.cardNumber " placeholder="0000 0000 0000 0000" class="form-control" id="cardNumber" />
            </div>
            <div class="mb-3 col-md-6">
                <div>
                    <label for="cardNumber" class="form-label">Card Type</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" v-model="cardDetails.cardType" type="radio" value="Visa" name="flexRadioDefault" id="flexRadioDefault1" checked>
                    <label class="form-check-label" for="flexRadioDefault1">
                        Visa
                    </label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" v-model="cardDetails.cardType" value="Master Card" name="flexRadioDefault" id="flexRadioDefault2">
                    <label class="form-check-label" for="flexRadioDefault2">
                        Master Card
                    </label>
                </div>
            </div>
            <div class="mb-3 col-md-4 ">
                <label for="expiryDate" class="form-label">Expiry Date</label>
                <select id="inputState" class="form-select" v-model="cardDetails.expiryDate.month">
                    <option value="" selected disabled hidden>MM</option>
                    <option v-for="month in months" :value="month" v-bind:key="month">{{month}}</option>
                </select>
            </div>
            <div class="mb-3 col-md-4">
                <label for="expiryDate" class="form-label">&nbsp;&nbsp;</label>
                <select id="inputState" class="form-select" v-model="cardDetails.expiryDate.year">
                    <option value="" selected disabled hidden>YYYY</option>
                    <option v-for="year in years" :value="year" v-bind:key="year">{{year}}</option>
                </select>
            </div>
            <div class="mb-3 col-md-4">
                <label for="cvvNumber" class="form-label">CVV</label>
                <input type="text" pattern="\d*" maxlength="3" v-model="cardDetails.cvv" placeholder="000" class="form-control" id="cvvNumber" required/>
            </div>
            <div class="mb-3">
                <label for="cardHolderName" class="form-label">Card Holder's Name</label>
                <input type="text" v-model="cardDetails.cardHolderName" class="form-control" id="cardHolderName" required />
            </div>
            <div class="mb-3 col-md-6">
                <label for="emailAddress" class="form-label">Email Address</label>
                <input type="email" v-model="cardDetails.emailAddress" class="form-control" id="emailAddress" required />
            </div>
            <div class="mb-3 col-md-6">
                <label for="phoneNo" class="form-label">Phone No</label>
                <input type="text" pattern="\d*" maxlength="10" v-model="cardDetails.phoneNo" class="form-control" id="phoneNo" required />
            </div>

            <div class="d-flex justify-content-center mt-3">
                <button type="submit" class="btn btn-primary">Process</button>
                <button type="button" class="btn btn-secondary mx-3" @click="removeCardDetail">Cancel</button>
            </div>

        </form>
    </div>
    </div>
    </div>
</template>

<script>
import {
    reactive
} from "@vue/reactivity";
import axios from "axios";
import {
    computed,
    onMounted
} from 'vue'
import {
    useRouter
} from "vue-router";
export default ({
    setup() {

        const router = useRouter()
        onMounted(() => {
            const cardInfo = localStorage.getItem('cardDetails')
            if (cardInfo) {
                let parsedData = JSON.parse(cardInfo);
                cardDetails.cardNumber = parsedData.cardNumber
                cardDetails.cardHolderName = parsedData.cardHolderName
                cardDetails.cardType = parsedData.cardType
                cardDetails.expiryDate.month = parsedData.expiryDate.month
                cardDetails.expiryDate.year = parsedData.expiryDate.year
                cardDetails.cvv = parsedData.cvv
                cardDetails.emailAddress = parsedData.emailAddress
                cardDetails.phoneNo = parsedData.phoneNo
            }
        })

        const formatCardNumber = computed(() => {
            return cardDetails.cardNumber ? (cardDetails.cardNumber.replace(/ /g, '')).match(/.{1,4}/g).join(' ') : '';
        })
        let cardDetails = reactive({
            cardNumber: "",
            cardHolderName: "",
            cardType: "Visa",
            expiryDate: {
                month: "",
                year: ""
            },
            cvv: "",
            emailAddress: "",
            phoneNo: ""
        });

        let isDownloading = reactive(false)

        const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
        const years = [2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030]
        const savePaymentInfo = () => {
            if (cardDetails.cardNumber === "" || cardDetails.cardHolderName === "" || cardDetails.cardType === "", cardDetails.expiryDate.month === "" || cardDetails.expiryDate.year === "" || cardDetails.cvv === "" || cardDetails.emailAddress === "", cardDetails.phoneNo === "") return false
            localStorage.setItem('cardDetails', JSON.stringify(cardDetails))
            router.push("/paymentPreview")
        } 

        const removeCardDetail = ()=>{
           localStorage.removeItem("cardDetails")
           window.location.reload()
        }

        const downloadAllDetails = () => {
            const link = document.createElement("a");
            const url = "http://localhost:8085/pdf";
            const type= "application/pdf";
            link.target = "_blank";
            link.download = "details.pdf";
            isDownloading = true
            return axios.request({url, method: 'GET', responseType: 'blob'})
              .then((res) => {
                link.href = URL.createObjectURL(
                  new Blob([res.data], {type: type})
                );
                link.click();
                isDownloading = false

              })
              .catch((error) => {
                isDownloading = false
                console.error(error);
                
              })
        }

        return {
            cardDetails,
            months,
            years,
            savePaymentInfo,
            formatCardNumber,
            removeCardDetail,
            downloadAllDetails,
            isDownloading
        }
    },
})
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

.card-header:first-child {
    border-radius: calc(0.25rem - 1px) calc(0.25rem - 1px) 0 0;
}

.card-header {
    padding: .75rem 1.25rem;
    margin-bottom: 0;
    background-color: #f0f3f5;
    border-bottom: 1px solid #c8ced3;
}

.card-body {
    flex: 1 1 auto;
    padding: 1.25rem;
}

.form-control:focus {
    color: #5c6873;
    background-color: #fff;
    border-color: #c8ced3 !important;
    outline: 0;
    box-shadow: 0 0 0 #F44336;
}
</style>
