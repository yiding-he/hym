import {defineStore} from "pinia";

export const useUserStore = defineStore('user', {
  state: () => {
    return loadUser({
      userStatus: UserStatus.LOGGED_OUT,
      token: ''
    } as User);
  }, actions: {
    onChange() {
      localStorage.setItem('user', JSON.stringify(this));
    },
    setUserStatus(status: UserStatus) {
      this.userStatus = status;
      this.onChange();
    },
    setToken(token: string) {
      this.token = token;
      this.onChange();
    },
  }
});

// 用户状态
export enum UserStatus {
  LOGGED_OUT,
  LOGGED_IN,
}

// 用户信息，会被多个组件侦听，并且会保存到 localStorage
export type User = {
  userStatus: UserStatus;
  token: string;
}

// Store 初始化时 从 localStorage 加载用户信息
function loadUser(user: User): User {
  let finalUser = user;
  const userString = localStorage.getItem('user');
  const localStorageUser = userString ? JSON.parse(userString) : null;
  if (localStorageUser) {
    finalUser = localStorageUser
  }
  return finalUser
}
