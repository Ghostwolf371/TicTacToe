package app;

import model.PlayerData2;
import service.UserManager2;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Login2 {

    public static void login() {

        Scanner scanner = new Scanner(System.in); //Creëer een scanner om gebruikersinput te lezen
        UserManager2 userManager = new UserManager2(); //Creëer een UserManager object om gebruikers te beheren

        System.out.println("Heel goed, laten we doorgaan met het inloggen!");

        //Vraag de gebruiker om een gebruikersnaam in te voeren
        System.out.println("Voer uw gebruikersnaam in: ");
        String username = scanner.nextLine();


        //Vraag de gebruiker om een code in te voeren
        System.out.println("Voer uw code in: ");
        int code = scanner.nextInt();


        //Probeer in te loggen met de ingevoerde gebruikersnaam en code (met behulp van de database)
        Optional<PlayerData2> user = userManager.login(username, code);
        if (user.isPresent()) {  //Controleer of er een gebruiker is gevonden
            System.out.println("Inloggen succesvol! Welkom, " + user.get().getUsername()); //Als de login succesvol is, begroet de gebruiker


            int score = 0; //Initialiseer de score van de gebruiker

            while (true) {  // Begin de oneindige spel lus
                GameLogic2.printBoard();  // Print het spelbord
                GameLogic2.playerMove(scanner);  // Laat de speler een zet doen

                if (GameLogic2.checkWin()) {  // Controleer of er een winnaar is
                    score++; // Verhoog de score van de gebruiker
                    GameLogic2.printBoard(); // Print het spelbord

                    //Toon de winnaar
                    System.out.println("\nGefeliciteerd, " + (GameLogic2.player1_turn ? "Speler 1 ("+GameLogic2.ANSI_BLUE+"X"+ GameLogic2.ANSI_RESET+")" : "Speler 2 ("+GameLogic2.ANSI_RED+"O"+ GameLogic2.ANSI_RESET+")") + " wint!");

                    //Update de gebruikersnaam met de winnende speler
                    username = (GameLogic2.player1_turn ? "Speler 1 ("+GameLogic2.ANSI_BLUE+"X"+ GameLogic2.ANSI_RESET+")" : "Speler 2 ("+GameLogic2.ANSI_RED+"O"+ GameLogic2.ANSI_RESET+")");

                    //Vraag of de gebruiker nog een spel wil spelen
                    System.out.println("\nWilt u nog een spel spelen? (ja/nee)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();

                    //Controleer of de gebruiker wil doorgaan met een nieuw spel
                    if (!playAgainResponse.equalsIgnoreCase("ja")) {
                        userManager.saveScore(score, username); // Sla de score op in de database
                        System.out.println("Bedankt voor het spelen. Tot ziens.");
                        break; // Stop de spelloop
                    } else {
                        // Reset de spelstatus voor een nieuw spel
                        GameLogic2.player1_turn = true; // Reset de beurt naar speler 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic2.board[i], " "); // Reset het bord
                        }
                        System.out.println("Geweldig! Een nieuw spel zal binnenkort beginnen. Klaar... af... start!");
                    }
                } else if (GameLogic2.checkTie()) {  //Controleer of het gelijkspel is
                    GameLogic2.printBoard();  //Print het bord
                    System.out.println("Het is gelijkspel!");
                    System.out.println("\nDe score is: " + score);
                    System.out.println("\nWilt u nog een spel spelen? (ja/nee)");
                    scanner.nextLine();
                    String playAgainResponse = scanner.nextLine();
                    if (!playAgainResponse.equalsIgnoreCase("ja")) {
                        break ; // Stop de spelloop
                    } else {
                        // Reset de spelstatus voor een nieuw spel
                        GameLogic2.player1_turn = true; // Reset beurt naar speler 1
                        for (int i = 0; i < 3; i++) {
                            Arrays.fill(GameLogic2.board[i], " "); // Reset het bord
                        }
                        System.out.println("Geweldig! Een nieuw spel zal binnenkort beginnen. Klaar... af... start!");
                    }
                } else {
                    GameLogic2.player1_turn = !GameLogic2.player1_turn; // Wissel beurten
                }
            }


        } else {
            System.out.println("Ongeldige gebruikersnaam of code."); //Als de login mislukt, toon een foutmelding
        }
    }
}
