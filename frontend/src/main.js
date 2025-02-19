import {createApp} from 'vue';
import App from './App.vue';
import {FontAwesomeIcon} from "./common/FontAwesome.js";
import {ApiClient} from "./common/ApiClient.js"; // 引入 mockSetup.js 以设置模拟后端

ApiClient.setApiUrl(document
  .querySelector('meta[name="api-url"]')
  .getAttribute('content'));

const app = createApp(App);
app.component('font-awesome-icon', FontAwesomeIcon);
app.mount('#app');