package com.rayshan.gitinfo.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Filter responsible for updating the log level on the fly. */
@Component
@Order(1)
public class LoggingFilter implements Filter {
    public static final String CORRELATION_ID_LOGGING = "correlation-id";
    public static final String X_LOG_LEVEL = "X-Log-Level";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getHeader(X_LOG_LEVEL) != null) {
            ThreadContext.put(X_LOG_LEVEL, req.getHeader(X_LOG_LEVEL));
        }

        // Setting correlation-id so that it prints in all log entries.
        if (null != CORRELATION_ID_LOGGING) {
            ThreadContext.put(CORRELATION_ID_LOGGING, req.getHeader(CORRELATION_ID_LOGGING));
        }

        chain.doFilter(request, response);
    }
}
