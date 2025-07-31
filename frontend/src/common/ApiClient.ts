import axios, {AxiosResponse} from 'axios';
import {UserStatus, useUserStore} from "./UserStore";

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
    const token = useUserStore().token;
    console.log("calling api with token: ", token);
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
    if (response.data && response.data.ok === false) {
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

  // 添加表单提交事件侦听
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

    if (this.method !== "GET" && this.method !== "POST") {
      throw new Error(`不支持的请求方法：${this.method}`);
    }

    try {
      const data = options.parameters;
      // 这里的 apiClient 是 AxiosInstance 对象
      if (this.method === "GET") {
        return await apiClient.get<req, resp>(this.name, {params: data});
      } else {
        return await apiClient.post<req, resp>(this.name, data);
      }
    } catch (e: any) {
      if (e.response?.status === 401) {
        const userStore = useUserStore();
        userStore.setUserStatus(UserStatus.LOGGED_OUT);
        userStore.setToken('')
        return new Promise(() => {
        })
      } else {
        throw e;
      }
    } finally {
      if (options.hooks) {
        options.hooks.filter(exists).map(hook => hook.onFinish).filter(exists).forEach(fn => fn?.());
      }
    }
  }
}

