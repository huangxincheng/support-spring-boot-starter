package com.limaila.support.global.body;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 *  从请求体中获取参数请求包装类：<br>
 * Author: huangxincheng
 * <p>
 * <p>
 **/
public class BodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody = null;//用于将流保存下来

    public BodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String contentEncoding = request.getHeader("Content-Encoding");
        // 如果对内容进行了压缩，则解压
        if (null != contentEncoding && contentEncoding.indexOf("gzip") != -1) {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(request.getInputStream());
            ServletInputStream newStream = new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return gzipInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener arg0) {
                    // TODO Auto-generated method stub

                }
            };
            IOUtils.closeQuietly(gzipInputStream);
            requestBody = StreamUtils.copyToByteArray(newStream);
        } else {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
