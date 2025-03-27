package com.hyd.hym.mappers;

import com.hyd.hym.HymApplicationTest;
import com.hyd.hym.database.Condition;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Operator;
import com.hyd.hym.models.HymUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HymUserMapperTest extends HymApplicationTest {

  @Autowired
  private HymUserMapper hymUserMapper;

  @Test
  void selectForUserLogin() {
    var hymUser = hymUserMapper.selectForUserLogin("admin");
    assertNotNull(hymUser);
    assertEquals("admin", hymUser.getUserName());
  }

  @Test
  void testListUser() {
    var users = hymUserMapper.listUser(new Conditions()
      .addCondition(Condition.of(HymUser::getUserName, Operator.like, "%admin%"))
      .addCondition(Condition.of(HymUser::getHymUserId, Operator.in, List.of(1, 2, 3, 4, 5)))
      .addCondition(Condition.of("status", Operator.eq, (Integer) null))
      .addOrderBy("hym_user_id DESC")
    );
    assertFalse(users.isEmpty());
    users.forEach(System.out::println);
  }
}