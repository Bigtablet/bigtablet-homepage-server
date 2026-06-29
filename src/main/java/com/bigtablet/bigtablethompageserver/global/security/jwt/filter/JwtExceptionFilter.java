package com.bigtablet.bigtablethompageserver.global.security.jwt.filter;

import com.bigtablet.bigtablethompageserver.global.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    // Spring Boot 4의 자동 설정 ObjectMapper는 Jackson 3(tools.jackson) 빈이라 주입 대신 직접 생성한다.
    // 싱글톤 필터의 필드로 1회만 생성해 요청마다 재생성하지 않는다. (JwtAuthenticationEntryPoint와 동일 패턴)
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            setErrorResponse(e.getError().getStatus(), response, e.getError().getMessage());
        }
    }

    public void setErrorResponse(HttpStatus status,
                                 HttpServletResponse response,
                                 String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());
        Map<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(status.value()));
        map.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }

}
