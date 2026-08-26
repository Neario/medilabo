package com.medilabo.web.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * HTTP interceptor for add Token JWT in Header cookie for Gateway
 * {@code Authorization: Bearer}
 */
public class JwtHttpInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Adds the {@code Authorization} header, if a cookie is present on current request.
     *
     * @param request the outgoing request to the gateway
     * @param body the outgoing request body
     * @param execution the remaining execution chain
     * @return the response from the gateway
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest servletRequest = attributes.getRequest();
            if (servletRequest.getCookies() != null) {
                for (var cookie : servletRequest.getCookies()) {
                    if ("jwt".equals(cookie.getName())) {
                        request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + cookie.getValue());
                    }
                }
            }
        }
        return  execution.execute(request, body);
    }
}
