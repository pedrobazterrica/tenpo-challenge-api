package com.tenpo.challenge.config;

import com.tenpo.challenge.model.Request;
import com.tenpo.challenge.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class RequestFilter extends OncePerRequestFilter {
    @Autowired
    private RequestLogRepository requestLogRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();
        String requestBody = new String(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
        Request requestLog = Request.builder()
                .method(requestWrapper.getMethod())
                .uri(requestWrapper.getRequestURI())
                .requestBody(requestBody)
                .build();

        filterChain.doFilter(requestWrapper, responseWrapper);

        String responseBody = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        long elapsed = System.currentTimeMillis() - start;
        requestLog.setResponseBody(responseBody);
        requestLog.setResponseStatus(response.getStatus());
        requestLog.setTimeTaken(elapsed);
        CompletableFuture.runAsync(() -> {
            requestLogRepository.save(requestLog);
        });
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return "/api/v1/requests".equals(path);
    }
}

