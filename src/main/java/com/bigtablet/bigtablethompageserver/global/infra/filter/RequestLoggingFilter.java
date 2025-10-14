package com.bigtablet.bigtablethompageserver.global.infra.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String traceId = Optional.ofNullable(MDC.get("traceId"))
                .orElse(UUID.randomUUID().toString());
        MDC.put("traceId", traceId);
        String clientIp = extractClientIp(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String ua = Optional.ofNullable(request.getHeader("User-Agent")).orElse("-");
        try {
            chain.doFilter(request, response);
        } finally {
            int status = response.getStatus();

            org.slf4j.LoggerFactory.getLogger(RequestLoggingFilter.class).info(
                    "traceId={} ip={} method={} uri={}{} status={} ua={}",
                    traceId, clientIp, method, uri, (query != null ? "?" + query : ""), status, ua
            );
            MDC.remove("traceId");
        }
    }

    private String extractClientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xff)) {
            return xff.split(",")[0].trim();
        }
        String xri = req.getHeader("X-Real-IP");
        if (StringUtils.hasText(xri)) {
            return xri;
        }
        return req.getRemoteAddr();
    }

}