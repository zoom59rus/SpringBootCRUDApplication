CREATE TABLE IF NOT EXISTS accounts
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    login    varchar(50),
    password varchar(255),
    status   varchar(50),
    created  TIMESTAMP DEFAULT NOW(),
    updated  TIMESTAMP DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS accounts_roles
(
    account_id BIGINT,
    role_id    BIGINT
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    username   varchar(100),
    email      varchar(100),
    first_name varchar(100),
    last_name  varchar(100),
    account_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS files
(
    id            bigint NOT NULL AUTO_INCREMENT,
    name          varchar(255),
    type          varchar(50),
    size          FLOAT     DEFAULT 0.00,
    status        varchar(50),
    upload        TIMESTAMP DEFAULT NOW(),
    last_modified TIMESTAMP DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_files
(
    user_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    file_id BIGINT,
    created TIMESTAMP DEFAULT NOW(),
    event   varchar(255),
    PRIMARY KEY (id)
);