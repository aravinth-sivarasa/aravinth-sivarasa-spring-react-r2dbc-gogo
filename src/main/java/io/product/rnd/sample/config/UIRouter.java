package io.product.rnd.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class UIRouter {

    @Bean
    public RouterFunction<ServerResponse> ui() {
        return route(GET("/").and(accept(MediaType.ALL)), request -> {
            return ServerResponse.ok().render("index", new ModelMap());
        }).andRoute(GET("/app/**").and(accept(MediaType.ALL)), request -> {
            return ServerResponse.ok().render("index", new ModelMap());
        }).andRoute(GET("/ui/**").and(accept(MediaType.ALL)), request -> {
            return ServerResponse.ok().render("index", new ModelMap());
        }).andRoute(GET("/error").and(accept(MediaType.ALL)), request -> {
            return ServerResponse.ok().render("index", new ModelMap());
        });
    }
}
