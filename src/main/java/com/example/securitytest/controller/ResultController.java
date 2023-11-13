package com.example.securitytest.controller;

import com.example.securitytest.command.CreateResultCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ingress/result")
public class ResultController {

  private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

  @PostMapping
  ResponseEntity<CreateResultCommand> result(@RequestBody CreateResultCommand result) {
    logger.info("received: {}", result.getResultId());
    return ResponseEntity.ok(result);
  }
}
