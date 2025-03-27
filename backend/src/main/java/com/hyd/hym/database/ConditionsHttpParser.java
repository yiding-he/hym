package com.hyd.hym.database;

import java.util.Map;
import java.util.stream.Stream;

public class ConditionsHttpParser {

  public static Conditions parse(Map<String, String> allParams) {
    // 参数中 key 的格式为 $ 分开的两部分，左边是字段名，右边是操作符，如 "status$eq"
    // 如果没有操作符，则默认为 eq。
    // 对于每个参数都构建一个 Condition 对象，然后添加到 Conditions 中。
    // 如果操作符为 in 或 nin，则该字段的值自动根据 "," 分隔符进行拆分成集合对象。
    // 例如参数为 { status$eq=1, id$in=1,2,3 }，则构建 Condition 对象为
    // Condition.of("status", Operator.eq, 1),
    // Condition.of("id", Operator.in, [1, 2, 3])

    Conditions conditions = new Conditions();

    for (Map.Entry<String, String> entry : allParams.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      String[] parts = key.split("\\$");
      String propName = parts[0];
      String operatorStr = parts.length > 1 ? parts[1] : "eq";

      Condition condition;
      Operator operator = Operator.valueOf(operatorStr);
      if (operator == Operator.in || operator == Operator.nin) {
        condition = Condition.of(propName, operator, Stream.of(value.split(",")).toList());
      } else {
        condition = Condition.of(propName, operator, value);
      }

      conditions.addCondition(condition);
    }

    return conditions;
  }
}
