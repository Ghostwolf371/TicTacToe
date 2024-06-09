CREATE DATABASE tictactoe;
USE tictactoe;

CREATE TABLE players (
  player_id int NOT NULL AUTO_INCREMENT UNIQUE,
  username varchar(200) PRIMARY KEY,
  code int NOT NULL,
  Date_of_Birth date NOT NULL
);

CREATE TABLE scores (
  score_id int NOT NULL PRIMARY KEY,
  score int NOT NULL,
  player varchar(200) NOT NULL,
  FOREIGN KEY (player) REFERENCES players(username)
);