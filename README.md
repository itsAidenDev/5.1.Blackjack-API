# 🃏 Blackjack API - Reactive Spring Boot Application

## 📋 Description

This project is a **Blackjack game API** developed in **Java** using **Spring Boot**, with a **reactive architecture** based on **Spring WebFlux**. It is designed to connect and manage data from two types of databases: **MongoDB** (NoSQL, reactive) and **MySQL** (relational).

---

## 🎯 Project Goals

The main objectives of this project are:

- To learn how to build a **reactive API in Java** using Spring Boot.
- To connect the application to both **relational (MySQL)** and **non-relational (MongoDB)** databases.
- To apply **unit testing** techniques with JUnit and Mockito.
- To document the API using **Swagger/OpenAPI**.
- To gain knowledge on **dockerizing** a Spring Boot application and **deploying** it to a web server.

---

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Spring WebFlux
- MongoDB (Reactive)
- MySQL
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Project Reactor
- Docker

---

## ✅ Features

- Fully reactive architecture with Spring WebFlux.
- Dual database support: MongoDB and MySQL.
- Global exception handling with `GlobalExceptionHandler`.
- Unit tests for key services and controllers.
- Automatically generated API documentation with Swagger.

---

## 📌 Main Endpoints

### 🎲 Create a new game
- **Method:** `POST`
- **Endpoint:** `/game/new`
- **Request Body:** New player's name
- **Response:** `201 Created` with game information

---

### 📄 Get game details
- **Method:** `GET`
- **Endpoint:** `/game/{id}`
- **Path Parameter:** `id` (game identifier)
- **Response:** `200 OK` with game details

---

### 🃏 Make a move
- **Method:** `POST`
- **Endpoint:** `/game/{id}/play`
- **Request Body:** Move details (type, bet amount)
- **Response:** `200 OK` with the result and updated game state

---

### ❌ Delete a game
- **Method:** `DELETE`
- **Endpoint:** `/game/{id}/delete`
- **Response:** `204 No Content` if the game is deleted successfully

---

### 🏆 Get player ranking
- **Method:** `GET`
- **Endpoint:** `/ranking`
- **Response:** `200 OK` with a list of players sorted by performance

---

### ✏️ Update player's name
- **Method:** `PUT`
- **Endpoint:** `/player/{playerId}`
- **Request Body:** New player name
- **Response:** `200 OK` with updated player information

---
