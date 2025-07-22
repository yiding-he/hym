package com.hyd.hym.mappers;

import com.hyd.hym.database.BaseProvider;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Page;
import com.hyd.hym.models.HymRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@TableName("t_hym_role")
public interface HymRoleMapper extends BaseMapper<HymRole> {

}
