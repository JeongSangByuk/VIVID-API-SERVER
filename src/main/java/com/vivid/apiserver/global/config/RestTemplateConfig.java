package com.vivid.apiserver.global.config;

import com.vivid.apiserver.global.error.RestTemplateErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {


    @Primary
    @Bean(name = "webexRestTemplate")
    public RestTemplate webexRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.rootUri("https://webexapis.com/v1")
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .errorHandler(new RestTemplateErrorHandler())
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMinutes(3))
                .build();
    }

    @Bean(name = "zoomRestTemplate")
    public RestTemplate zoomRestTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.rootUri("https://webexapis.com/v1")
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .errorHandler(new RestTemplateErrorHandler())
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMinutes(3))
                .build();
    }
}
