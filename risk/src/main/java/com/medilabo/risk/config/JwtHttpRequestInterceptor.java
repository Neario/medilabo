package com.medilabo.risk.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * Relays the {@code Authorization} header of the request {@code risk}
 */
public class JwtHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Adds the {@code Authorization} header, if present on the request
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
            String authorization = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (authorization != null && authorization.startsWith("Bearer ")) {
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + authorization.substring(7));
            }
        }
        return execution.execute(request, body);
    }
}
