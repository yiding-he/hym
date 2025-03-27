package com.hyd.hym.models;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 功能分类表
 */
@Data
public class HymFunctionCat {

  /**
   * 主键
   */
  private Long hymFunctionCatId;

  /**
   * 分类名称
   */
  private String categoryName;

  /**
   * 是否可见，0=不可见，1=可见
   */
  private Integer visible;

  /**
   * 创建时间
   */
  private LocalDateTime createdAt;

  /**
   * 修改时间
   */
  private LocalDateTime updatedAt;
}
