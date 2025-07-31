import {AppConfig} from "./AppConfig";
import {PageResult} from "../components/table/DataTableCore";
import {ApiType} from "./ApiClient";

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
      functions: Array<{
        title: string,
        functions: Array<{
          title: string, pageName: string
        }>,
      }>,
    }
  >("/functions", "GET"),

  // 查询用户列表
  GetUserList: new ApiType<
    Object,
    PageResult
  >("/user/list", "GET"),

  // 添加用户
  AddUser: new ApiType<
    { userName: string, mobile: string, email: string},
    {}
  >("/user/add", "POST"),

  // 查询角色列表
  GetRoleList: new ApiType<
    Object,
    PageResult
  >("/role/list", "GET"),
}