package com.castlabs.astrit.mp4analyzer.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BoundedInputStream extends FilterInputStream {

  private long left;

  public BoundedInputStream(InputStream in, long size) {
    super(in);
    this.in = in;
    this.left = size;
  }

  @Override
  public int read() throws IOException {
    if (this.left == 0) {
      return -1;
    }
    int result = this.in.read();
    this.left--;
    return result;
  }

  @Override
  public int read(byte[] b) throws IOException {
    return this.read(b, 0, b.length);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    if (this.left == 0) {
      return -1;
    }
    long maxRead = Math.min(left, len);
    int bytesRead = this.in.read(b, off, (int) maxRead);
    if (bytesRead == -1) {
      return -1;
    }
    this.left -= bytesRead;
    return bytesRead;
  }

  @Override
  public long skip(long n) throws IOException {
    long toSkip = Math.min(left, n);
    long skippedBytes = this.in.skip(toSkip);
    this.left -= skippedBytes;
    return skippedBytes;
  }

  @Override
  public void skipNBytes(long n) throws IOException {
    long toSkip = Math.min(left, n);
    this.left -= toSkip;
    this.in.skipNBytes(toSkip);
  }

  @Override
  public int available() throws IOException {
    return (int) Math.min(left, this.in.available());
  }
}
