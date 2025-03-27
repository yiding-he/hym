package com.hyd.hym.api;

import com.hyd.hym.database.Conditions;
import com.hyd.hym.database.ConditionsHttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public abstract class WebApiV1Controller {

  /**
   * 利用 @ModelAttribute 注解，将请求参数解析为 Conditions 对象。
   */
  @ModelAttribute
  public Conditions parseConditions(@RequestParam Map<String, String> allParams) {
    return ConditionsHttpParser.parse(allParams);
  }

  @Autowired
  protected ApplicationEventPublisher publisher;
}
