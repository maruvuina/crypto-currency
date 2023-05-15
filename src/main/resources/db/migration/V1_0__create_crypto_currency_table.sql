CREATE TABLE IF NOT EXISTS "crypto_currencies"
(
    id bigint NOT NULL PRIMARY KEY,
    symbol character varying(5) NOT NULL,
    actual_price numeric(9,2) NOT NULL
)