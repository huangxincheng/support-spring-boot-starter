package com.limaila.support.global.bodyread;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 *     继承OncePerRequestFilter 确保每个请求Filter只经过一次
 **/
@SpringBootConfiguration
public class BodyReaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去，提供后续使用
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, httpServletResponse);
        System.out.println("doFilterInternal over");
    }
}
