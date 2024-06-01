package app;

import model.PlayerData2;
import service.UserManager2;

import java.util.Scanner;

public class SignUp2 {

    public static void signUp(){

        Scanner scanner = new Scanner(System.in);
        UserManager2 userManager = new UserManager2();
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

            PlayerData2 newUser = userManager.register(username, code, dateOfBirth);
            if (newUser != null) {
                //Als de registratie succesvol is, toon een melding
                System.out.println("User registered succesfully: " + newUser.getUsername());
                break; //Verlaat de binnenste loop na succesvolle registratie
            }

            else {
                System.out.println("Failed to register user."); //Als de registratie is mislukt, toon een foutmelding
            }
        }

    }
}
