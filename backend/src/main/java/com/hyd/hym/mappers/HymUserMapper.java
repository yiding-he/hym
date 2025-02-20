package com.hyd.hym.mappers;

import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HymUserMapper {

    @Select("""
            SELECT id, username, password
            FROM hym_user 
            where username=#{username}""")
    HymUser selectForUserLogin(String username);
}
