package com.hyd.hym.models;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 功能表
 */
@Data
public class HymFunction {

  /**
   * 主键
   */
  private Long hymFunctionId;

  /**
   * 功能名称
   */
  private String functionName;

  /**
   * 功能代码
   */
  private String functionCode;

  /**
   * 页面名称
   */
  private String pageName;

  /**
   * 功能类别ID
   */
  private Long categoryId;

  /**
   * 创建时间
   */
  private LocalDateTime createdAt;

  /**
   * 修改时间
   */
  private LocalDateTime updatedAt;
}
