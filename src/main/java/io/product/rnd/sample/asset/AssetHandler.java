package io.product.rnd.sample.asset;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.product.rnd.sample.asset.domain.Asset;
import io.product.rnd.sample.asset.domain.AssetType;
import io.product.rnd.sample.asset.repo.AssetRepository;
import io.product.rnd.sample.asset.repo.AssetTypeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AssetHandler {

    private static Logger log = LoggerFactory.getLogger(AssetHandler.class);
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetTypeRepository assetTypeRepository;

    @PostConstruct
    public void init() {

    }

    public Mono<ServerResponse> fetch(ServerRequest request) {
        String code = request.pathVariable("code");
        return null;
    }

    public Mono<ServerResponse> fetchAll(ServerRequest request) {

        Flux<Asset> people = assetRepository.findAll();
        return ServerResponse.ok().body(people, Asset.class);

    }

    @Transactional
    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(Asset.class)//
                .flatMap(value -> {
                    printInfoLog(request, "request_body =" + value);
                    if (value.getCode() == null) {
                        value.setCode(UUID.randomUUID().toString());
                    }
                    value.setAssetTypeId(1l);
                    printInfoLog(request, "repo value =" + value);
                    Mono<Asset> repResponse = assetRepository.save(value);
                    return repResponse.flatMap(savedValue -> {
                        return ServerResponse.ok().body(Mono.empty(),
                                String.class);
                    });
                });
    }

    public Mono<ServerResponse> fetchLandingPage(ServerRequest request) {
        Flux<AssetType> result = assetTypeRepository.findAll();
        result.map(value -> {
            log.info(value.toString());
            return value;
        });
        return ServerResponse.ok().body(result, AssetType.class);
    }

    private void printInfoLog(ServerRequest request, String msg) {
        log.info("log-id= \"{}\",{}",
                request.attribute("org.springframework.web.server.ServerWebExchange.LOG_ID").get(),
                msg);
    }
}