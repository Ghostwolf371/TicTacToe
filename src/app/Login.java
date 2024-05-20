package app;

import model.PlayerData;
import service.UserManager;

import java.util.Scanner;
import java.util.Optional;

public class Login {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);  //Creëer een scanner om gebruikersinput te lezen
        UserManager userManager = new UserManager();  //Creëer een UserManager object om gebruikers te beheren

        //Oneindige loop (totdat de voorwaarde wordt bereikt), om gebruikersinteractie te beheren
        while (true) {

            //Controleer of de gebruiker een bestaand account heeft
            System.out.println("Hello and welcome to the game! Do you already have an account? (yes/no)");
            String response = scanner.nextLine();

            //Indien de gebruiker al een account heeft, is er een optie om in te loggen
            if (response.equals("yes") || response.equals("Yes")) {
                System.out.println("Very well, let's proceed with the login!");

                //Vraag de gebruiker om een gebruikersnaam in te voeren
                System.out.println("Enter your username: ");
                String username = scanner.nextLine();


                //Vraag de gebruiker om een code in te voeren
                System.out.println("Enter your code: ");
                int code = scanner.nextInt();


                //Probeer in te loggen met de ingevoerde gebruikersnaam en code (met behulp van de database)
                Optional<PlayerData> user = userManager.login(username, code);
                if (user.isPresent()) {
                    System.out.println("Login succesful! Welcome, " + user.get().getUsername()); //Als de login succesvol is, begroet de gebruiker
                } else {
                    System.out.println("Invalid username or code."); //Als de login mislukt, toon een foutmelding
                }
                break; //Verlaat de loop na de login poging (zodat hij niet oneindig blijft draaien)
            }

            //Indien de gebruiker geen account heeft, is er een optie om te registreren
            else if (response.equals("no") || response.equals("No")) {
                System.out.println("Okay, no problem. Let's create an account!");


                //Oneindige loop (totdat de voorwaarde wordt bereikt), om gebruikers te dwingen een unieke gebruikersnaam te kiezen.
                while (true) {

                    //Vraag de gebruiker om een gebruikersnaam in te voeren
                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();

                    //Indien de gebruikersnaam al in gebruik is, toon een melding
                    if(userManager.isUsernameTaken(username)){
                        System.out.println("Username is already taken. Please choose a different username.");
                        continue; //Vraag opnieuw om een gebruikersnaam in te voeren
                    }

                    //Vraag de gebruiker om een code in te voeren
                    System.out.println("Enter your code: ");
                    int code = scanner.nextInt();
                    scanner.nextLine(); //Leeg de buffer

                    //Vraag de gebruiker om een geboortedatum in te voeren
                    System.out.println("Enter your birthdate (YYYY-MM-DD): ");
                    String dateOfBirth = scanner.nextLine();

                    PlayerData newUser = userManager.register(username, code, dateOfBirth);
                    if (newUser != null) {
                        //Als de registratie succesvol is, toon een melding
                        System.out.println("User registered succesfully: " + newUser.getUsername());
                        break; //Verlaat de binnenste loop na succesvolle registratie
                    }

                    else {
                        System.out.println("Failed to register user."); //Als de registratie is mislukt, toon een foutmelding
                    }
                }
                break; //Verlaat de buitenste loop na registratie proces
            }

            else {
                //Als de gebruiker een ongeldig antwoord geeft, toon een foutmelding
                System.out.println("Invalid input. Please try again with 'yes' or 'no'.");
            }
        }

        scanner.close(); //Sluit de scanner

    }
}
