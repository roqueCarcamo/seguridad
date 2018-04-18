package com.seguridad.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class URLSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }

            @Override
            public String encodeUrl(String url) {
                return url;
            }

            @Override
            public String encodeURL(String url) {
                return url;
            }
        };
        chain.doFilter(request, wrappedResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
