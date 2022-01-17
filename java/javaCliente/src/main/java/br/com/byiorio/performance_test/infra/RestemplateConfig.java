package br.com.byiorio.performance_test.infra;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestemplateConfig {
    @Bean
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(2000);
        poolingConnectionManager.setDefaultMaxPerRoute(2000);

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManagerShared(true)
                // .setRedirectStrategy(new LaxRedirectStrategy())
                // .setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE)
                .setConnectionManager(poolingConnectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(20000);
        requestFactory.setConnectionRequestTimeout(20000);
        requestFactory.setReadTimeout(20000);

        return new RestTemplate(requestFactory);
    }

}
