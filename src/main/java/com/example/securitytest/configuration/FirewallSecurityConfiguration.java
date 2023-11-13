package com.example.securitytest.configuration;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * TODO: check cutomization of ExceptionHandlerExceptionResolver.
 */
@Configuration
@ControllerAdvice
public class FirewallSecurityConfiguration extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(FirewallSecurityConfiguration.class);

  @Bean
  StrictHttpFirewall strictHttpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowedHttpMethods(List.of("GET", "POST"));
    return firewall;
  }

  @Bean
  RequestRejectedHandler requestRejectedHandler() {
    return new HttpStatusRequestRejectedHandler(405);
  }


  @Bean
  SecurityFilterChain allowAllFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(requests -> requests.anyRequest().permitAll())
        .sessionManagement(session -> session.disable())
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable());

    return http.build();
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

    //logger.info("handling:", ex);
    return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
  }
}
