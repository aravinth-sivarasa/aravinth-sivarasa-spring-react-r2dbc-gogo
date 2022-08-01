package io.product.rnd.sample.asset.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.product.rnd.sample.asset.domain.AssetType;
import reactor.core.publisher.Mono;

public interface AssetTypeRepository extends ReactiveCrudRepository<AssetType, Long> {

    @Query("SELECT * FROM asset WHERE code = :code")
    Mono<AssetType> findByCode(String code);

}
