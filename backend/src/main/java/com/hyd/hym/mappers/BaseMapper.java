package com.hyd.hym.mappers;

import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Page;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.function.Function;

public interface BaseMapper {
  /// ///////////////////////////

  @Select("select found_rows()")
  int selectFoundRows();

  default <T> Page<T> listPage(Conditions conditions, Function<Conditions, List<T>> queryFunc) {
    var list = queryFunc.apply(conditions);
    var count = selectFoundRows();
    var totalPage = (count + conditions.getPageSize() - 1) / conditions.getPageSize();
    return new Page<>(
      conditions.getPageSize(), conditions.getPageIndex(),
      count, totalPage, list
    );
  }
}
