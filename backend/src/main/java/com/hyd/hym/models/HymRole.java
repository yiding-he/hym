package com.hyd.hym.models;

import com.hyd.hybatis.annotations.HbEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色表
 */
@Data
@HbEntity(table = "t_hym_role")
public class HymRole {

    /**
     * 主键
     */
    private Long hymRoleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    private LocalDateTime updatedAt;
}
