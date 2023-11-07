package com.castlabs.astrit.mp4analyzer.service.analyzer;

import static com.castlabs.astrit.mp4analyzer.domain.Box.HEADER_SIZE;

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
import org.apache.commons.io.input.BoundedInputStream;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class Mp4Analyzer implements FileAnalyzer {

  private static final Set<String> HEADERS_WITH_SUBBOXES = new HashSet<>(Arrays.asList("moof", "traf"));

  @Override
  public List<Box> analyze(InputStream inputStream, String videoUrl){
    try (var boundedInputStream = new BoundedInputStream(inputStream)) {
      List<Box> boxes = new ArrayList<>();
      analyzeMp4(boundedInputStream, boxes);
      return boxes;
    } catch (Exception e) {
      log.error("An error occurred while analyzing the mp4 file: {}",videoUrl, e);
      throw FileException.fileAnalyzeError( videoUrl);
    }
  }
  
  private static void analyzeMp4(BoundedInputStream inputStream, List<Box> boxes) throws IOException {
    byte[] header = new byte[HEADER_SIZE];
    boolean noBytesAvailable = inputStream.read(header) == -1;
    if (noBytesAvailable) {
      return;
    }

    Box box = Box.create(header);
    boxes.add(box);
    int payloadDataSize = box.getPayloadDataSize();
    if (HEADERS_WITH_SUBBOXES.contains(box.getType())) {
      var boxInputStream = new BoundedInputStream(inputStream, payloadDataSize);
      analyzeMp4(boxInputStream, box.getSubBoxes());
    }
    else {
      inputStream.skipNBytes(payloadDataSize);
    }
    analyzeMp4(inputStream, boxes);
  }
}
