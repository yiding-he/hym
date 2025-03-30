package com.hyd.hym.database;

import java.util.List;

public record Page<T>(
    int pageSize,
    int pageIndex,
    int totalCount,
    int totalPage,
    List<T> data
) {
}
