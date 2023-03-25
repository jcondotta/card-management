CREATE TABLE IF NOT EXISTS card
(
    card_id                  BIGINT NOT NULL auto_increment PRIMARY KEY,
    cardholder_name          VARCHAR(21) NOT NULL,
    card_status              VARCHAR(15) NOT NULL,
    daily_withdrawal_limit   int NOT NULL
--     card_number              VARCHAR(40) NOT NULL,
--     expiration_date          date NOT NULL,
--     security_code            VARCHAR(3) NOT NULL
)
engine=innodb;