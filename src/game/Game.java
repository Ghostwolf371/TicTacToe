package game;

import java.util.Random;
import java.util.Scanner;

public class Game {

        static boolean player1_turn = true;
        // Om aan te geven wiens beurt het is, true voor player 1 false voor player 2

        static char[][] board = {
                // Het 3x3 rooster dat het tic tac toe board voorstelt
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();

            if (random.nextInt(2) == 0) {
                // kiest willekeurig wie als eerst gaat spelen
                player1_turn = true;
                System.out.println("X turn");
            } else {
                player1_turn = false;
                System.out.println("O turn");
            }

            while (true) { // Main game loop
                printBoard(); // Geeft het huidige bord weer
                playerMove(scanner); // Verwerkt de zet van de huidige speler
                if (checkWin()) { // Controleert of de huidige speler heeft gewonnen
                    printBoard();
                    break; // Beëinigd de spelloop als er een winnaar is
                }
                if (checkTie()) { //Controleert als er een gelijkspel is in de spel
                    printBoard();
                    System.out.println("It's a tie!");
                    break; // Beëinigd de spelloop als er een gelijkspel is
                }
                player1_turn = !player1_turn; // Verwisselen van beurten tussen spelers
            }
            scanner.close();
        }

        public static void printBoard() {
            // Het huidige bord weergeven
            System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
            System.out.println("-+-+-");
            System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
            System.out.println("-+-+-");
            System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
        }

        public static void playerMove(Scanner scanner) {
            // De zet van de speler verwerkt
            int pos;
            while (true) {
                if (player1_turn) {
                    System.out.println("Player 1 (X) Enter your move (1-9) ");
                } else {
                    System.out.println("Player 2 (O) Enter your move (1-9) ");
                }
                pos = scanner.nextInt();
                if (isValidMove(pos)) { // Controleren of de zet geldig is
                    placeMove(pos); // De zet op het bord plaatsen
                    break;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
        }

        public static boolean isValidMove(int pos) {
            // Controleren of de zet geldig is
            switch (pos) {
                case 1:
                    return board[0][0] == ' ';
                case 2:
                    return board[0][1] == ' ';
                case 3:
                    return board[0][2] == ' ';
                case 4:
                    return board[1][0] == ' ';
                case 5:
                    return board[1][1] == ' ';
                case 6:
                    return board[1][2] == ' ';
                case 7:
                    return board[2][0] == ' ';
                case 8:
                    return board[2][1] == ' ';
                case 9:
                    return board[2][2] == ' ';
                default:
                    return false;
            }
        }

        public static void placeMove(int pos) {
            // De zet van de speler op het bord plaatsen
            char symbol = player1_turn ? 'X' : 'O'; //  Het symbool bepalen op basis van de beurt van de speler

            switch (pos) {
                case 1:
                    board[0][0] = symbol;
                    break;
                case 2:
                    board[0][1] = symbol;
                    break;
                case 3:
                    board[0][2] = symbol;
                    break;
                case 4:
                    board[1][0] = symbol;
                    break;
                case 5:
                    board[1][1] = symbol;
                    break;
                case 6:
                    board[1][2] = symbol;
                    break;
                case 7:
                    board[2][0] = symbol;
                    break;
                case 8:
                    board[2][1] = symbol;
                    break;
                case 9:
                    board[2][2] = symbol;
                    break;
                default:
                    break;
            }
        }

        static boolean checkWin() {
            // Controleren of er een winnende voorwaarde is

            // Controleren voor X winstcondities
            if (
                    (board[0][0] == 'X' && board[0][1] == 'X' && board[0][2] == 'X') ||
                            (board[1][0] == 'X' && board[1][1] == 'X' && board[1][2] == 'X') ||
                            (board[2][0] == 'X' && board[2][1] == 'X' && board[2][2] == 'X') ||
                            (board[0][0] == 'X' && board[1][0] == 'X' && board[2][0] == 'X') ||
                            (board[0][1] == 'X' && board[1][1] == 'X' && board[2][1] == 'X') ||
                            (board[0][2] == 'X' && board[1][2] == 'X' && board[2][2] == 'X') ||
                            (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') ||
                            (board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X')
            ) {
                System.out.println("Player 1 (X) wins!");
                return true;
            }

            // Controleren voor O winstcondities
            if (
                    (board[0][0] == 'O' && board[0][1] == 'O' && board[0][2] == 'O') ||
                            (board[1][0] == 'O' && board[1][1] == 'O' && board[1][2] == 'O') ||
                            (board[2][0] == 'O' && board[2][1] == 'O' && board[2][2] == 'O') ||
                            (board[0][0] == 'O' && board[1][0] == 'O' && board[2][0] == 'O') ||
                            (board[0][1] == 'O' && board[1][1] == 'O' && board[2][1] == 'O') ||
                            (board[0][2] == 'O' && board[1][2] == 'O' && board[2][2] == 'O') ||
                            (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') ||
                            (board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O')
            ) {
                System.out.println("Player 2 (O) wins!");
                return true;
            }

            return false;
        }


        static boolean checkTie() {
            // CControleren of het spel gelijkspel is
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        return false; // // Er is nog een lege plek
                    }
                }
            }
            return true; // Geen lege plekken, het is gelijkspel
        }
    }

