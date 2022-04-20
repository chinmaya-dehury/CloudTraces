import Vue from 'vue';
import App from './App.vue';
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue';
import router from './router';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<router-view></router-view>'
});
