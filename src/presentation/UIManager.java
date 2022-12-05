package presentation;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;



public class UIManager {

    Scanner scanner;

    public UIManager(){this.scanner = new Scanner(System.in);}

    public void loadData(){
        System.out.println("Welcome to Simple LSRPG");
        System.out.println("\n\nLoading data...");
    }


    public void showMainMenu(){
        System.out.println("The tavern keeper looks at you and says:\n");
        System.out.println("\"Welcome adventurer! How can I help you?\"\n");
        System.out.println("\n\t1) Character creation");
        System.out.println("\n\t2) List characters");
        System.out.println("\n\t3) Create an adventure");
        System.out.println("\n\t4) Start an adventure");
        System.out.println("\n\t5) Exit");
    }

    public void showMessage(String message){
        System.out.println(message);
    }


    public void showList(LinkedList<String> items){
        for (String item : items) {
            System.out.println("\t* " + item);
        }
    }

    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("This isn't an integer!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}
