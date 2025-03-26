package com.hyd.hym.jwtsecurity;

import com.hyd.hym.models.HymUser;
import org.springframework.web.context.request.RequestContextHolder;

public class UserContextHolder {

  public static void setCurrentUser(HymUser user) {
    RequestContextHolder.currentRequestAttributes().setAttribute("currentUser", user, 0);
  }

  public static HymUser getCurrentUser() {
    return (HymUser) RequestContextHolder.currentRequestAttributes().getAttribute("currentUser", 0);
  }
}
