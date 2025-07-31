package com.hyd.hym.mappers;

import com.hyd.hym.models.HymRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("t_hym_role")
public interface HymRoleMapper extends BaseMapper<HymRole> {

}
