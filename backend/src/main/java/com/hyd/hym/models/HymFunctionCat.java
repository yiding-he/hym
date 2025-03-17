package com.hyd.hym.models;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 菜单功能分类表
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
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    private LocalDateTime updatedAt;
}
