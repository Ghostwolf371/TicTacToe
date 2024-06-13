package app;

import java.util.Scanner;

public class GameLogic {

    public static final String ANSI_BLUE = "\u001B[34m"; // Kleurcode voor blauw
    public static final String ANSI_RED = "\u001B[31m";  // Kleurcode voor rood
    public static final String ANSI_RESET = "\u001B[0m"; // Reset de kleur naar standaard

    // Geeft aan wiens beurt het is, true voor player 1 false voor player 2
    static boolean player1_turn = true;
    public static String X = ANSI_BLUE + "X" + ANSI_RESET; // Player X is blauw
    public static String O = ANSI_RED + "O" + ANSI_RESET;  // Player O is rood


    static String[][] board = {
            // Het 3x3 rooster dat het tic tac toe board voorstelt
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}
    };
    public static void printBoard() {
        // Het huidige bord weergeven
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("-+-+-");
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("-+-+-");
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
    }
    public static void playerMove(Scanner scanner) {
        // Verwerkt de zet van de speler
        int pos;
        while (true) {
            if (player1_turn) {
                System.out.println("Player 1 ("+ANSI_BLUE+"X"+ANSI_RESET+") Enter your move (1-9) ");
            } else {
                System.out.println("Player 2 ("+ GameLogic.ANSI_RED+"O"+ GameLogic.ANSI_RESET+") Enter your move (1-9) ");
            }
            pos = scanner.nextInt();
            if (isValidMove(pos)) { // Controleert of de zet geldig is
                placeMove(pos); // De zet op het bord plaatsen
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
    public static boolean isValidMove(int pos) {
        // Controleert of de zet geldig is
        return switch (pos) {
            case 1 -> board[0][0] == " ";
            case 2 -> board[0][1] == " ";
            case 3 -> board[0][2] == " ";
            case 4 -> board[1][0] == " ";
            case 5 -> board[1][1] == " ";
            case 6 -> board[1][2] == " ";
            case 7 -> board[2][0] == " ";
            case 8 -> board[2][1] == " ";
            case 9 -> board[2][2] == " ";
            default -> false;
        };
    }
    public static void placeMove(int pos) {
        // De zet van de speler op het bord plaatsen
        String symbol = player1_turn ? X : O; //  Het symbool bepalen op basis van wiens beurt het is


        switch (pos) {  //Plaats het symbool op de juiste positie op het bord
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
                (board[0][0] == X && board[0][1] == X && board[0][2] == X) ||
                        (board[1][0] == X && board[1][1] == X && board[1][2] == X) ||
                        (board[2][0] == X && board[2][1] == X && board[2][2] == X) ||
                        (board[0][0] == X && board[1][0] == X && board[2][0] == X) ||
                        (board[0][1] == X && board[1][1] == X && board[2][1] == X) ||
                        (board[0][2] == X && board[1][2] == X && board[2][2] == X) ||
                        (board[0][0] == X && board[1][1] == X && board[2][2] == X) ||
                        (board[0][2] == X && board[1][1] == X && board[2][0] == X)
        ) {
            System.out.println("Player 1 ("+ GameLogic.ANSI_BLUE+"X"+ GameLogic.ANSI_RESET+") wins!");
            return true;
        }

        // Controleren voor O winstcondities
        if (
                (board[0][0] == O && board[0][1] == O && board[0][2] == O) ||
                        (board[1][0] == O && board[1][1] == O && board[1][2] == O) ||
                        (board[2][0] == O && board[2][1] == O && board[2][2] == O) ||
                        (board[0][0] == O && board[1][0] == O && board[2][0] == O) ||
                        (board[0][1] == O && board[1][1] == O && board[2][1] == O) ||
                        (board[0][2] == O && board[1][2] == O && board[2][2] == O) ||
                        (board[0][0] == O && board[1][1] == O && board[2][2] == O) ||
                        (board[0][2] == O && board[1][1] == O && board[2][0] == O)
        ) {
            System.out.println("Player 2 ("+ GameLogic.ANSI_RED+"O"+ GameLogic.ANSI_RESET+") wins!");
            return true;
        }

        return false;
    }
    static boolean checkTie() {
        // Controleert of het spel gelijkspel is
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == " ") {
                    return false; // // Er is nog een lege plek
                }
            }
        }
        return true; // Geen lege plekken, het is gelijkspel
    }
}
