package com.castlabs.astrit.mp4analyzer.service.analyzer;

import static org.assertj.core.api.Assertions.assertThat;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Mp4AnalyzerTest {

  private static final Mp4Analyzer mp4Analyzer = new Mp4Analyzer();

  @Test
  public void testAnalyze() {
    String fileName="/text0.mp4";
    InputStream inputStream = this.getClass().getResourceAsStream(fileName);
    List<Box> boxes = mp4Analyzer.analyze(inputStream, fileName);
    assertThat(boxes.size()).isEqualTo(2);
    Box moof = boxes.get(0);
    assertThat(moof.getType()).isEqualTo("moof");
    assertThat(moof.getSize()).isEqualTo(181);
    assertThat(moof.getSubBoxes().size()).isEqualTo(2);

    List<Box> moofSuboxes = moof.getSubBoxes();
    
    Box mfhd = moofSuboxes.get(0);
    assertThat(mfhd.getType()).isEqualTo("mfhd");
    assertThat(mfhd.getSize()).isEqualTo(16);
    assertThat(mfhd.getSubBoxes()).isEmpty();

    Box traf = moofSuboxes.get(1);
    assertThat(traf.getType()).isEqualTo("traf");
    assertThat(traf.getSize()).isEqualTo(157);
    assertThat(traf.getSubBoxes().size()).isEqualTo(4);
    
    List<Box> trafSuboxes = traf.getSubBoxes();
    
    Box tfhd = trafSuboxes.get(0);
    assertThat(tfhd.getType()).isEqualTo("tfhd");
    assertThat(tfhd.getSize()).isEqualTo(24);
    assertThat(tfhd.getSubBoxes()).isEmpty();

    Box trun = trafSuboxes.get(1);
    assertThat(trun.getType()).isEqualTo("trun");
    assertThat(trun.getSize()).isEqualTo(20);
    assertThat(trun.getSubBoxes()).isEmpty();

    Box uuid1 = trafSuboxes.get(2);
    assertThat(uuid1.getType()).isEqualTo("uuid");
    assertThat(uuid1.getSize()).isEqualTo(44);
    assertThat(uuid1.getSubBoxes()).isEmpty();

    Box uuid2 = trafSuboxes.get(3);
    assertThat(uuid2.getType()).isEqualTo("uuid");
    assertThat(uuid2.getSize()).isEqualTo(61);
    assertThat(uuid2.getSubBoxes()).isEmpty();

    Box mdat = boxes.get(1);
    assertThat(mdat.getType()).isEqualTo("mdat");
    assertThat(mdat.getSize()).isEqualTo(17908);
    assertThat(mdat.getSubBoxes()).isEmpty();
  }

  @Test
  public void testAnalyzeWithEmptyFile() {
    String fileName = "https://example.com/video.mp4";
    byte[] data = new byte[0];
    InputStream inputStream = new ByteArrayInputStream(data);
    List<Box> boxes = mp4Analyzer.analyze(inputStream, fileName);
    assertThat(boxes).isEmpty();
  }
}
