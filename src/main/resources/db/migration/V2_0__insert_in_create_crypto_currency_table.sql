INSERT INTO crypto_currencies(
    id, symbol, actual_price)
VALUES (90, 'BTC', 0.0)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO crypto_currencies(
    id, symbol, actual_price)
VALUES (80, 'ETH', 0.0)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO crypto_currencies(
    id, symbol, actual_price)
VALUES (48543, 'SOL', 0.0)
    ON CONFLICT (id) DO NOTHING;
