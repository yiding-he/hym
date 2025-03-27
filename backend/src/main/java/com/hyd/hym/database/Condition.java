package com.hyd.hym.database;

import com.hyd.hym.constants.HymError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.List;

import static org.springframework.util.StringUtils.uncapitalize;

@Data
@NoArgsConstructor
public class Condition {

  @FunctionalInterface
  public interface Getter<T, R> extends Serializable {
    @SuppressWarnings("unused")
    R apply(T t);
  }

  public static <T, R> Condition of(Getter<T, R> getter, Operator operator, List<Object> values) {
    var condition = new Condition();
    condition.setOperator(operator);
    condition.setValues(values);
    parseGetter(getter, condition);
    return condition;
  }

  public static <T, R> Condition of(Getter<T, R> getter, Operator operator, Object... values) {
    return of(getter, operator, List.of(values));
  }

  public static Condition of(String propName, Operator operator, List<Object> values) {
    var condition = new Condition();
    condition.setPropName(propName);
    condition.setOperator(operator);
    condition.setValues(values);
    return condition;
  }

  public static Condition of(String propName, Operator operator, Object... values) {
    return of(propName, operator, List.of(values));
  }

  /**
   * 解析 lambda 表达式，得到原始的 JavaBean 类名和属性名并注入到 Condition 对象
   */
  private static <T, R> void parseGetter(Getter<T, R> getter, Condition condition) {
    try {
      var lambdaClass = getter.getClass();
      var writeReplaceMethod = lambdaClass.getDeclaredMethod("writeReplace");
      writeReplaceMethod.setAccessible(true);
      var serializedForm = (SerializedLambda) writeReplaceMethod.invoke(getter);
      condition.setPropName(extractPropFromGetter(serializedForm.getImplMethodName()));
    } catch (Exception e) {
      throw HymError.Core.ParseBeanMethodError.withCause(e).toException();
    }
  }

  private static String extractPropFromGetter(String getterMethodName) {
    return switch (getterMethodName) {
      case String name when (name.startsWith("is")) -> uncapitalize(name.substring(2));
      case String name when (name.startsWith("get")) -> uncapitalize(name.substring(3));
      default -> throw new IllegalArgumentException("Invalid getter method name: " + getterMethodName);
    };
  }

  private String propName;

  private Operator operator;

  private List<Object> values;

  public String getColumnName() {
    return this.propName.replaceAll("([A-Z]+)", "_$1").toLowerCase();
  }
}
