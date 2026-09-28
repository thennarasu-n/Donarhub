CREATE DATABASE IF NOT EXISTS donatehub;
USE donatehub;

CREATE TABLE drives (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (id),
    CHECK (end_date >= start_date)
);

CREATE TABLE donors (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_donor_email (email)
);

CREATE TABLE recipients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    organization VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE donated_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    category VARCHAR(20) NOT NULL,
    item_condition VARCHAR(20) NOT NULL,
    collected BOOLEAN NOT NULL DEFAULT FALSE,
    donor_id BIGINT NOT NULL,
    drive_id BIGINT NOT NULL,
    donated_date DATE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_donated_item_donor FOREIGN KEY (donor_id) REFERENCES donors(id),
    CONSTRAINT fk_donated_item_drive FOREIGN KEY (drive_id) REFERENCES drives(id),
    INDEX idx_item_category (category),
    INDEX idx_item_collected (collected),
    INDEX idx_item_drive (drive_id),
    INDEX idx_item_donor (donor_id)
);

CREATE TABLE distributions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    distributed_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_distribution_item (item_id),
    CONSTRAINT fk_distribution_item FOREIGN KEY (item_id) REFERENCES donated_items(id),
    CONSTRAINT fk_distribution_recipient FOREIGN KEY (recipient_id) REFERENCES recipients(id),
    INDEX idx_distribution_recipient (recipient_id)
);
