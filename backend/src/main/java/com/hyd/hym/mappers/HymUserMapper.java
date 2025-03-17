package com.hyd.hym.mappers;

import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HymUserMapper {

  @Select("""
    SELECT hym_user_id, user_name, password
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
    @Param("lastLoginIp")  String lastLoginIp
  );
}
