package io.product.rnd.sample.greeting;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

        @Bean
        public RouterFunction<ServerResponse> hello(GreetingHandler greetingHandler) {
                return route(GET("/v1/hello").and(accept(MediaType.APPLICATION_JSON)), //
                                request -> ServerResponse.ok().body(greetingHandler.fetch(request), Greeting.class))
                                .and(route(GET("v1/hello/{id}").and(accept(MediaType.APPLICATION_JSON)),
                                                request -> ServerResponse.ok().body(greetingHandler.fetch(request),
                                                                Greeting.class)))
                                .and(route(POST("v1/hello").and(accept(MediaType.APPLICATION_JSON)),
                                                request -> ServerResponse.ok().body(greetingHandler.add(request),
                                                                Greeting.class)));
        }
}
