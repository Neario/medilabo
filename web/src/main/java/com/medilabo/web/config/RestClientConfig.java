package com.medilabo.web.config;

import com.medilabo.web.client.AuthClient;
import com.medilabo.web.client.NoteGatewayClient;
import com.medilabo.web.client.PatientGatewayClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient gatewayRestClient(@Value("${gateway.url}") String gatewayUrl) {
        return RestClient.builder().baseUrl(gatewayUrl).requestInterceptors(interceptor -> interceptor.add(new JwtHttpInterceptor())).build();
    }

    @Bean
    public PatientGatewayClient patientGatewayClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PatientGatewayClient.class);
    }

    @Bean
    public NoteGatewayClient noteGatewayClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NoteGatewayClient.class);
    }

    @Bean
    public AuthClient authClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(AuthClient.class);
    }
}
