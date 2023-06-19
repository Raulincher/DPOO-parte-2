import business.AdventureManager;
import business.CharacterManager;
import business.MonsterManager;
import persistance.*;
import presentation.UIController;
import presentation.UIManager;
import java.io.File;
import java.io.IOException;
/**
 * Clase Main la cual arrancará el programa
 */
public class Main {

    /**
     * Función padre para hacer funcionar el programa
     */
    public static void main(String[] args) throws IOException {

        // Indicamos los JSON
        File characterJSON = new File("files/characters.json");
        File monstersJSON = new File("monster.json");


        // Persistance
        MonsterDAO monsterDAO = new MonsterDAO();
        CharacterDAO characterDAO = new CharacterDAO();
        AdventureDAO adventureDAO = new AdventureDAO();

        // Business
        CharacterManager characterManager = new CharacterManager(characterDAO);
        AdventureManager adventureManager = new AdventureManager(adventureDAO, characterManager);
        MonsterManager monsterManager = new MonsterManager(monsterDAO);

        // Presentation
        UIManager uiManager = new UIManager();
        UIController uiController = new UIController(uiManager, adventureManager, characterManager, monsterManager);

        uiController.run();

    }

}
