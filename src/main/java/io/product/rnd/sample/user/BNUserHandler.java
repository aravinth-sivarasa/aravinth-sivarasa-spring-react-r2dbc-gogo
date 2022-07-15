package io.product.rnd.sample.user;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BNUserHandler implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private static Logger log = LoggerFactory.getLogger(BNUserHandler.class);

    @Autowired
    private BNUserRepository userCRUD;
    @Autowired
    private PasswordEncoder encoder;

    // @Transactional
    public void addUser(BNUser user) {
        userCRUD.findByCode(user.getCode())//
                .doOnNext(value -> {
                    log.info("User fetched {}", value.getUsername());
                }).switchIfEmpty(userCRUD.save(user))
                .block();

    }

    @PostConstruct
    public void init() {
        try {
            BNUser user = new BNUser("admin", encoder.encode("admin"));
            addUser(user);
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userCRUD.findByCode(username);
    }

    public Flux<BNUser> fetch(ServerRequest request) {
        String code = request.pathVariable("code");
        return null;
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return Mono.just(user);
    }
}
