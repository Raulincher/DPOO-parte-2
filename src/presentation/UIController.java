package presentation;

import business.AdventureManager;
import business.CharacterManager;

public class UIController {
    private UIManager uiManager;
    private AdventureManager adventureManager;
    private CharacterManager characterManager;

    public UIController(UIManager uiManager, AdventureManager adventureManager, CharacterManager characterManager) {
        this.uiManager = uiManager;
        this.adventureManager = adventureManager;
        this.characterManager = characterManager;
    }
    public void run() {
        int option;
        do {
            uiManager.showMainMenu();

            option = uiManager.askForInteger("Enter an option: ");
            executeOption(option);
        } while(option != 6);
    }


    private void executeOption(int option) {
        uiManager.showMessage("");
        switch (option) {
            case 1:
                //createTask();
                break;
            case 2:
                //showAllTasks();
                break;
            case 3:
                //showCompletedTasks();
                break;
            case 4:
                //showPendingTasks();
                break;
            case 5:
                //completeTask();
                break;
            default:
                uiManager.showMessage("Incorrect option");
                break;
        }
    }
}
