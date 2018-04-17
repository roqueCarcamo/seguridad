
package com.seguridad.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class SimpleServletFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filtrado Init: ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
        
        System.out.println("Filtrado: ");
        
        filterChain.doFilter(request, response);  

    }

    @Override
    public void destroy() {
        System.out.println("Filtrado destroy: ");
    }
}
