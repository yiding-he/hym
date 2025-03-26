package com.hyd.hym.models;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
public class HymUser {

  public static final int STATUS_NORMAL = 1;
  public static final int STATUS_DISABLED = 2;

  /**
   * 主键
   */
  private Long hymUserId;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 加盐密码
   */
  private String password;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 状态，1=正常，2=禁用
   */
  private Integer status;

  /**
   * 最后登录时间
   */
  private LocalDateTime lastLoginTime;

  /**
   * 最后登录IP
   */
  private String lastLoginIp;

  /**
   * 创建时间
   */
  private LocalDateTime createdAt;

  /**
   * 修改时间
   */
  private LocalDateTime updatedAt;
}