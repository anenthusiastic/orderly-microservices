package com.fatih.orderservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwidXNlcklkIjozLCJzdWIiOiJmYXRpaCIsImlhdCI6MTc0NzQ2NDIzMCwiZXhwIjoxNzQ3NTUwNjMwfQ.qbkJeO2UewwqKLwqkp2464U8KBMyZNfHwC0dBi1_MSs";
        template.header("Authorization", token);
    }
}

