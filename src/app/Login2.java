package app;

import model.PlayerData2;
import service.UserManager2;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Login2 {

    public static void login() {

        Scanner scanner = new Scanner(System.in);
        UserManager2 userManager = new UserManager2();

        System.out.println("Very well, let's proceed with the login!");

        //Vraag de gebruiker om een gebruikersnaam in te voeren
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();


        //Vraag de gebruiker om een code in te voeren
        System.out.println("Enter your code: ");
        int code = scanner.nextInt();


        //Probeer in te loggen met de ingevoerde gebruikersnaam en code (met behulp van de database)
        Optional<PlayerData2> user = userManager.login(username, code);
        if (user.isPresent()) {
            System.out.println("Login succesful! Welcome, " + user.get().getUsername()); //Als de login succesvol is, begroet de gebruiker


            int score = 0;
            while (true) {
                GameLogic2.printBoard();
                GameLogic2.playerMove(scanner);

                if (GameLogic2.checkWin()) {
                    score++;
                    GameLogic2.printBoard();
                    System.out.println("\nCongratulations, " + (GameLogic2.player1_turn ? "Player 1 ("+GameLogic2.ANSI_BLUE+"X"+ GameLogic2.ANSI_RESET+")" : "Player 2 ("+GameLogic2.ANSI_RED+"O"+ GameLogic2.ANSI_RESET+")") + " wins!");
                    username = (GameLogic2.player1_turn ? "Player 1 ("+GameLogic2.ANSI_BLUE+"X"+ GameLogic2.ANSI_RESET+")" : "Player 2 ("+GameLogic2.ANSI_RED+"O"+ GameLogic2.ANSI_RESET+")");
                    System.out.println("\nDo you want to play another game? (yes/no)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();
                    if (!playAgainResponse.equalsIgnoreCase("yes")) {
                        userManager.saveScore(score, username);
                        System.out.println("Thanks for playing. Goodbye.");
                        break; // Exit the game loop
                    } else {
                        // Reset the game state for a new game
                        GameLogic2.player1_turn = true; // Reset turn to Player 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic2.board[i], " "); // Reset the board
                        }
                        System.out.println("Great A new game will start soon. Ready... set... go!");
                    }
                } else if (GameLogic2.checkTie()) {
                    GameLogic2.printBoard();
                    System.out.println("It's a tie!");
                    System.out.println("\nThe score is: " + score);
                    System.out.println("\nDo you want to play another game? (yes/no)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();
                    if (!playAgainResponse.equalsIgnoreCase("yes")) {
                        break ; // Exit the game loop
                    } else {
                        // Reset the game state for a new game
                        GameLogic2.player1_turn = true; // Reset turn to Player 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic2.board[i], " "); // Reset the board
                        }
                        System.out.println("Great A new game will start soon. Ready... set... go!");
                    }
                } else {
                    GameLogic2.player1_turn = !GameLogic2.player1_turn; // Switch turns
                }
            }


        } else {
            System.out.println("Invalid username or code."); //Als de login mislukt, toon een foutmelding
        }
    }
}
