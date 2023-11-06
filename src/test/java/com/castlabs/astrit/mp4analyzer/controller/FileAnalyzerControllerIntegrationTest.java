package com.castlabs.astrit.mp4analyzer.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.castlabs.astrit.mp4analyzer.domain.Box;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileAnalyzerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAnalyzeMp4() {

        String BASE_URL = "http://localhost:" + port + "/api/v1/analyze-mp4?videoUrl={videoUrl}";
        String videoURL = "http://demo.castlabs.com/tmp/text0.mp4";
        ResponseEntity<List<Box>> response = restTemplate.exchange(BASE_URL,
            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            }, videoURL);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Box> boxes = response.getBody();
        assertThat(boxes.size()).isEqualTo(2);
        Box moof = boxes.get(0);
        assertThat(moof.getType()).isEqualTo("moof");
        assertThat(moof.getSize()).isEqualTo(181);
        assertThat(moof.getSubBoxes().size()).isEqualTo(2);

        List<Box> moofSuboxes = moof.getSubBoxes();

        Box mfhd = moofSuboxes.get(0);
        assertThat(mfhd.getType()).isEqualTo("mfhd");
        assertThat(mfhd.getSize()).isEqualTo(16);
        assertThat(mfhd.getSubBoxes()).isNullOrEmpty();

        Box traf = moofSuboxes.get(1);
        assertThat(traf.getType()).isEqualTo("traf");
        assertThat(traf.getSize()).isEqualTo(157);
        assertThat(traf.getSubBoxes().size()).isEqualTo(4);

        List<Box> trafSuboxes = traf.getSubBoxes();

        Box tfhd = trafSuboxes.get(0);
        assertThat(tfhd.getType()).isEqualTo("tfhd");
        assertThat(tfhd.getSize()).isEqualTo(24);
        assertThat(tfhd.getSubBoxes()).isNullOrEmpty();

        Box trun = trafSuboxes.get(1);
        assertThat(trun.getType()).isEqualTo("trun");
        assertThat(trun.getSize()).isEqualTo(20);
        assertThat(trun.getSubBoxes()).isNullOrEmpty();

        Box uuid1 = trafSuboxes.get(2);
        assertThat(uuid1.getType()).isEqualTo("uuid");
        assertThat(uuid1.getSize()).isEqualTo(44);
        assertThat(uuid1.getSubBoxes()).isNullOrEmpty();

        Box uuid2 = trafSuboxes.get(3);
        assertThat(uuid2.getType()).isEqualTo("uuid");
        assertThat(uuid2.getSize()).isEqualTo(61);
        assertThat(uuid2.getSubBoxes()).isNullOrEmpty();

        Box mdat = boxes.get(1);
        assertThat(mdat.getType()).isEqualTo("mdat");
        assertThat(mdat.getSize()).isEqualTo(17908);
        assertThat(mdat.getSubBoxes()).isNullOrEmpty();
    }

    @Test
    public void testAnalyzeMp4WithNonExistingFie() {

        String BASE_URL = "http://localhost:" + port + "/api/v1/analyze-mp4?videoUrl={videoUrl}";
        String videoURL = "http://demo.castlabs.com/tmp/" + UUID.randomUUID() + ".mp4";
        ResponseEntity<Object> response = restTemplate.exchange(BASE_URL,
            HttpMethod.GET, null, Object.class, videoURL);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
