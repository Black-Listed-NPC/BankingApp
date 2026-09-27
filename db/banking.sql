-- WAP: JDBC Database Script
-- Run this in MySQL if you want to connect a real database

CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

CREATE TABLE accounts (
    account_number VARCHAR(10) PRIMARY KEY,
    holder_name    VARCHAR(100),
    balance        DOUBLE,
    password       VARCHAR(50)
);

INSERT INTO accounts VALUES ('ACC001', 'Rahul Sharma', 50000, 'rahul123');
INSERT INTO accounts VALUES ('ACC002', 'Priya Patel',  30000, 'priya123');
INSERT INTO accounts VALUES ('ACC003', 'Amit Kumar',   75000, 'amit123');

CREATE TABLE transactions (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    account_no  VARCHAR(10),
    type        VARCHAR(50),
    amount      DOUBLE,
    txn_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
