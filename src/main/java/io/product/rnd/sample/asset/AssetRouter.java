package io.product.rnd.sample.asset;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class AssetRouter {

    private static final String RESOURCE_URL = "/v1/asset";

    @Bean
    public RouterFunction<ServerResponse> asset(AssetHandler assetHandler) {

        return route()
                .GET(RESOURCE_URL + "/landing-page",
                        assetHandler::fetchLandingPage)
                .POST(RESOURCE_URL, accept((MediaType.APPLICATION_JSON)),
                        assetHandler::update)
                .build();
    }
}
