package com.hyd.hym.mappers;

import com.hyd.hybatis.pagination.PageCrudMapper;
import com.hyd.hym.models.HymRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HymRoleMapper extends PageCrudMapper<HymRole> {

}
