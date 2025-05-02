CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(255) NOT NULL UNIQUE,
    amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(100) NOT NULL,
    due_date DATE,
    paid_date DATE,
    customer_name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    creator_id BIGINT,
    creator_username VARCHAR(100),
    last_modifier_id BIGINT,
    last_modifier_username VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    payment_reference VARCHAR(255) NOT NULL UNIQUE,
    amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(100) NOT NULL,
    paid_date DATE,
    payer_name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    creator_id BIGINT,
    creator_username VARCHAR(100),
    last_modifier_id BIGINT,
    last_modifier_username VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
