package presentation;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;



public class UIManager {

    // Añadimos un scanner
    Scanner scanner;

    /**
     * Esta función servirá para construir UIManager
     * No tendrá ni param ni return
     */
    public UIManager(){this.scanner = new Scanner(System.in);}

    /**
     * Esta función servirá para dar la bienvenida al usuario
     * No tendrá ni param ni return
     */
    public void loadData(){
        System.out.println("Welcome to Simple LSRPG");
        System.out.println("\n\nLoading data...");
    }

    /**
     * Esta función servirá para enseñar el menú al usuario
     * No tendrá ni param ni return
     */
    public void showMainMenu(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("“Welcome adventurer! How can I help you?”");
        System.out.println("\n\t1) Character creation");
        System.out.println("\t2) List characters");
        System.out.println("\t3) Create an adventure");
        System.out.println("\t4) Start an adventure");
        System.out.println("\t5) Exit");
    }

    /**
     * Esta función servirá para enseñar el menú a un usuario que
     * aún no ha creado 3 personajes como mínimo
     * No tendrá ni param ni return
     */
    public void showMainMenuDissabled(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("“Welcome adventurer! How can I help you?”");
        System.out.println("\n\t1) Character creation");
        System.out.println("\t2) List characters");
        System.out.println("\t3) Create an adventure");
        System.out.println("\t4) Start an adventure (disabled: create 3 characters first)");
        System.out.println("\t5) Exit");
    }

    /**
     * Esta función servirá para enseñar el menú de aventuras al usuario
     * No tendrá ni param ni return
     */
    public void showAdventureMenu(){
        System.out.println("\n1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue");
    }

    /**
     * Esta función servirá para enseñar cualquier mensaje al usuario
     *
     * @param message, mensaje en cuestión que se enseñará
     */
    public void showMessage(String message){
        System.out.println(message);
    }

    /**
     * Esta función servirá para enseñar la lista con todos los items al usuario
     *
     * @param items, LinkedList con todos los items
     */
    public void showList(LinkedList<String> items){
        for (String item : items) {
            System.out.println("\t* " + item);
        }
    }

    /**
     * Esta función servirá para pedirle al usuario que
     * escriba un Integer, cubirendo el posible error
     *
     * @param message, mensaje con el que se pedirá el Integer
     * @return int con el Integer que se pida
     */
    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nThis isn't an integer!\n");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Esta función servirá para pedirle al usuario que
     * escriba un String, cubirendo el posible error
     *
     * @param message, mensaje con el que se pedirá el String
     * @return String con lo que se pida
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}
