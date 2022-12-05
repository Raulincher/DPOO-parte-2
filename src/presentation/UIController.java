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

            option = uiManager.askForInteger("\nYour answer: ");
            executeOption(option);
        } while(option != 5);
    }


    private void executeOption(int option) {
        uiManager.showMessage("");
        switch (option) {
            case 1:
                characterCreation();
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
                uiManager.showMessage("\nTavern keeper: “Are you leaving already? See you soon, adventurer.”\n");
                break;
            default:
                uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repit it to me, please?”");
                uiManager.showMessage("\nValid options are between 1 to 5 (including them)”");
                break;
        }
    }



    private void characterCreation(){
        int error = 0;
        String CharacterName = "NoCharacterName";
        String PlayerName = "NoPlayerName";
        int CharacterLevel = 0;
        uiManager.showMessage("Tavern keeper: “Oh, so you are new to this land.”\n" + "“What’s your name?”\n");
        while(error == 0) {
            CharacterName = uiManager.askForString("-> Enter your name: ");
            if(CharacterName.matches(".*\\d.*")){
                System.out.println("\nTavern keeper: “C'mon don't fool around and tell me your real name, will ya?”");
                System.out.println("Character name can't include numbers or special characters\n");
            }else{
                error = 1;
            }
        }

        uiManager.showMessage("\nTavern keeper: “Hello, " +  CharacterName + ", be welcome.”\n" + "“And now, if I may break the fourth wall, who is your Player?”\n");

        PlayerName = uiManager.askForString("-> Enter the player’s name: ");

        uiManager.showMessage("\nTavern keeper: “I see, I see...”\n" + "“Now, are you an experienced adventurer?”\n");

        error = 0;
        while(error == 0) {
            CharacterLevel = uiManager.askForInteger("-> Enter the character’s level [1..10]: ");
            if(CharacterLevel > 10 || CharacterLevel < 1){
                System.out.println("\nTavern keeper: “I don't think you could be at that level, c'mon tell me the truth”");
                System.out.println("Character level can't be lower than 1 or higher than 10\n");
            }
            else {
                error = 1;
            }
        }
        uiManager.showMessage("\nTavern keeper: “Oh, so you are level "+ CharacterLevel + "!”\n" + "“Great, let me get a closer look at you...”\n");
        uiManager.showMessage("Generating your stats...\n");

        int[] body = characterManager.diceRoll();
        int[] mind = characterManager.diceRoll();
        int[] spirit = characterManager.diceRoll();

        uiManager.showMessage("Body: You rolled " + (body[0] + body[1]) + " (" + body[0] + " and "+ body[1]+ ").");
        uiManager.showMessage("Body: You rolled " + (mind[0] + mind[1]) + " (" + mind[0] + " and " + mind[1] + ").");
        uiManager.showMessage("Body: You rolled " + (spirit[0] + spirit[1]) + " (" + spirit[0] + " and "+ spirit[1] + ").");

        uiManager.showMessage("\nYour stats are:");
        String stat1 = characterManager.statCalculator(body);
        uiManager.showMessage("\t- Body: "+ stat1);
        String stat2 = characterManager.statCalculator(mind);
        uiManager.showMessage("\t- Mind: "+ stat2);
        String stat3 = characterManager.statCalculator(spirit);
        uiManager.showMessage("\t- Spirit: "+ stat3);

        uiManager.showMessage("\nThe new character " + CharacterName + " has been created.\n");

        //characterManager.CreateCharacter(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
    }
}
