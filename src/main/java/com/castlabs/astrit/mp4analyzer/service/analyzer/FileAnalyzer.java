package com.castlabs.astrit.mp4analyzer.service.analyzer;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import java.io.InputStream;
import java.util.List;

public interface FileAnalyzer {
  List<Box> analyze(InputStream inputStream, String uri);
}
