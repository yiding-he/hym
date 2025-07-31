package com.hyd.hym.mappers;

import com.hyd.hym.database.BaseProvider;
import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.Page;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BaseMapper<T> {

  @Select("select found_rows()")
  int selectFoundRows();

  default Optional<String> getTableName() {
    var baseMapperInterface = Stream.of(this.getClass().getInterfaces())
      .filter(BaseMapper.class::isAssignableFrom)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("BaseMapper not found"));

    if (baseMapperInterface.isAnnotationPresent(TableName.class)) {
      return Optional.of(baseMapperInterface.getAnnotation(TableName.class).value());
    }
    return Optional.empty();
  }

  @Transactional
  default Page<T> listPage(Conditions conditions) {
    var list = list(conditions.withRowCount());
    var count = selectFoundRows();
    var totalPage = (count + conditions.getPageSize() - 1) / conditions.getPageSize();
    return new Page<>(
      conditions.getPageSize(), conditions.getPageIndex(),
      count, totalPage, list
    );
  }

  default List<T> list(Conditions conditions) {
    Optional<String> tableName = getTableName();
    if (tableName.isEmpty()) {
      throw new RuntimeException("TableName not found");
    }
    return __list(conditions.withTableName(tableName.get()));
  }

  default T find(Conditions conditions) {
    Optional<String> tableName = getTableName();
    if (tableName.isEmpty()) {
      throw new RuntimeException("TableName not found");
    }
    return __list(conditions.withTableName(tableName.get())).stream().findFirst().orElse(null);
  }

  @SelectProvider(type = BaseProvider.class, method = "toSelectSql")
  List<T> __list(Conditions conditions);
}
