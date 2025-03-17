import {defineStore} from 'pinia';

type EventHandler = (...payload: any[]) => void;

// 扫描并注册 components/page 目录下所有的 *Page.vue 文件
// @ts-ignore
const pageFiles = import.meta.glob('../components/page/content/*Page.vue');
const pageRegistry: Record<string, () => Promise<any>> = {
};
const pageCache: Record<string, any> = {};
for (const path in pageFiles) {
  const pageName = path.split('/').pop()!.replace('.vue', '');
  pageRegistry[pageName] = pageFiles[path];
}

// 定义事件类型
export enum EventType {
  LoadPageStarted, LoadPageFinished
}

export const contentNavigator = defineStore('contentPaneHolder', {
  state: () => ({
    eventHandlers: {} as Record<EventType, EventHandler[]>,
    currentPage: '' as string,
  }),
  actions: {
    on(type: EventType, handler: EventHandler) {
      if (!this.eventHandlers[type]) {
        this.eventHandlers[type] = [];
      }
      this.eventHandlers[type].push(handler);
    },
    emit(type: EventType, ...payload: any[]) {
      if (this.eventHandlers[type]) {
        this.eventHandlers[type].forEach(handler => handler(...payload));
      }
    },
    onPageLoadStarted(handler: EventHandler) {
      this.on(EventType.LoadPageStarted, handler);
    },
    onPageLoadFinished(handler: EventHandler) {
      this.on(EventType.LoadPageFinished, handler);
    },
    navigateTo(pageName: string) {
      console.log(`Current page is ${this.currentPage} and navigate to ${pageName}`)
      if (this.currentPage === pageName) {
        console.log('Already on page', pageName);
        return;
      }

      if (!pageRegistry[pageName]) {
        pageName = "PageNotFoundPage"
      }

      history.pushState({}, '', `#${pageName}`);
      this.emit(EventType.LoadPageStarted, pageName);

      if (pageCache[pageName]) {
        this.currentPage = pageName;
        this.emit(EventType.LoadPageFinished, pageName, pageCache[pageName]);
      } else {
        const componentLoader = pageRegistry[pageName];
        if (componentLoader) {
          this.currentPage = pageName;
          componentLoader().then(({default: _page}) => {
            pageCache[pageName] = _page;
            this.emit(EventType.LoadPageFinished, pageName, _page);
          });
        }
      }
    },
    updatePageByHash() {
      const hash = window.location.hash;
      const pageName = (hash? hash.substring(1): "") || "DashboardPage";
      this.navigateTo(pageName)
    },
    init() {
      window.addEventListener('hashchange', this.updatePageByHash)
      this.currentPage = '';
      this.updatePageByHash()
    }
  }
})
