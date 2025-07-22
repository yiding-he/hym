package com.hyd.hym.database;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Conditions extends HashMap<String, Object> {

  private final List<String> projection = new ArrayList<>();

  private final List<Condition> conditions = new ArrayList<>();

  private final List<String> orderBy = new ArrayList<>();

  private final AtomicInteger counter = new AtomicInteger();

  private boolean withRowCount = false;

  private int pageSize = 10;

  private int pageIndex = 0;

  public Conditions withRowCount() {
    this.withRowCount = true;
    return this;
  }

  public Conditions projection(String... projection) {
    Collections.addAll(this.projection, projection);
    return this;
  }

  public Conditions page(int pageIndex, int pageSize) {
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
    this.withRowCount = true;
    return this;
  }

  public Conditions pageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
    this.withRowCount = true;
    return this;
  }

  public Conditions pageSize(int pageSize) {
    this.pageSize = pageSize;
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
