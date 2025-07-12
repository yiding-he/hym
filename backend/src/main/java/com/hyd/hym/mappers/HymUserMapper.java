package com.hyd.hym.mappers;

import com.hyd.hym.database.BaseProvider;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Page;
import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Mapper
public interface HymUserMapper extends BaseMapper {

  @Select("""
    SELECT hym_user_id, user_name, password, status
    FROM t_hym_user
    where user_name=#{username}""")
  HymUser selectForUserLogin(String username);

  @Update("""
    UPDATE t_hym_user
    SET last_login_time = NOW(),
        last_login_ip = #{lastLoginIp}
    WHERE user_name = #{username}""")
  void updateLastLoginTime(
    @Param("username") String username,
    @Param("lastLoginIp") String lastLoginIp
  );

  @Transactional
  default Page<HymUser> listUserPage(Conditions conditions) {
    return listPage(conditions.withTableName("t_hym_user"), this::listUser);
  }

  @SelectProvider(type = BaseProvider.class, method = "toSql")
  List<HymUser> listUser(Conditions conditions);

}
