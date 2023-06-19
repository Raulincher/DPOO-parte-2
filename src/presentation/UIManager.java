package presentation;

import java.util.InputMismatchException;
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
     * Esta función servirá para enseñar el menú al usuario
     * No tendrá ni param ni return
     */
    public void showMainMenu(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("Welcome adventurer! How can I help you?");
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
        System.out.println("Welcome adventurer! How can I help you?");
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
     * Esta función servirá para mostrar la fase de descanso después del combate
     * No tendrá ni param ni return
     */
    public void showRestStage(){
        System.out.println("All enemies are defeated.");
        System.out.println("--------------------");
        System.out.println("*** Short rest stage ***");
        System.out.println("--------------------");
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

    /**
     * Esta función servirá para mostrar el mensaje correspondiente
     * una vez el personaje ataque
     *
     * @param actualName, nombre del personaje
     * @param attackedMonster, nombre del monstruo que ataca
     */
    public void messageAttack(String actualName, String attackedMonster){
        String message = "\n" + actualName + " attacks " + attackedMonster + " with Sword slash.";

        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar que un jugador ha caído inconsciente
     * No tendrá ni param ni return
     */
    public void deadMessage(String name){
        showMessage(name + " falls unconscious.");
    }

    /**
     * Esta función servirá para mostrar el hit y el dañó provocado,
     * teniendo en cuenta si es crítico o si se falla
     *
     * @param damage, daño provocado
     * @param isCrit, int para saber si el hit será crítico
     */
    public void hitMessage(int damage, int isCrit){
        String message;

        // Abrimos if en caso que el golpe sea crítico
        if(isCrit == 2){
            message = "Critical hit and deals " + (damage * 2) + " damage.";
        }
        // Abrimos elseif en caso que el golpe sea normal
        else if(isCrit == 1){
            message = "Hit and deals " + damage + " damage.";
        }
        // Cerramos conn un else en caso que se falle el golpe
        else{
            message = "Fails and deals 0 damage.";
        }

        showMessage(message);
    }

    /**
     * Esta función servirá para mostrar que un personaje usa su habilidad en la fase de
     * descanso
     *
     * @param characterName, nombre del personaje
     * @param characterCuration, personaje que es curado
     * @param temporalLife, vida temporal para asegurar que no está muerto
     */
    public void showAbilitiesRestPhase(String characterName, int characterCuration, int temporalLife) {

        String message;

        // En caso que el personaje no esté inconsciente
        if (temporalLife != 0) {
            message = characterName + " uses BandageTime. Heals " + characterCuration + " hit points";
        }
        // En caso que sí esté inconsciente
        else {
            message = characterName + " is unconscious";
        }
        showMessage(message);
    }
}
