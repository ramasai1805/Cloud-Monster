<template>
  <HeaderView />
  <div class="app py-5">
    <div class="container">
      <div class="row justify-content-center">
        <router-view />
      </div>
    </div>
  </div>

  <FooterView />
</template>
  
  <script>
import HeaderView from "./components/HeaderView.vue";
import FooterView from "./components/FooterView.vue";
export default {
  name: "App",
  components: {
    HeaderView,
    FooterView,
  },
};
</script>
  
  <style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.app {
  padding-bottom: 50px;
  background: url(https://www.nisum.com/hubfs/Gernal-webp/nisum-gradient-global-career.webp);
  background-position: left top;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
</style>
  