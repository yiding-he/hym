// 表单 Field 组件的属性。因为不是在组件内部定义的，所以 IDE 可能无法列举出来
import {injectProperties} from "../../common/ComponentPropertiesFactory";
import {PropType} from "vue";

export type FieldValueType = string | number | boolean;
export const fieldProperties = {
  label: {
    type: String,
    default: undefined
  },
  description: {
    type: String,
    default: undefined
  },
  disabled: {
    type: Boolean,
    default: false
  },
  maxLength: {
    type: Number,
    default: undefined
  },
  value: {
    type: [String, Number, Boolean] as PropType<FieldValueType>,
    default: undefined
  }
}

/**
 * 一个 Field 下面的子组件可以通过这个方法来获得 Field 层面定义的属性值
 */
export function injectFieldProperties() {
  return injectProperties(...Object.keys(fieldProperties))
}

/**
 * 给所有的 Field 组件添加公共的方法，供外层调用
 * @param element 组件内的含值 DOM 元素
 * @param functions 组件特有的方法
 */
export function exposeFieldFunctions(element: HTMLElement | null, functions: any = null) {
  if (!element) {
    return;
  }
  defineExpose({
    focus: () => {
      element.focus();
    }
  })

  if (!functions) {
    return;
  }
  defineExpose(functions);
}

