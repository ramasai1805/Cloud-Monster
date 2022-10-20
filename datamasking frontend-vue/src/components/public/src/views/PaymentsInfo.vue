<template>
        <div class="card col-md-6 mt-3 container">
            <h5 class="card-header">
              Credit/Debit Card Details
            </h5>
            <div class="card-body">
                <form @submit.prevent="savePaymentInfo" class="row">
                <div class="mb-3 col-md-6">
                    <label for="cardNumber" class="form-label">Card Number</label>
                    <input type="text" v-model="cardDetails.cardNumber"  placeholder="16 digit number" class="form-control" id="cardNumber" />
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
                      <option v-for="month in months" :value="month" v-bind:key="month" >{{month}}</option>
                    </select>
                </div>
                <div class="mb-3 col-md-4">
                    <label for="expiryDate" class="form-label">&nbsp;&nbsp;</label>
                    <select id="inputState" class="form-select" v-model="cardDetails.expiryDate.year">
                      <option v-for="year in years" :value="year" v-bind:key="year">{{year}}</option>
                    </select>
                </div>
                <div class="mb-3 col-md-4">
                    <label for="cvvNumber" class="form-label">CVV</label>
                    <input type="text" v-model="cardDetails.cvv"  placeholder="3 digit number" class="form-control" id="cvvNumber" />
                </div>
                <div class="mb-3">
                    <label for="cardHolderName" class="form-label">Card Holder's Name</label>
                    <input type="text" v-model="cardDetails.cardHolderName" class="form-control" id="cardHolderName" />
                </div>
                <div class="mb-3 col-md-6">
                    <label for="emailAddress" class="form-label">Email Address</label>
                    <input type="email" v-model="cardDetails.emailAddress" class="form-control" id="emailAddress" required />
                </div>
                <div class="mb-3 col-md-6">
                    <label for="phoneNo" class="form-label">Phone No</label>
                    <input type="text" v-model="cardDetails.phoneNo" class="form-control" id="phoneNo" />
                </div>
                <div class="mb-3 col-md-3 mx-auto" /> 
                <div class="mb-3 col-md-3 mx-auto"> 
                  <button type="submit" class="btn btn-primary">Process</button>
                  <!-- <button type="button" class="btn btn-secondary">Cancel</button> -->
                </div>
                <div class="mb-3 col-md-3 mx-auto"> 
                  <!-- <button type="submit" class="btn btn-primary">Process</button> -->
                  <button type="button" class="btn btn-secondary">Cancel</button>
                </div>
                <div class="mb-3 col-md-3 mx-auto" /> 
                </form>
            </div>
        </div>
</template>

<script>
import {
    reactive
} from "@vue/reactivity";
import { onMounted } from 'vue'
export default ({
    setup() {
      onMounted(() => {
          const cardInfo = localStorage.getItem('cardDetails')
          if(cardInfo){
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
        
        const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
        const years = [2022,2023,2024,2025,2026,2027,2028,2029,2030]
        const savePaymentInfo = () => {
          localStorage.setItem('cardDetails', JSON.stringify(cardDetails))
        }
        return {
            cardDetails,
            months,
            years,
            savePaymentInfo
        }
    },
})
</script>
<style scoped>
.container {
  padding: 0;
  box-shadow: 0 5px 10px 0 rgb(0 0 0 / 10%);

}
</style>
