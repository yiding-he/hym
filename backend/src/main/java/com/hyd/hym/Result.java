package com.hyd.hym;

import com.hyd.hym.constants.HymError;
import com.hyd.hym.constants.HymError.Common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

  public static <T> Result<T> ok() {
    return new Result<>(null, null);
  }

  public static <T> Result<T> ok(T data) {
    return new Result<>(null, data);
  }

  public static <T> Result<T> error() {
    return new Result<>(Common.UnknownError, null);
  }

  public static <T> Result<T> error(HymError error) {
    return new Result<>(error, null);
  }

  private HymError error;

  private T data;

  public boolean isOk() {
    return error == null;
  }

  /// ////////////////////////////////////////

  public record ResultOptional<T, R>(Result<T> result, Function<T, R> parseOk) {
    public R orElse(Function<Result<T>, R> parseError) {
      return result.isOk() ? parseOk.apply(result.data) : parseError.apply(result);
    }
  }

  public <R> ResultOptional<T, R> ifOk(Function<T, R> converter) {
    return new ResultOptional<>(this, converter);
  }
}
