package com.hyd.hym.mappers;

import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HymUserMapper {

  @Select("""
    SELECT hym_user_id, user_name, password
    FROM t_hym_user
    where user_name=#{username}""")
  HymUser selectForUserLogin(String username);
}
