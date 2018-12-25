package com.limaila.support.global.body;

import com.limaila.support.global.LocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.ssi.ByteArrayServletOutputStream;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.nio.ch.IOUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 *     继承OncePerRequestFilter 确保每个请求Filter只经过一次
 **/
@SpringBootConfiguration
@Slf4j
public class BodyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 包装httpServletRequest  防止流读取一次后就没有了, 所以需要将流继续写出去，提供后续使用
        ServletRequest requestWrapper = new BodyHttpServletRequestWrapper(httpServletRequest);
        // 包装httpServletResponse 增加获取返回结果信息
        ServletResponse responseWrapper = new BodyHttpServletResponseWrapper(httpServletResponse);
        log.debug("BodyFilter doFilterInternal wrapper request and response");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        log.debug("BodyFilter doFilterInternal after");
        String compress = requestWrapper.getParameter("compress");
        if (!httpServletResponse.isCommitted()) {
            ByteArrayServletOutputStream baos = null;
            //没有提交
            try {
                baos = ((BodyHttpServletResponseWrapper) responseWrapper).getByteArrOutputStream();
                ServletOutputStream os = httpServletResponse.getOutputStream();
                byte[] bytes = baos.toByteArray();
                if (StringUtils.pathEquals(compress, "true") || (LocalHolder.GZIPCOMPRESSLOCAL.get() != null && LocalHolder.GZIPCOMPRESSLOCAL.get())) {
                    log.debug("BodyFilter 压缩前大小：" + bytes.length);
                    log.debug("BodyFilter 压缩前数据：" + new String(bytes,"utf-8"));
                    //GZIP压缩
                    GZIPOutputStream gos = new GZIPOutputStream(new ByteArrayOutputStream(1024));
                    gos.write(bytes);
                    gos.close();
                    httpServletResponse.setHeader("Content-Encoding", "gzip"); // 设置响应头信息
                    os.write(bytes);
                    LocalHolder.GZIPCOMPRESSLOCAL.remove();
                    log.debug("BodyFilter 压缩后大小：" + bytes.length);
                } else {
                    os.write(bytes);
                }
                os.flush();
            } finally {
                if (baos != null) {
                    baos.close();
                }
            }
        }
    }
}
