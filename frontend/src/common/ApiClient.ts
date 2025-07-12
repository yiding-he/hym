import axios, {AxiosResponse} from 'axios';
import {AppConfig} from "./AppConfig";
import {User, useUserStore} from "./UserStore";
import {Store} from 'pinia';
import {PageResult} from "../components/table/DataTableCore";

const apiClient = axios.create();
const ApiClient = {
  value: apiClient,
  apiUrl: null as string | null,
  setApiUrl: function (url: string) {
    ApiClient.apiUrl = url;
  },
}

let userStore: Store<"user", User> | null = null;

// 拦截请求路径，添加前缀
apiClient.interceptors.request.use(
  config => {
    const originalUrl = config.url;
    if (config.url?.startsWith("/")) {
      config.url = ApiClient.apiUrl + config.url;
    }
    if (userStore == null) {
      userStore = useUserStore();
    }
    const token = userStore.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
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

type FunctionCategory = {
  title: string,
  functions: Function[],
}

type Function = {
  title: string,
  pageName: string,
}

//////////////////////////////

/**
 * 接口定义，包含接口的参数类型和返回值类型，并且提供了一个 call 方法，用于调用接口。
 */
export class ApiType<req, resp> {
  constructor(private name: string, private method: string) {
  }

  call(data: req, target: HTMLElement | null = null): Promise<resp> {
    // 这里的 apiClient 是 AxiosInstance 对象
    if (target) {
      target.setAttribute('disabled', 'disabled');
    }
    if (this.method === "GET") {
      return apiClient
        .get<req, resp>(this.name, {params: data})
        .finally(() => {target?.removeAttribute('disabled')});
    } else if (this.method === "POST") {
      return apiClient
        .post<req, resp>(this.name, data)
        .finally(() => {target?.removeAttribute('disabled')});
    } else {
      throw new Error(`不支持的请求方法：${this.method}`);
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

  // 获取功能菜单
  GetFunctions: new ApiType<
    {},
    {
      functions: FunctionCategory[],
    }
  >("/functions", "GET"),

  // 查询用户列表
  GetUserList: new ApiType<
    Object,
    PageResult
  >("/user/list", "GET"),

  // 查询角色列表
  GetRoleList: new ApiType<
    Object,
    PageResult
  >("/role/list", "GET"),
}

