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

  isnull("IS NULL", false),

  notnull("IS NOT NULL", false);

  private final String code;

  private final boolean needParams;

  Operator(String code) {
    this.code = code;
    this.needParams = true;
  }

  Operator(String code, boolean needParams) {
    this.code = code;
    this.needParams = needParams;
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
