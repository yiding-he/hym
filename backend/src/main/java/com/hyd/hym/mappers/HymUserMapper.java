package com.hyd.hym.mappers;

import com.hyd.hybatis.Conditions;
import com.hyd.hybatis.pagination.PageCrudMapper;
import com.hyd.hym.models.HymUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HymUserMapper extends PageCrudMapper<HymUser> {

  default HymUser selectForUserLogin(String username) {
    return selectOne(new Conditions()
      .projection("hym_user_id", "user_name", "password", "status")
      .withColumn("user_name").eq(username)
    );
  }

  default void updateLastLoginTime(
    @Param("username") String username,
    @Param("lastLoginIp") String lastLoginIp
  ) {
    var updateObj = new HymUser();
    updateObj.setLastLoginTime(null);
    updateObj.setLastLoginIp(lastLoginIp);
    update(Conditions.eq("user_name", username), updateObj);
  }

  int insert(HymUser hymUser);
}
