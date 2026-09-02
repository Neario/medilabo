package com.medilabo.web.config;

import com.medilabo.web.client.AuthClient;
import com.medilabo.web.client.NoteGatewayClient;
import com.medilabo.web.client.PatientGatewayClient;
import com.medilabo.web.client.RiskGatewayClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * {@link RestClient} config pointed at the Gateway with resource factories :
 * {@link PatientGatewayClient},
 * {@link NoteGatewayClient},
 * {@link AuthClient}
 */
@Configuration
public class RestClientConfig {

    /**
     * {@link RestClient} used by every gateway client with {@link JwtHttpInterceptor} for add JWT TOKEN in cookie
     *
     * @param gatewayUrl base URL of the Gateway
     * @return the configured client
     */
    @Bean
    public RestClient gatewayRestClient(@Value("${gateway.url}") String gatewayUrl) {
        return RestClient.builder()
                .baseUrl(gatewayUrl)
                .requestInterceptors(interceptor ->
                        interceptor.add(new JwtHttpInterceptor()))
                .build();
    }

    /**
     * @param restClient config {@link RestClient}
     * @return resource {@link PatientGatewayClient}
     */
    @Bean
    public PatientGatewayClient patientGatewayClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PatientGatewayClient.class);
    }

    /**
     * @param restClient config {@link RestClient}
     * @return resource {@link NoteGatewayClient}
     */
    @Bean
    public NoteGatewayClient noteGatewayClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NoteGatewayClient.class);
    }

    /**
     * @param restClient config {@link RestClient}
     * @return resource {@link AuthClient}
     */
    @Bean
    public AuthClient authClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(AuthClient.class);
    }

    /**
     * @param restClient config {@link RestClient}
     * @return resource {@link RiskGatewayClient}
     */
    @Bean
    public RiskGatewayClient riskGatewayClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(RiskGatewayClient.class);
    }
}
