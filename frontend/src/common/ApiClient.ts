import axios, {AxiosResponse} from 'axios';
import {AppConfig} from "./AppConfig";

const apiClient = axios.create();
const ApiClient = {
  value: apiClient,
  apiUrl: null as string | null,
  setApiUrl: function (url: string) {
    ApiClient.apiUrl = url;
  },
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
      return Promise.reject(response.data);
    }
    return Promise.resolve(response.data.data);
  },
  error => {
    return Promise.reject(error);
  }
);

export {ApiClient, apiClient};

//////////////////////////////

/**
 * 接口定义，包含接口的参数类型和返回值类型，并且提供了一个 call 方法，用于调用接口。
 */
export class ApiType<req, resp> {
  constructor(private name: string, private method: string) {
  }

  call(data: req): Promise<resp> {
    // 这里的 apiClient 是 AxiosInstance 对象
    if (this.method !== "POST") {
      return apiClient.get<req, resp>(this.name, {data: JSON.stringify(data)})
    } else {
      return apiClient.post<req, resp>(this.name, data)
    }
  }
}

/**
 * 定义所有的接口，每个接口都是一个 ApiType 对象。使用方法：
 *   ApiList.Login.call({
 *     // 这里的参数属性名是可以在 IDE 中提示的
 *     username: "admin", password: "123456"
 *   }).then(response => {
 *     // 处理返回值，返回值的属性名也是可以在 IDE 中提示的
 *     const token = response.token;
 *   }).catch(error => {
 *     // 处理错误
 *   })
 */
export const ApiList = {

  // 加载站点全局配置
  InitConfig: new ApiType<
    {},
    AppConfig
  >("/init-config", "GET"),

  // 登录接口
  Login: new ApiType<
    { username: string, password: string },
    { token: string }
  >("/login", "POST"),

}

