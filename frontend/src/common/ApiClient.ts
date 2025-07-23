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

export type CallHook = {
  onStart?: () => void,
  onFinish?: () => void,
}

export class CallOptions {
  // 定义类的属性，使用可选参数
  constructor(
    public parameters?: any,
    public hooks: CallHook[] = []
  ) {
  }

  static of(parameters: any): CallOptions {
    return new CallOptions(parameters);
  }

  apply(func: ((o: CallOptions) => void) | undefined): CallOptions {
    if (func) {
      func(this);
    }
    return this;
  }

  // 添加 addHook 方法
  addHook(hook: CallHook): this {
    if (hook) {
      this.hooks.push(hook);
    }
    return this;
  }

  // 设置当调用接口时，将指定的 HTML 元素设置为禁用状态，并在调用结束后恢复可用
  addElementsDisabledWhenCalling(target: HTMLElement[] | null = null): this {
    if (target == null || target.length === 0) {
      return this;
    }
    this.addHook({
      onStart: () => {
        if (target) {
          target.forEach(element => {
            if (element) {
              element.setAttribute('disabled', 'disabled');
            }
          });
        }
      },
      onFinish: () => {
        if (target) {
          target.forEach(element => {
            if (element) {
              element.removeAttribute('disabled');
            }
          });
        }
      }
    });
    return this;
  }
}

//////////////////////////////

/**
 * 接口定义，包含接口的参数类型和返回值类型，并且提供了一个 call 方法，用于调用接口。
 */
export class ApiType<req, resp> {
  constructor(private name: string, private method: string) {
  }

  async call(data: req, target: HTMLElement | null = null): Promise<resp> {
    return this.callByOptions(CallOptions.of(data).addHook({
      onStart: () => {
        if (target) {
          target.setAttribute('disabled', 'disabled');
        }
      },
      onFinish: () => {
        if (target) {
          target.removeAttribute('disabled');
        }
      }
    }))
  }

  async callByOptions(options: CallOptions): Promise<resp> {
    const exists = (obj: any): boolean => obj;
    if (options.hooks) {
      options.hooks.filter(exists).map(hook => hook.onStart).filter(exists).forEach(fn => fn?.());
    }

    try {
      const data = options.parameters;
      // 这里的 apiClient 是 AxiosInstance 对象
      if (this.method === "GET") {
        return await apiClient.get<req, resp>(this.name, {params: data});
      } else if (this.method === "POST") {
        return await apiClient.post<req, resp>(this.name, data);
      } else {
        throw new Error(`不支持的请求方法：${this.method}`);
      }
    } finally {
      if (options.hooks) {
        options.hooks.filter(exists).map(hook => hook.onFinish).filter(exists).forEach(fn => fn?.());
      }
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
    { functions: FunctionCategory[], }
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

