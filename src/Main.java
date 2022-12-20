import business.AdventureManager;
import business.CharacterManager;
import business.MonsterManager;
import persistance.AdventureDAO;
import persistance.CharacterDAO;
import persistance.MonsterDAO;
import presentation.UIController;
import presentation.UIManager;
import java.io.File;

public class Main {
    public static void main(String[] args){
        File characterJSON = new File("files/characters.json");
        File monstersJSON = new File("monster.json");


        //persistance
        MonsterDAO monsterDAO = new MonsterDAO();
        CharacterDAO characterDAO = new CharacterDAO();
        AdventureDAO adventureDAO = new AdventureDAO();


        //Business
        CharacterManager characterManager = new CharacterManager(characterDAO);
        AdventureManager adventureManager = new AdventureManager(adventureDAO, characterManager);
        MonsterManager monsterManager = new MonsterManager(monsterDAO);

        //presentation
        UIManager uiManager = new UIManager();
        UIController uiController = new UIController(uiManager, adventureManager, characterManager, monsterManager);

        uiController.run();

    }

}
