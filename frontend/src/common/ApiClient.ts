import axios, {AxiosResponse} from 'axios';

const apiClient = axios.create();
const ApiClient = {
  value: apiClient,
  apiUrl: null as string | null,
  setApiUrl: function (url: string) {
    ApiClient.apiUrl = url;
  },
  hasApiUrl: function () {
    return ApiClient.apiUrl !== null;
  }
}

// 拦截请求路径，添加前缀
apiClient.interceptors.request.use(
  config => {
    if (config.url?.startsWith("/")) {
      config.url = ApiClient.apiUrl + config.url;
    }
    console.log("Requesting URL: " + config.url)
    return config;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

// 拦截请求返回值
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    if (response.data && response.data.success === false) {
      // 如果 success 属性为 false，返回一个被拒绝的 Promise 对象
      return Promise.reject(response.data);
    }
    // 否则，返回 response.data
    return response.data;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

export {ApiClient, apiClient};
