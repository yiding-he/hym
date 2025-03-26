package com.hyd.hym.events;

public interface UserEvents {

  record UserUpdated(String username) {
  }

  record UserLoggedIn(String username, String ip) {
  }
}
