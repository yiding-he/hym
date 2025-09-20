let credential = {
  defaultCredential: {
    user: 'Anonymous',
    token: ''
  },
  setUser: (/* string */user) => {
    localStorage.setItem('credential-user', user);
  },
  setToken: (/* string */token) => {
    localStorage.setItem('credential-token', token);
  },
  get: () => {
    let user = localStorage.getItem('credential-user');
    let token = localStorage.getItem('credential-token');
    if (!user || !token || user === credential.defaultCredential.user) {
      let cred = credential.defaultCredential;
      credential.setUser(cred.user);
      credential.setToken(cred.token);
      return cred;
    }
    return {
      user,
      token
    };
  },
  ifNotLoggedIn: (/* function */callback) => {
    let cred = credential.get();
    if (cred.user === credential.defaultCredential.user || !cred.token) {
      callback();
    }
  }
};

/**
 * 检查用户是否登录，每个页面加载时都要做这样的判断
 */
credential.ifNotLoggedIn(() => {
  if (window.currentPage === 'login') {
    return;
  }
  window.location.href = '/login.html';
})
