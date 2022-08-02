package io.product.rnd.sample.asset;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    public Mono<ServerResponse> fetch(ServerRequest request) {
        String code = request.pathVariable("code");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                assetRepository.findByCode(code),
                Asset.class).switchIfEmpty(Mono.error(new RuntimeException("Asset not found for " + code)));
    }

    public Mono<ServerResponse> fetchAll(ServerRequest request) {
        printInfoLog(request, "fetching all asset");
        Flux<Asset> assets = assetRepository.findAll();
        return ServerResponse.ok().body(assets, Asset.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String code = request.pathVariable("code");
        return assetRepository.findByCode(code).flatMap(value -> {
            printInfoLog(request, "Deleting asset " + code);
            return assetRepository.delete(value).thenReturn(value.getId()).flatMap(id -> {
                printInfoLog(request, "Asset deleted successfully " + code);
                return ServerResponse.ok().bodyValue("");
            }).switchIfEmpty(Mono.error(new RuntimeException("Problem in deleting asset " + code)));
        }).switchIfEmpty(Mono.error(new RuntimeException("Asset not found for " + code)));
    }

    @Transactional
    public Mono<ServerResponse> update(ServerRequest request) {
        boolean hasCode = methodCheck(request);
        return request.bodyToMono(Asset.class).flatMap(asset -> {
            printInfoLog(request, "request_body =" + asset);
            return validateAssetType(asset.getAssetTypeCode())
                    .flatMap(assetType -> verifyAndSaveAsset(asset, assetType, hasCode).flatMap(savedValue -> {
                        printInfoLog(request, "updated successfully");
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just("{\"code\":\"" + savedValue.getCode() + "\"}"),
                                String.class);
                    }));
        });
    }

    private boolean methodCheck(ServerRequest request) {
        return request.attribute("org.springframework.web.reactive.function.server.RouterFunctions.request")
                .orElse("N/A").toString()
                .indexOf("PUT") != -1;
    }

    private Mono<Asset> verifyAndSaveAsset(Asset asset, AssetType assetType, boolean hasCode) {
        asset.setAssetTypeId(assetType.getId());
        if (asset.getCode() == null) {
            if (hasCode) {
                return Mono.error(new RuntimeException("Asset code is required for an update"));
            }
            asset.setCode(UUID.randomUUID().toString());
        } else {
            return assetRepository.findByCode(asset.getCode()).flatMap(searchedValue -> {
                asset.setId(searchedValue.getId());
                return assetRepository.save(asset)
                        .switchIfEmpty(Mono.error(
                                new RuntimeException("Problem with update asset" + asset)));
            }).switchIfEmpty(Mono.error(new RuntimeException("Asset code not found for " + asset)));
        }
        return assetRepository.save(asset)
                .switchIfEmpty(Mono.error(new RuntimeException("Problem with add Asset: " + asset)));
    }

    private Mono<AssetType> validateAssetType(String code) {
        return assetTypeRepository.findByCode(code)
                .switchIfEmpty(Mono.error(new RuntimeException("Asset type not found when add new Asset " + code)));
    }

    public Mono<ServerResponse> fetchLandingPage(ServerRequest request) {
        printInfoLog(request, "fetching landing page");
        Flux<AssetType> result = assetTypeRepository.findAll();
        return ServerResponse.ok().body(result, AssetType.class);
    }

    private void printInfoLog(ServerRequest request, String msg) {
        log.info("log-id= \"{}\",{}",
                request.attribute("org.springframework.web.server.ServerWebExchange.LOG_ID").orElse("NA"),
                msg);
    }
}