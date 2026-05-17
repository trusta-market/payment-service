--liquibase formatted sql

--changeset seungwon:1
CREATE TABLE IF NOT EXISTS p_payments (
    payment_id                  UUID         NOT NULL,
    created_at                  TIMESTAMPTZ,
    updated_at                  TIMESTAMPTZ,
    amount                      BIGINT       NOT NULL,
    payment_key                 VARCHAR(200),
    payment_status              VARCHAR(30)  NOT NULL,
    point_tx_request_history_id UUID         NOT NULL,
    user_id                     UUID         NOT NULL,
    version                     INT          NOT NULL,
    CONSTRAINT pk_p_payments                          PRIMARY KEY (payment_id),
    CONSTRAINT uk_p_payments_point_tx_request_history UNIQUE (point_tx_request_history_id)
);

--changeset seungwon:2
CREATE TABLE IF NOT EXISTS p_payment_transactions (
    payment_tx_id       UUID         NOT NULL,
    created_at          TIMESTAMPTZ,
    amount              BIGINT       NOT NULL,
    payment_key         VARCHAR(200),
    pg_response_code    VARCHAR(50),
    pg_response_message VARCHAR(255),
    tx_type             VARCHAR(30)  NOT NULL,
    user_id             UUID         NOT NULL,
    payment_id          UUID         NOT NULL,
    CONSTRAINT pk_p_payment_transactions PRIMARY KEY (payment_tx_id),
    CONSTRAINT fk_payment_tx_payment     FOREIGN KEY (payment_id) REFERENCES p_payments (payment_id)
);

--changeset seungwon:3
CREATE TABLE IF NOT EXISTS p_payouts (
    payout_id                   UUID         NOT NULL,
    created_at                  TIMESTAMPTZ,
    updated_at                  TIMESTAMPTZ,
    account_number              VARCHAR(30),
    amount                      BIGINT       NOT NULL,
    bank_code                   VARCHAR(30),
    point_tx_request_history_id UUID         NOT NULL,
    status                      VARCHAR(30)  NOT NULL,
    user_id                     UUID         NOT NULL,
    version                     INT          NOT NULL,
    CONSTRAINT pk_p_payouts                          PRIMARY KEY (payout_id),
    CONSTRAINT uk_p_payouts_point_tx_request_history UNIQUE (point_tx_request_history_id)
);

--changeset seungwon:4
CREATE TABLE IF NOT EXISTS p_payout_transactions (
    payout_tx_id   UUID         NOT NULL,
    created_at     TIMESTAMPTZ,
    amount         BIGINT       NOT NULL,
    failure_reason VARCHAR(255),
    tx_type        VARCHAR(30)  NOT NULL,
    payout_id      UUID         NOT NULL,
    CONSTRAINT pk_p_payout_transactions PRIMARY KEY (payout_tx_id),
    CONSTRAINT fk_payout_tx_payout      FOREIGN KEY (payout_id) REFERENCES p_payouts (payout_id)
);
