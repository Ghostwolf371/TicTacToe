package app;

import model.PlayerData;
import service.UserManager;

import java.util.Scanner;

public class SignUp {

    public static void signUp(){

        Scanner scanner = new Scanner(System.in); //Creëer een scanner om gebruikersinput te lezen
        UserManager userManager = new UserManager(); //Creëer een UserManager object om gebruikers te beheren
        System.out.println("Okay, geen probleem. Laten we een account aanmaken!");


        //Oneindige loop (totdat de voorwaarde wordt bereikt), om gebruikers te dwingen een unieke gebruikersnaam te kiezen.
        while (true) {

            //Vraag de gebruiker om een gebruikersnaam in te voeren
            System.out.println("Voer uw gebruikersnaam in: ");
            String username = scanner.nextLine();

            //Indien de gebruikersnaam al in gebruik is, toon een melding en vraag opnieuw om een gebruikersnaam in te voeren
            if(userManager.isUsernameTaken(username)){
                System.out.println("Gebruikersnaam is al in gebruik. Kies alstublieft een andere gebruikersnaam."); //Vraag opnieuw om een gebruikersnaam in te voeren
                continue;
            }

            //Vraag de gebruiker om een code in te voeren
            System.out.println("Voer uw code in: ");
            int code = scanner.nextInt();
            scanner.nextLine(); //Leeg de buffer

            //Vraag de gebruiker om een geboortedatum in te voeren
            System.out.println("Voer uw geboortedatum in (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();

            PlayerData newUser = userManager.register(username, code, dateOfBirth);
            if (newUser != null) {
                //Als de registratie succesvol is, toon een melding
                System.out.println("Gebruiker succesvol geregistreerd: " + newUser.getUsername());
                break; //Verlaat de binnenste loop na succesvolle registratie
            }

            else {
                System.out.println("Registratie van gebruiker is mislukt."); //Als de registratie is mislukt, toon een foutmelding
            }
        }

    }
}
