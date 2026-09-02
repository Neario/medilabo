package com.medilabo.risk.config;

import com.medilabo.risk.client.NoteClient;
import com.medilabo.risk.client.PatientClient;
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
        return RestClient.builder()
                .baseUrl(gatewayUrl)
                .requestInterceptors(interceptor ->
                        interceptor.add(new JwtHttpRequestInterceptor()))
                .build();
    }

    @Bean
    public PatientClient patientClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PatientClient.class);
    }

    @Bean
    public NoteClient noteClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NoteClient.class);
    }
}
