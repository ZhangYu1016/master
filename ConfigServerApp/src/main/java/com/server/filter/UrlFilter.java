package com.server.filter;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@WebFilter(filterName="UrlFilter",urlPatterns="/*")
public class UrlFilter implements Filter {

    private static Logger logger = Logger.getLogger(UrlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("过滤器初始化-----------------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            String url = new String(httpServletRequest.getRequestURI());

            logger.info("拦截WebHooks请求地址{url}"+url);

            //只过滤/actuator/bus-refresh请求
            if (!url.endsWith("/bus-refresh")) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
            //获取原始的body
            String body = getParm(httpServletRequest);
            logger.info("原始报文:"+body );
            //使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
            CustometRequestWrapper requestWrapper = new CustometRequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取POST请求中Body参数
     * @param request
     * @return 字符串
     */
    public String getParm(HttpServletRequest request) {
        BufferedReader br = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
//            if(br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        return sb.toString();
    }
}
