package io.product.rnd.sample.asset.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.product.rnd.sample.asset.domain.Asset;
import reactor.core.publisher.Mono;

public interface AssetRepository extends ReactiveCrudRepository<Asset, Long> {

    @Query("SELECT * FROM asset WHERE code = :code")
    Mono<Asset> findByCode(String code);

}
