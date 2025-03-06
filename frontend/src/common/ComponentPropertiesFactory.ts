import {inject, provide} from "vue";

/**
 * 将组件属性通过 provide 发布出去
 * @param props 通过 defineProps 获得的组件属性，包含属性名和属性的实际值
 */
export function provideProperties(props: any) {
  for (const key in props) {
    provide(key, props[key]);
  }
}

/**
 * 获取父组件提供的属性，并一次返回
 * @param keys 属性名列表
 */
export function injectProperties(...keys: string[]) {
  return Object.fromEntries(keys.map(key => [key, inject(key)]));
}