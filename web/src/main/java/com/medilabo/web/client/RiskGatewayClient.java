package com.medilabo.web.client;

import com.medilabo.web.dto.RiskResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

/**
 * HTTP client for the {@code risk} endpoints
 */
public interface RiskGatewayClient {

    @GetExchange("/risk/{patId}")
    RiskResponseDTO getRisk(@PathVariable Long patId);
}
