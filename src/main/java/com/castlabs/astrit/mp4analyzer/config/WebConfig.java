package com.castlabs.astrit.mp4analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {

  @Bean
  public WebClient webClient() {
   return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(
            HttpClient.create().wiretap(true).followRedirect(true)
        )).build();
  }

}