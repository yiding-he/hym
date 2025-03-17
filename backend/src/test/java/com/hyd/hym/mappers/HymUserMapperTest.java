package com.hyd.hym.mappers;

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
  }
}