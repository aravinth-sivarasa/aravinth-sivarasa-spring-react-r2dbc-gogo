CREATE TABLE IF NOT EXISTS "asset_type" (
    id SERIAL PRIMARY KEY,
    code VARCHAR (255) NOT NULL UNIQUE,
    description VARCHAR (255)
);

INSERT INTO
    asset_type (code, description)
SELECT
    'LPT',
    'Laptop'
WHERE
    NOT EXISTS (
        SELECT
            id
        FROM
            asset_type
        WHERE
            code = 'LPT'
    );

CREATE TABLE IF NOT EXISTS "asset" (
    id SERIAL PRIMARY KEY,
    code VARCHAR (255) NOT NULL UNIQUE,
    description VARCHAR (255)
);

ALTER TABLE
    "asset" DROP CONSTRAINT IF EXISTS asset_type_fk;

ALTER TABLE
    IF EXISTS "asset"
ADD
    IF NOT EXISTS asset_type_id INTEGER,
ADD
    CONSTRAINT asset_type_fk FOREIGN KEY (asset_type_id) REFERENCES asset_type (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION NOT VALID;