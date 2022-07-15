package io.product.rnd.sample.user;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface BNUserRepository extends ReactiveCrudRepository<BNUser, Long> {

    @Query("SELECT * FROM bn_user WHERE code = :username")
    Mono<UserDetails> findByCode(String username);

}
