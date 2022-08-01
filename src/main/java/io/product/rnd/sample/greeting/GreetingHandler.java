package io.product.rnd.sample.greeting;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    private static Logger log = LoggerFactory.getLogger(GreetingHandler.class);
    @Autowired
    private GreetingRepository greetingRepository;

    @Transactional
    public Mono<Greeting> add(ServerRequest request) {

        return request.bodyToMono(Greeting.class)//
                .doFirst(() -> log.info("request-id: {}", request.attributes()))
                .flatMap(greeting -> {
                    log.info("Received value {}", greeting);
                    return greetingRepository.save(greeting);
                });
    }

    public Flux<Greeting> fetch(ServerRequest request) {
        Optional<Object> requestIdOpt = request.attribute("request-id");
        String requestId = (String) requestIdOpt.orElse("NA");
        try {
            Long id = Long.parseLong(request.pathVariable("id"));
            log.info("request-id: {} , received Id: {}", requestId, id);
            return greetingRepository.findById(id).flux();
        } catch (RuntimeException e) {
            log.warn("request-id: {}", requestId);
            return greetingRepository.findAll();
        }

    }
}
