package com.castlabs.astrit.mp4analyzer.service;

import com.castlabs.astrit.mp4analyzer.exceptions.FileException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class FileDownloaderService {

  private final WebClient webClient;

  public Mono<InputStream> downloadFile(String url) {
    return webClient.get().uri(url).retrieve()
        .onStatus(HttpStatusCode::isError, r -> this.handleErrors(r, url))
        .bodyToFlux(DataBuffer.class)
        .map(b -> b.asInputStream(true))
        .reduce(SequenceInputStream::new);
  }

  private Mono<Throwable> handleErrors(ClientResponse clientResponse, String url) {
    if (clientResponse.statusCode().is5xxServerError()) {
      return clientResponse.bodyToMono(String.class)
          .flatMap(errorBody -> {
            log.error("An error occurred while downloading the file: {} error: {}", url, errorBody);
            return Mono.error(FileException.fileDownloadError(url));
          });
    }
    return clientResponse.bodyToMono(String.class)
        .flatMap(errorBody -> Mono.error(FileException.notFound(url)));
  }

}
