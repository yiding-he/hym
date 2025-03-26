package com.hyd.hym.database;

import com.hyd.hym.constants.HymError;
import lombok.Getter;

@Getter
public enum Operator {

  eq("="),

  neq("!="),

  gt(">"),

  gte(">="),

  lt("<"),

  lte("<="),

  like("LIKE"),

  in("IN"),

  nin("NOT IN"),

  isnull("IS NULL"),

  notnull("IS NOT NULL");

  private final String code;

  Operator(String code) {
    this.code = code;
  }

  public static Operator of(String code) {
    for (Operator operator : values()) {
      if (operator.code.equals(code)) {
        return operator;
      }
    }
    throw HymError.Core.ParseQueryOperatorError
      .addContext("operator", code)
      .toException();
  }
}
