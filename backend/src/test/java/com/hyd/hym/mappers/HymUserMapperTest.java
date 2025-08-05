package com.hyd.hym.mappers;

import com.hyd.hybatis.Conditions;
import com.hyd.hym.HymApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class HymUserMapperTest extends HymApplicationTest {

  @Autowired
  private HymUserMapper hymUserMapper;

  @Test
  void selectForUserLogin() {
    var hymUser = hymUserMapper.selectForUserLogin("admin");
    assertNotNull(hymUser);
    assertEquals("admin", hymUser.getUserName());
    System.out.println(hymUser);
  }

  @Test
  void testList() {
    var users = hymUserMapper.selectList(new Conditions()
        .withColumn("user_name").contains("admin")
        .withColumn("user_id").in(1, 2, 3, 4, 5)
        .withColumn("status").nonNull()
        .orderDesc("hym_user_id")
    );
    assertFalse(users.isEmpty());
    users.forEach(System.out::println);
  }

  @Test
  void testListPage() {
    var users = hymUserMapper.selectPage(
      new Conditions().withColumn("user_name").contains("admin"), 0, 10
    );
    assertFalse(users.getList().isEmpty());
    assertEquals(10, users.getPageSize());
    users.getList().forEach(System.out::println);
  }
}