package com.server.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustometRequestWrapper extends HttpServletRequestWrapper {

  public CustometRequestWrapper(HttpServletRequest request) {
      super(request);
  }

    StringBuilder stringBuilder = new StringBuilder();

    @Override
    public ServletInputStream getInputStream() throws IOException {

        byte[] bytes = new byte[0];
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.read() == -1 ? true:false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                int a = byteArrayInputStream.read();
                return a;
            }
        };
    }
}
