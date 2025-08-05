package com.hyd.hym.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public abstract class WebApiV1Controller {

  @Autowired
  protected ApplicationEventPublisher publisher;
}
