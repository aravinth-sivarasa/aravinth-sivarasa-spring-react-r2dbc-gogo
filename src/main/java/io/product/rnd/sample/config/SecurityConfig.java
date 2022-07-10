package io.product.rnd.sample.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.WebSession;

import io.product.rnd.sample.user.BNUserHandler;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

        @Bean
        SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
                http
                                .authorizeExchange(exchanges -> exchanges
                                                .pathMatchers("/assets/**", "/manifest.json",
                                                                "/favicon.ico", "/static/**", "/ui/user/login",
                                                                "/error")
                                                .permitAll()
                                                .anyExchange().authenticated())

                                .formLogin(formLogin -> formLogin.loginPage("/ui/user/login"))
                                // .formLogin().and()
                                .logout().logoutSuccessHandler(logoutSuccessHandler()).and().csrf().disable();
                return http.build();
        }

        // @Bean
        // @DependsOn({ "initializer", "encoder" })
        // ReactiveUserDetailsService reactiveUserDetailsService() {
        // return new BNUserHandler();
        // }

        @Bean
        public ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
                UserDetails admin = User
                                .withUsername("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles("ADMIN", "MEMBER")
                                .build();

                UserDetails caterpillar = User
                                .withUsername("caterpillar")
                                .password(passwordEncoder.encode("admin"))
                                .roles("MEMBER")
                                .build();

                return new MapReactiveUserDetailsService(admin, caterpillar);
        }

        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
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