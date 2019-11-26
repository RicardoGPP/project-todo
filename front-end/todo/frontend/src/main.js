import Vue from 'vue'
import App from './App.vue'
import router from './router'

import TodoButton from '@/components/todo-button/TodoButton';
import TodoMessage from '@/components/todo-message/TodoMessage';

import "font-awesome/css/font-awesome.css";

Vue.config.productionTip = false

Vue.component('TodoButton', TodoButton);
Vue.component('TodoMessage', TodoMessage);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
