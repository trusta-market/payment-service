CREATE TABLE IF NOT EXISTS p_wallet_charge_retry (
    id                          UUID        NOT NULL,
    payment_id                  UUID        NOT NULL,
    user_id                     UUID        NOT NULL,
    point_tx_request_history_id UUID        NOT NULL,
    payment_status              VARCHAR(20) NOT NULL,
    amount                      BIGINT      NOT NULL,
    status                      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    retry_count                 INT         NOT NULL DEFAULT 0,
    last_attempted_at           TIMESTAMPTZ,
    created_at                  TIMESTAMPTZ,
    updated_at                  TIMESTAMPTZ,
    CONSTRAINT pk_p_wallet_charge_retry PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS p_wallet_withdraw_retry (
    id                          UUID        NOT NULL,
    payout_id                   UUID        NOT NULL,
    user_id                     UUID        NOT NULL,
    point_tx_request_history_id UUID        NOT NULL,
    payout_status               VARCHAR(20) NOT NULL,
    amount                      BIGINT      NOT NULL,
    status                      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    retry_count                 INT         NOT NULL DEFAULT 0,
    last_attempted_at           TIMESTAMPTZ,
    created_at                  TIMESTAMPTZ,
    updated_at                  TIMESTAMPTZ,
    CONSTRAINT pk_p_wallet_payout_retry PRIMARY KEY (id)
    );

