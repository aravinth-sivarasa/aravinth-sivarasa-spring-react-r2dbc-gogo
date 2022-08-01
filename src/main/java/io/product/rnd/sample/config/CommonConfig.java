package io.product.rnd.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

import reactor.core.publisher.Mono;

@Configuration
public class CommonConfig {
    private static Logger log = LoggerFactory.getLogger(CommonConfig.class);

    private static final String REQUEST_ID_KEY = "org.springframework.web.server.ServerWebExchange.LOG_ID";

    @Bean
    WebFilter handleRequestAndResponse() {
        return (exchange, next) -> next.filter(exchange)
                .doFirst(() -> log.info("log-id= \"{}\", method=\"{}\" ,URL=\"{}\"",
                        exchange.getAttribute(REQUEST_ID_KEY), exchange.getRequest().getMethod(),
                        exchange.getRequest().getPath()));
    }
}
