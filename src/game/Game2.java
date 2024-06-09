package game;

import app.Login2;
import app.SignUp2;
import service.UserManager2;

import java.util.Scanner;


public class Game2 {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Creëer een Scanner object om gebruikersinvoer te lezen
        UserManager2 userManager = new UserManager2(); // Creëer een UserManager2 object om gebruikers te beheren

        outer: // Om de buitenste while-lus te markeren
        while (true) {

            // Toon de opties voor de gebruiker: inloggen, registreren, top scores bekijken of het spel afsluiten
            System.out.println("Hallo en welkom bij het spel! Heeft u al een account?");
            System.out.println("1. Ja, ik heb al een account");
            System.out.println("2. Nee, ik heb nog geen account");
            System.out.println("3. Bekijk de top scores");
            System.out.println("4. Exit");
            System.out.print("Maak een keuze: ");
            int response = scanner.nextInt();
            scanner.nextLine();



            switch (response){
                case 1:
                    Login2.login(); // Als de gebruiker aangeeft dat hij al een account heeft, wordt de login-methode aangeroepen uit de Login klasse.
                    break outer; //Na succesvol inloggen, verlaat de buitenste while-lus om het spel te starten.
                case 2:
                    SignUp2.signUp(); // Als de gebruiker aangeeft dat hij nog geen account heeft, wordt de signUp-methode aangeroepen uit de SignUp klasse.
                    break;
                case 3:
                    userManager.getTopScores(); // Als de gebruiker ervoor kiest om de top scores te bekijken, wordt de getTopScores-methode aangeroepen uit de UserManager klasse.
                    break;

                case 4:
                    System.out.println("Tot ziens!"); // Als de gebruiker kiest om het programma te verlaten, wordt er een afscheidsgroet getoond.
                    break outer;

                default:
                    System.out.println("Ongeldige invoer. Probeer opnieuw met '1','2','3' of '4'.");
                    // Als de gebruiker een ongeldige keuze maakt, wordt een foutmelding getoond.

            }


        }



    }


    }

