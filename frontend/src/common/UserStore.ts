import {defineStore} from "pinia";

// FIXME: 我尝试了很久都不知道为什么
//        watch(userStore.userStatus, (newValue) => {}) 不起作用，
//        只能用 userStore.$subscribe 来实现侦听属性变化。
export const useUserStore = defineStore('user', {
  state: () => {
    return loadUser({
      userStatus: UserStatus.LOGGED_OUT,
      token: ''
    } as User);
  }, actions: {
    setUserStatus(status: UserStatus) {
      this.userStatus = status;
    },
    setToken(token: string) {
      this.token = token;
    },
  }
});

// 用户状态
export enum UserStatus {
  LOGGED_OUT,
  LOGGED_IN,
}

// 用户信息，会被多个组件侦听，并且会保存到 localStorage
type User = {
  userStatus: UserStatus;
  token: string;
}

// Store 初始化时 从 localStorage 加载用户信息
function loadUser(user: User): User {
  const userString = localStorage.getItem('user');
  const localStorageUser = userString ? JSON.parse(userString) : null;
  if (localStorageUser) {
    user = {...localStorageUser}
  }
  return user
}

// App 初始化时调用这个方法来将 userStore 同步到 localStorage
export function createUserStoreSyncToLocalStorage() {
  const userStore = useUserStore();
  userStore.$subscribe((mutation, state) => {
    localStorage.setItem('user', JSON.stringify(state));
  })
}

