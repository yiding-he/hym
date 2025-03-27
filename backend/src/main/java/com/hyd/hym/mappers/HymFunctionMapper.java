package com.hyd.hym.mappers;

import com.hyd.hym.models.HymFunction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HymFunctionMapper {

    /**
     * 查询所有菜单功能
     *
     * @return 菜单功能列表
     */
    @Select("""
            SELECT
              `hym_function_id`, `function_name`, `function_code`, `page_name`,
              `category_id`, `created_at`, `updated_at`
            FROM `t_hym_function`
            """)
    List<HymFunction> selectAll();
}
