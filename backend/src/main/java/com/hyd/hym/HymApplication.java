package com.hyd.hym;

import com.hyd.hybatis.HybatisConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan("com.hyd.hym.mappers")
@EnableConfigurationProperties(HymConfig.class)
@Import(HybatisConfigurator.class)
public class HymApplication {

  /**
   * 定义全局的密码编码器
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public static void main(String[] args) {
        SpringApplication.run(HymApplication.class, args);
    }
}
