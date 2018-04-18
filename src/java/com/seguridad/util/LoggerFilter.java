package com.seguridad.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Rodolfo
 */
public class LoggerFilter extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(LoggerFilter.class);

    static {
        BasicConfigurator.configure();
    }
}
