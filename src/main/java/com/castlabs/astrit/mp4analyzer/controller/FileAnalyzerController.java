package com.castlabs.astrit.mp4analyzer.controller;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import com.castlabs.astrit.mp4analyzer.service.FileAnalyzerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FileAnalyzerController {

  public static final String ANALYZE_MP4_PATH = "analyze-mp4";
  
  private final FileAnalyzerService fileAnalyzerService;

  @GetMapping("/v1/"+ANALYZE_MP4_PATH)
  public Mono<List<Box>> analyzeMp4(@RequestParam String videoUrl){
    return fileAnalyzerService.analyzeMp4(videoUrl);
  }
}
