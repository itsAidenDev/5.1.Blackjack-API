CREATE DATABASE IF NOT EXISTS blackjack_players_db;
USE blackjack_players_db;

CREATE TABLE IF NOT EXISTS players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    total_games INT NOT NULL DEFAULT 0,
    total_wins INT NOT NULL DEFAULT 0,
    total_losses INT NOT NULL DEFAULT 0,
    total_draws INT NOT NULL DEFAULT 0,
    total_points DECIMAL(10, 2) NOT NULL DEFAULT 0
);