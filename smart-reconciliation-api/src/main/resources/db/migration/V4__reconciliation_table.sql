CREATE TABLE reconciliations (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT,
    payment_id BIGINT NOT NULL,
    status VARCHAR(100) NOT NULL,
    matched_on DATE,
    user_id BIGINT NOT NULL,
    creator_id BIGINT,
    creator_username VARCHAR(100),
    last_modifier_id BIGINT,
    last_modifier_username VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
