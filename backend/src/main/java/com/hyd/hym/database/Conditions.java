package com.hyd.hym.database;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Conditions extends HashMap<String, Object> {

  private final List<Condition> conditions = new ArrayList<>();

  private final List<String> orderBy = new ArrayList<>();

  private final AtomicInteger counter = new AtomicInteger();

  private boolean withRowCount = false;

  @Setter
  private int pageSize = 10;

  @Setter
  private int pageIndex = 0;

  public Conditions withRowCount() {
    this.withRowCount = true;
    return this;
  }

  public Conditions addCondition(Condition condition) {
    this.conditions.add(condition);
    return this;
  }

  public boolean hasCondition() {
    return !this.conditions.isEmpty();
  }

  public Conditions addOrderBy(String orderBy) {
    this.orderBy.add(orderBy);
    return this;
  }

  public boolean hasOrderBy() {
    return !this.orderBy.isEmpty();
  }

  public String param(Object paramValue) {
    var paramName = "p" + this.counter.getAndIncrement();
    this.put(paramName, paramValue);
    return "#{" + paramName + "}";
  }

  public Conditions withTableName(String tableName) {
    this.put("__tableName", tableName);
    return this;
  }

  public String getTableName() {
    return (String) this.get("__tableName");
  }
}
