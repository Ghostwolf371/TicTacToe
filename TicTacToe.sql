CREATE DATABASE tictactoe;
USE tictactoe;


CREATE TABLE players (
  player_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(200) NOT NULL UNIQUE,
  code int NOT NULL,
  Date_of_Birth date NOT NULL
);

CREATE TABLE scores (
  score_id int NOT NULL PRIMARY KEY,
  score int NOT NULL,
  FOREIGN KEY (score_id) REFERENCES players(player_id)
);
