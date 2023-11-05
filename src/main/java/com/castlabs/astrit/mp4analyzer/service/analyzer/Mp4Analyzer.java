package com.castlabs.astrit.mp4analyzer.service.analyzer;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import com.castlabs.astrit.mp4analyzer.exceptions.FileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class Mp4Analyzer implements FileAnalyzer {

  private static final Set<String> HEADERS_WITH_SUBBOXES = new HashSet<>(Arrays.asList("moof", "traf"));

  @Override
  public List<Box> analyze(InputStream inputStream, String videoUrl){
    try (inputStream) {
      List<Box> boxes = new ArrayList<>();
      analyzeMp4(inputStream, boxes, Integer.MAX_VALUE);
      return boxes;
    } catch (Exception e) {
      log.error("An error occurred while analyzing the mp4 file: {}",videoUrl, e);
      throw FileException.fileAnalyzeError( videoUrl);
    }
  }
  
  private static void analyzeMp4(InputStream fis, List<Box> boxes, int parentBoxAreaToScan) throws IOException {
    byte[] header = new byte[8];
    if (fis.read(header) == -1) {
      return;
    }

    Box box = Box.create(header);
    boxes.add(box);
    int payloadDataSize = box.getPayloadDataSize();
    if (HEADERS_WITH_SUBBOXES.contains(box.getType())) {
      analyzeMp4(fis, box.getSubBoxes(), payloadDataSize);
    } else {
      fis.skip(payloadDataSize);
    }
    parentBoxAreaToScan -= box.getSize();
    if(parentBoxAreaToScan>0) {
      analyzeMp4(fis, boxes, parentBoxAreaToScan);
    }
  }
}
