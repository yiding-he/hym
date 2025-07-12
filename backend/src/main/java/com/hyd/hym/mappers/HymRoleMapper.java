package com.hyd.hym.mappers;

import com.hyd.hym.database.BaseProvider;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Page;
import com.hyd.hym.models.HymRole;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HymRoleMapper extends BaseMapper {

  @SelectProvider(type = BaseProvider.class, method = "toSql")
  List<HymRole> listRole(Conditions conditions);

  @Transactional
  default Page<HymRole> listRolePage(Conditions conditions) {
    return listPage(conditions.withTableName("t_hym_role"), this::listRole);
  }
}
