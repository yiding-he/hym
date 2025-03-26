package com.hyd.hym.mappers;

import com.hyd.hym.database.BaseProvider;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.events.UserEvents;
import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.*;
import org.springframework.context.event.EventListener;

import java.util.List;

@Mapper
public interface HymUserMapper {

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

  @SelectProvider(type = ListUserProvider.class, method = "toSql")
  List<HymUser> listUser(Conditions conditions);

  class ListUserProvider extends BaseProvider {

    @Override
    protected String getSqlStart(Conditions conditions) {
      return "select * from t_hym_user";
    }
  }
}
