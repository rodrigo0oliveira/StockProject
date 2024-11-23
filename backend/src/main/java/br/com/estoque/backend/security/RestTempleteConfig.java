package br.com.estoque.backend.security;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

public class RestTempleteConfig {

    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(),trustStorePassword.toCharArray())
                .build();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(factory);
    }
}
