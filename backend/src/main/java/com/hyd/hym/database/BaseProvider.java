package com.hyd.hym.database;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class BaseProvider {

  @SuppressWarnings("unused")
  public String toSql(Conditions conditions) {
    var sql = new StringBuilder(getSqlStart(conditions));
    if (conditions.hasCondition()) {
      sql.append(" where ");
      for (Condition condition : conditions.getConditions()) {
        if (!validateConditionValues(condition.getValues())) {
          continue;
        }
        sql.append(condition.getColumnName()).append(" ").append(condition.getOperator().getCode()).append(" ");
        if (condition.getOperator() == Operator.in) {
          sql.append("(");
          for (Object value : condition.getValues()) {
            sql.append(conditions.param(value)).append(",");
          }
          sql.deleteCharAt(sql.length() - 1);
          sql.append(")");
        } else {
          sql.append(conditions.param(condition.getValues().getFirst()));
        }
        sql.append(" and ");
      }
      sql.delete(sql.length() - 4, sql.length());
    }
    if (conditions.hasOrderBy()) {
      sql.append(" order by ");
      for (String orderBy : conditions.getOrderBy()) {
        sql.append(orderBy).append(",");
      }
      sql.deleteCharAt(sql.length() - 1);
    }
    log.info("SQL: \n{}\n{}", sql, conditions);
    return sql.toString().trim();
  }

  private boolean validateConditionValues(List<Object> values) {
    return values != null
      && !values.isEmpty()
      && !values.getFirst().equals("")
      && !values.stream().allMatch(Objects::isNull);
  }

  protected abstract String getSqlStart(Conditions conditions);

}
