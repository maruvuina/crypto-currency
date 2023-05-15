CREATE TABLE IF NOT EXISTS "users"
(
    id bigserial NOT NULL PRIMARY KEY,
    username character varying(255) NOT NULL,
    user_price numeric(9,2) NOT NULL,
    currency_id bigint NOT NULL,
    CONSTRAINT currency_fkey FOREIGN KEY (currency_id)
    REFERENCES crypto_currencies (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);