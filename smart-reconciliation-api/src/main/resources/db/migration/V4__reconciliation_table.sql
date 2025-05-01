CREATE TABLE reconciliations (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT,
    payment_id BIGINT NOT NULL,
    status VARCHAR(100) NOT NULL,
    matched_on DATE,
    user_id BIGINT NOT NULL,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP
);
