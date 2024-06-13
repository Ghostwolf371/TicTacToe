package app;

import model.PlayerData;
import service.UserManager;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Login {

    public static void login() {

        Scanner scanner = new Scanner(System.in); //Creëer een scanner om gebruikersinput te lezen
        UserManager userManager = new UserManager(); //Creëer een UserManager object om gebruikers te beheren

        System.out.println("Heel goed, laten we doorgaan met het inloggen!");

        //Vraag de gebruiker om een gebruikersnaam in te voeren
        System.out.println("Voer uw gebruikersnaam in: ");
        String username = scanner.nextLine();


        //Vraag de gebruiker om een code in te voeren
        System.out.println("Voer uw code in: ");
        int code = scanner.nextInt();


        //Probeer in te loggen met de ingevoerde gebruikersnaam en code (met behulp van de database)
        Optional<PlayerData> user = userManager.login(username, code);
        if (user.isPresent()) {  //Controleer of er een gebruiker is gevonden
            System.out.println("Inloggen succesvol! Welkom, " + user.get().getUsername()); //Als de login succesvol is, begroet de gebruiker


            int score = 0; //Initialiseer de score van de gebruiker

            while (true) {  // Begin de oneindige spel lus
                GameLogic.printBoard();  // Print het spelbord
                GameLogic.playerMove(scanner);  // Laat de speler een zet doen

                if (GameLogic.checkWin()) {  // Controleer of er een winnaar is
                    score++; // Verhoog de score van de gebruiker
                    GameLogic.printBoard(); // Print het spelbord

                    //Toon de winnaar
                    System.out.println("\nGefeliciteerd, " + (GameLogic.player1_turn ? "Speler 1 ("+ GameLogic.ANSI_BLUE+"X"+ GameLogic.ANSI_RESET+")" : "Speler 2 ("+ GameLogic.ANSI_RED+"O"+ GameLogic.ANSI_RESET+")") + " wint!");


                    //Vraag of de gebruiker nog een spel wil spelen
                    System.out.println("\nWilt u nog een spel spelen? (ja/nee)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();

                    //Controleer of de gebruiker wil doorgaan met een nieuw spel
                    if (!playAgainResponse.equalsIgnoreCase("ja")) {
                        userManager.saveScore(score); // Sla de score op in de database
                        System.out.println("Bedankt voor het spelen. Tot ziens.");
                        break; // Stop de spelloop
                    } else {
                        // Reset de spelstatus voor een nieuw spel
                        GameLogic.player1_turn = true; // Reset de beurt naar speler 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic.board[i], " "); // Reset het bord
                        }
                        System.out.println("Geweldig! Een nieuw spel zal binnenkort beginnen. Klaar... af... start!");
                    }
                } else if (GameLogic.checkTie()) {  //Controleer of het gelijkspel is
                    GameLogic.printBoard();  //Print het bord
                    System.out.println("Het is gelijkspel!");
                    System.out.println("\nDe score is: " + score);
                    System.out.println("\nWilt u nog een spel spelen? (ja/nee)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();
                    if (!playAgainResponse.equalsIgnoreCase("ja")) {
                        break ; // Stop de spelloop
                    } else {
                        // Reset de spelstatus voor een nieuw spel
                        GameLogic.player1_turn = true; // Reset beurt naar speler 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic.board[i], " "); // Reset het bord
                        }
                        System.out.println("Geweldig! Een nieuw spel zal binnenkort beginnen. Klaar... af... start!");
                    }
                } else {
                    GameLogic.player1_turn = !GameLogic.player1_turn; // Wissel beurten
                }
            }


        } else {
            System.out.println("Ongeldige gebruikersnaam of code."); //Als de login mislukt, toon een foutmelding
        }
    }
}
