package com.snailmann.seckill.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * URL拦截器，输出URL
 * @author liwenjie
 */
@Component
public class URLFilte implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(LocalDateTime.now().toString()+ " : " + ((HttpServletRequest)request).getRequestURL());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
