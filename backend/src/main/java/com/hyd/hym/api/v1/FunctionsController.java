package com.hyd.hym.api.v1;

import com.hyd.hym.Response;
import com.hyd.hym.api.WebApiV1Controller;
import com.hyd.hym.mappers.HymFunctionCatMapper;
import com.hyd.hym.mappers.HymFunctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FunctionsController extends WebApiV1Controller {

  @Autowired
  private HymFunctionCatMapper hymFunctionCatMapper;

  @Autowired
  private HymFunctionMapper hymFunctionMapper;

  @GetMapping("/functions")
  public Response getFunctions() {
    var categories = hymFunctionCatMapper.selectAll();
    var functions = hymFunctionMapper.selectAll();

    record Function(Long id, String title, String pageName) {
    }

    record Category(Long id, String title, List<Function> functions) {
    }

    return Response.success().addData("functions",
      categories.stream()
        .map(category -> new Category(
          category.getHymFunctionCatId(),
          category.getCategoryName(),
          functions.stream()
            .filter(function -> function.getCategoryId().equals(category.getHymFunctionCatId()))
            .map(function -> new Function(
              function.getHymFunctionId(),
              function.getFunctionName(),
              function.getPageName()
            ))
            .toList()
        ))
        .toList()
    );
  }
}
