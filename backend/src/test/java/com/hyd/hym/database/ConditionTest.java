package com.hyd.hym.database;

import com.hyd.hym.models.HymUser;
import org.junit.jupiter.api.Test;

public class ConditionTest {

  @Test
  void test() throws Exception {
    var condition = Condition.of(HymUser::getHymUserId, Operator.eq, 1);
    System.out.println(condition);
  }
}
