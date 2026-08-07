package com.medilabo.web.config;

import com.medilabo.web.client.PatientGatewayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(@Value("${gateway.url}") String gatewayUrl) {
        return RestClient.create(gatewayUrl);
    }

    @Bean
    public PatientGatewayClient patientGatewayClient(RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PatientGatewayClient.class);
    }
}
