package io.product.rnd.sample.asset.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.product.rnd.sample.asset.domain.AssetType;
import reactor.core.publisher.Mono;

public interface AssetTypeRepository extends ReactiveCrudRepository<AssetType, Long> {

    @Query("SELECT * FROM asset_type WHERE code = :code")
    Mono<AssetType> findByCode(String code);

    @Query("" +
            "INSERT INTO asset (code, description, asset_type_id)" +
            "VALUES" +
            "( :code ,description,(SELECT id from asset_type WHERE code=:assetTypeCode))")

    Mono<Integer> insertWithCode(String code, String description, String assetTypeCode);
}
