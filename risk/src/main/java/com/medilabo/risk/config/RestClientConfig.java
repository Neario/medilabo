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

/**
 * {@link RestClient} config pointed at the Gateway with resource factories :
 * {@link PatientClient},
 * {@link NoteClient}
 */
@Configuration
public class RestClientConfig {

    /**
     * {@link RestClient} used by every gateway client, with {@link JwtHttpRequestInterceptor} for relay JWT.
     *
     * @param gatewayUrl base URL of the {@code gateway}
     * @return the configured client
     */
    @Bean
    public RestClient gatewayRestClient(@Value("${gateway.url}") String gatewayUrl) {
        return RestClient.builder()
                .baseUrl(gatewayUrl)
                .requestInterceptors(interceptor ->
                        interceptor.add(new JwtHttpRequestInterceptor()))
                .build();
    }

    /**
     * @param restClient config {@link RestClient}
     * @return resource {@link PatientClient}
     */
    @Bean
    public PatientClient patientClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(PatientClient.class);
    }

    /**
     * @param restClient the shared gateway-bound client
     * @return resource {@link NoteClient}
     */
    @Bean
    public NoteClient noteClient(@Qualifier("gatewayRestClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NoteClient.class);
    }
}
