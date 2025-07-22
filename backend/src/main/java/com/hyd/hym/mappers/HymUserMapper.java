package com.hyd.hym.mappers;

import com.hyd.hym.database.Condition;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Operator;
import com.hyd.hym.database.Page;
import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@TableName("t_hym_user")
public interface HymUserMapper extends BaseMapper<HymUser> {

  default HymUser selectForUserLogin(String username) {
    return find(new Conditions()
      .projection("hym_user_id", "user_name", "password", "status")
      .addCondition(Condition.of("user_name", Operator.eq, username))
    );
  }

  @Update("""
    UPDATE t_hym_user
    SET last_login_time = NOW(),
        last_login_ip = #{lastLoginIp}
    WHERE user_name = #{username}""")
  void updateLastLoginTime(
    @Param("username") String username,
    @Param("lastLoginIp") String lastLoginIp
  );

}
