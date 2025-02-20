package com.hyd.hym.jwtsecurity;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hyd.hym.events.UserUpdated;
import com.hyd.hym.mappers.HymUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 实现 Spring Security 的 UserDetailsService 接口，
 * 用于从数据库中加载用户信息。这里使用了 Caffeine 缓存来提高性能。
 * 当用户信息发生变化时，会触发 {@link #onUserUpdated(UserUpdated)} 方法，
 * 从而清除对应的缓存。
 */
@Service
public class HymUserDetailService implements UserDetailsService {

  @Autowired
  HymUserMapper hymUserMapper;

  private final Cache<String, HymUserDetail> userCache = Caffeine.newBuilder()
    .maximumSize(1000)
    .expireAfterWrite(10, MINUTES)
    .build();

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userCache.get(username, __ -> {
      var hymUser = hymUserMapper.selectForUserLogin(__);
      return hymUser == null ? null : new HymUserDetail(hymUser);
    });
  }

  @EventListener
  public void onUserUpdated(UserUpdated event) {
    userCache.invalidate(event.username());
  }
}
