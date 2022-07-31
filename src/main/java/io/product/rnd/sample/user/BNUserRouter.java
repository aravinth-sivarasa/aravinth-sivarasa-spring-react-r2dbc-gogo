package io.product.rnd.sample.user;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class BNUserRouter {

    @Bean
    public RouterFunction<ServerResponse> user(BNUserHandler bnUserHandler) {
        return route(GET(UserEndpoints.GET_USER_BY_CODE_V1).and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().body(bnUserHandler.fetch(request),
                        BNUser.class));
    }
}

class UserEndpoints {
    private static final String PREFIX = "";
    public static final String GET_USER_BY_CODE_V1 = PREFIX + "/v1/user/{code}";

    private UserEndpoints() {
    }
}