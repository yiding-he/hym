
export const EventBus = {
  onComponentUnmounted: (component: any) => {
    console.log('onComponentUnmounted', component)
  }
}