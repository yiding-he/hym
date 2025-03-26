package com.hyd.hym.database;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Conditions extends HashMap<String, Object> {

  private final List<Condition> conditions = new ArrayList<>();

  private final List<String> orderBy = new ArrayList<>();

  private final AtomicInteger counter = new AtomicInteger();

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
}
