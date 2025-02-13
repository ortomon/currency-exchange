CREATE TABLE if not exists Currencies (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    sign VARCHAR(10) NOT NULL
    );

CREATE UNIQUE INDEX idx_currency_code ON Currencies(Code);

CREATE TABLE if not exists ExchangeRates (
                                             id SERIAL PRIMARY KEY,
                                             base_currency_id INT NOT NULL,
                                             target_currencyId INT NOT NULL,
                                             rate DECIMAL(10,6) NOT NULL,
    UNIQUE (base_currency_id, target_currencyId),
    FOREIGN KEY (base_currency_id) REFERENCES Currencies(ID) ON DELETE CASCADE,
    FOREIGN KEY (target_currencyId) REFERENCES Currencies(ID) ON DELETE CASCADE
    );

CREATE UNIQUE INDEX idx_exchange_rate_pair ON ExchangeRates(base_currency_id, target_currencyId);

INSERT INTO Currencies (code, full_name, sign) VALUES
                                                   ('USD', 'United States Dollar', '$'),
                                                   ('EUR', 'Euro', '€'),
                                                   ('GBP', 'British Pound', '£'),
                                                   ('JPY', 'Japanese Yen', '¥'),
                                                   ('RUB', 'Russian Ruble', '₽');

INSERT INTO ExchangeRates (base_currency_id, target_currencyId, rate) VALUES
                                                                          (1, 2, 0.92), -- USD -> EUR
                                                                          (2, 1, 1.09), -- EUR -> USD
                                                                          (1, 3, 0.78), -- USD -> GBP
                                                                          (3, 1, 1.28), -- GBP -> USD
                                                                          (1, 4, 150.50); -- USD -> JPY

