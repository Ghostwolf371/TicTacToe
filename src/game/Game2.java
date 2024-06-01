package game;

import app.Login2;
import app.SignUp2;
import service.UserManager2;

import java.util.Scanner;


public class Game2 {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserManager2 userManager = new UserManager2();

        outer:
        while (true) {

            System.out.println("Hello and welcome to the game! Do you already have an account?");
            System.out.println("1. Yes i do have a account");
            System.out.println("2. No i don't have a account");
            System.out.println("3. Get the top scores");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int response = scanner.nextInt();
            scanner.nextLine();



            switch (response){
                case 1:
                    Login2.login();
                    break outer;
                case 2:
                    SignUp2.signUp();
                    break;
                case 3:
                    userManager.getTopScores();
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid input. Please try again with 'yes' or 'no'.");

            }


        }



    }


    }

