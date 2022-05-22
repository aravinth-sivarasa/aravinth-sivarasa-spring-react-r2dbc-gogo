package io.product.rnd.sample.config;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;

@Configuration
public class CommonConfig {
    private static Logger log = LoggerFactory.getLogger(CommonConfig.class);

    private static final String REQUEST_ID_KEY = "request-id";

    @Bean
    WebFilter handleRequestAndResponse() {
        return (exchange, next) -> next.filter(exchange)
                .doFirst(() -> exchange.getAttributes().put(REQUEST_ID_KEY, UUID.randomUUID().toString()))
                .onErrorResume(RuntimeException.class, e -> {
                    String requestId = exchange.getAttribute(REQUEST_ID_KEY);
                    log.error("request-id: {} {}", requestId, e.getMessage());
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    response.getHeaders().add("X-request-id", requestId);
                    String message = getReason(e.getMessage());
                    byte[] bytes = getResponseBody(message).getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                });
    }

    private String getResponseBody(String message) {
        return "{\"reason\":\"" + message + "\"}";

    }

    private String getReason(String message) {
        if (message.indexOf("Unique index or primary key violation") != -1) {
            message = "Data already exist";
        }
        return message;
    }
}
