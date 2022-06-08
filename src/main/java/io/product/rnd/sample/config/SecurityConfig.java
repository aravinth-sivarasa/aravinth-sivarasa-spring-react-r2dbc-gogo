package io.product.rnd.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;
import java.net.URI;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

        @Bean
        SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
                http
                                .authorizeExchange(exchanges -> exchanges
                                                .pathMatchers("/assets/**", "/manifest.json",
                                                                "/favicon.ico", "/static/**",
                                                                "/ui/**")
                                                .permitAll()
                                                .anyExchange().authenticated())

                                .formLogin(formLogin -> formLogin.loginPage("ui/user/login"))
                                // .formLogin().and()
                                .logout().logoutSuccessHandler(logoutSuccessHandler()).and().csrf().disable();
                return http.build();
                // return http.authorizeExchange()
                // .anyExchange().authenticated()
                // .and()
                // .formLogin().and().//
                // logout().logoutSuccessHandler(logoutSuccessHandler()).and().csrf()
                // .disable()
                // .build();
        }

        @Bean
        MapReactiveUserDetailsService userDetailsService() {
                User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
                UserDetails rob = userBuilder.username("rob")
                                .password("rob")
                                .roles("USER")
                                .build();
                UserDetails admin = userBuilder.username("admin")
                                .password("admin")
                                .roles("USER", "ADMIN")
                                .build();
                return new MapReactiveUserDetailsService(rob, admin);
        }

        ServerLogoutSuccessHandler logoutSuccessHandler() {
                return new ServerLogoutSuccessHandler() {
                        @Override
                        public Mono<Void> onLogoutSuccess(WebFilterExchange exchange,
                                        Authentication authentication) {
                                ServerHttpResponse response = exchange.getExchange().getResponse();
                                response.setStatusCode(HttpStatus.FOUND);
                                response.getHeaders().setLocation(URI.create("/login?logout"));
                                response.getCookies().remove("JSESSIONID");
                                return exchange.getExchange().getSession()
                                                .flatMap(WebSession::invalidate);
                        }
                };
        }
}