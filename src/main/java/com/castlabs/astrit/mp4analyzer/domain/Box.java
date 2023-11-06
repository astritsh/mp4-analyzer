package com.castlabs.astrit.mp4analyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.Value;

@Value
@JsonInclude(Include.NON_EMPTY)
public class Box {
  
  String type;
  int size;
  List<Box> subBoxes;

  public static Box create(byte[] header) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(header);
    int size = byteBuffer.getInt();
    byte[] typeBytes = new byte[4];
    byteBuffer.get(typeBytes);
    String type = new String(typeBytes, StandardCharsets.UTF_8);
    return new Box(type, size, new ArrayList<>());
  }

  @JsonIgnore
  public int getPayloadDataSize() {
    return this.size - 8;
  }
    
}