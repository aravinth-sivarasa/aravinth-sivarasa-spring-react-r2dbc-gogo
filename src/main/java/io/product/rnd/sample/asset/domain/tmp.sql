INSERT INTO
    asset (code, description, asset_type_id)
SELECT
    'aaa',
    'bbbbbbb',
    asset_type.id
FROM
    asset
    join asset_type ON asset.asset_type_id = asset_type.id
WHERE
    asset_type.code = 'LPT'