package com.hyd.hym.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class IdSupplier {

  public static final String DEFAULT_SEQUENCE_NAME = "default";

  private final MysqlSequenceGenerator generator;

  @Autowired
  public IdSupplier(DataSource dataSource) {
    this.generator = new MysqlSequenceGenerator(dataSource);
  }

  public long nextId(String sequenceName) {
    return generator.nextLong(sequenceName);
  }

  public long nextId() {
    return generator.nextLong(DEFAULT_SEQUENCE_NAME);
  }
}
