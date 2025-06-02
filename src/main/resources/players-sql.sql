CREATE DATABASE IF NOT EXISTS blackjack_db;
USE blackjack_db;

CREATE TABLE IF NOT EXISTS games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    totalGames INT NOT NULL,
    totalWins INT NOT NULL,
    totalLosses INT NOT NULL,
    totalDraws INT NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);