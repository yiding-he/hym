package com.hyd.hym.mappers;

import com.hyd.hym.models.HymFunctionCat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HymFunctionCatMapper {

    /**
     * 查询所有菜单功能分类
     *
     * @return 菜单功能分类列表
     */
    @Select("""
      SELECT
        `hym_function_cat_id`, `category_name`, `created_at`, `updated_at`
      FROM
        `t_hym_function_cat`""")
    List<HymFunctionCat> selectAll();
}
