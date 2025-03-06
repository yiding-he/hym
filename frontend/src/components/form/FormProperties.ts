import {injectProperties} from "../../common/ComponentPropertiesFactory";

// 表单 Form 组件的属性。因为不是在组件内部定义的，所以 IDE 可能无法列举出来
export const formProperties = {
  labelWidth: {
    type: String,
    default: '100px'
  },
  labelAlign: {
    type: String,
    default: 'left'
  }
}

// 表单子组件调用，获取通过 provide 发布出来的表单属性
// 例如在子组件中写：
//   const { labelWidth, labelAlign } = injectFormProperties();
// 然后这两个属性就能在 template 中使用了
export function injectFormProperties() {
  return injectProperties(...Object.keys(formProperties))
}
