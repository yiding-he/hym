import {createApp} from 'vue';
import App from './App.vue';
import {FontAwesomeIcon} from "./common/FontAwesome.js";
import {ApiClient} from "./common/ApiClient.js";
import {createPinia} from "pinia"; // 引入 mockSetup.js 以设置模拟后端

// 在 config.js 中配置后端 API 的路径前缀
ApiClient.setApiUrl(window.appConfig.apiRoot);

const app = createApp(App);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(createPinia())
app.mount('#app');