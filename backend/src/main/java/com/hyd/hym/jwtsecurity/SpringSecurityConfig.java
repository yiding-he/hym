package com.hyd.hym.jwtsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception {
    return c.getAuthenticationManager();
  }

  /**
   * 定义全局的密码编码器
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    HymUserDetailService hymUserDetailService,
    PasswordEncoder passwordEncoder
  ) throws Exception {

    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(hymUserDetailService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    http
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(cnf -> cnf
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authenticationProvider(authenticationProvider)
      .authorizeHttpRequests(requests -> requests
        .requestMatchers(
          new AntPathRequestMatcher("/**/login"),
          new AntPathRequestMatcher("/**/init-config"),
          new AntPathRequestMatcher("/**/error")
        ).permitAll()
        .anyRequest().authenticated()
      );

    return http.build();
  }
}
