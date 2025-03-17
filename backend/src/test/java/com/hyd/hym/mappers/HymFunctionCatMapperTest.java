package com.hyd.hym.mappers;

import com.hyd.hym.HymApplicationTest;
import com.hyd.hym.models.HymFunctionCat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HymFunctionCatMapperTest extends HymApplicationTest {

  @Autowired
  private HymFunctionCatMapper hymFunctionCatMapper;

  @Test
  void selectAll() {
    var categories = hymFunctionCatMapper.selectAll();
    assertNotNull(categories);
    assertFalse(categories.isEmpty());
    categories.forEach(category -> {
      assertNotNull(category.getHymFunctionCatId());
      assertNotNull(category.getCategoryName());
      assertNotNull(category.getCreatedAt());
      assertNotNull(category.getUpdatedAt());
    });
  }
}