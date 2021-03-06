package com.limaila.support.global.body;

import org.apache.catalina.ssi.ByteArrayServletOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 包装HttpServletResponse类
 * 增加获取返回信息的数据
 * Author: huangxincheng
 * <p>
 * <p>
 */
public class BodyHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayServletOutputStream byteArrOutputStream = new ByteArrayServletOutputStream();

    public BodyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(this.byteArrOutputStream);
//        return super.getWriter();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return this.byteArrOutputStream;
    }

    public ByteArrayServletOutputStream getByteArrOutputStream() {
        return this.byteArrOutputStream;
    }

    public void setByteArrOutputStream(ByteArrayServletOutputStream byteArrOutputStream) {
        this.byteArrOutputStream = byteArrOutputStream;
    }
}
