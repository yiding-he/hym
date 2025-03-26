package com.hyd.hym.events.handlers;

import com.hyd.hym.events.UserEvents;
import com.hyd.hym.mappers.HymUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventHandler {

  @Autowired
  private HymUserMapper hymUserMapper;

  @EventListener
  public void onUserLogin(UserEvents.UserLoggedIn event) {
    hymUserMapper.updateLastLoginTime(event.username(), event.ip());
  }
}
