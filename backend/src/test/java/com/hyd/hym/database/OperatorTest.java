package com.hyd.hym.database;

import com.hyd.hym.HymException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class OperatorTest {

  @Test
  public void testParseError() {
    try {
      Operator.of("aaaaa");
      fail("这里应该抛出异常");
    } catch (HymException e) {
      System.err.println(e);
    }
  }
}