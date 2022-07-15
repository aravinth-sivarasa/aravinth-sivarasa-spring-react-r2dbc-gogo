package io.product.rnd.sample.greeting;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface GreetingRepository extends ReactiveCrudRepository<Greeting, Long> {
    @Query("SELECT * FROM Greeting WHERE message = :message")
    Flux<Greeting> findByMessage(String message);
}
