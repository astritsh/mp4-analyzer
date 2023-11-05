package com.castlabs.astrit.mp4analyzer.service;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import com.castlabs.astrit.mp4analyzer.service.analyzer.FileAnalyzer;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class FileAnalyzerService {
  
  private final FileDownloaderService fileDownloaderService;
  private final FileAnalyzer fileAnalyzer;
  
  public Mono<List<Box>> analyzeMp4(String videoUrl){
    return fileDownloaderService.downloadFile(videoUrl).map(file->fileAnalyzer.analyze(file, videoUrl));
  }
}
