package presentation;

import business.AdventureManager;
import business.CharacterManager;
import business.entities.Character;

import java.util.Arrays;

public class UIController {
    private final UIManager uiManager;
    private final AdventureManager adventureManager;
    private final CharacterManager characterManager;

    public UIController(UIManager uiManager, AdventureManager adventureManager, CharacterManager characterManager) {
        this.uiManager = uiManager;
        this.adventureManager = adventureManager;
        this.characterManager = characterManager;
    }


    public void run() {
        int option;
        //Character[] character = characterManager.getAllCharacters();
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
                listCharacters();
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
        String characterName = "NoCharacterName";
        String playerName = "NoPlayerName";
        int characterLevel = 0;
        uiManager.showMessage("Tavern keeper: “Oh, so you are new to this land.”\n" + "“What’s your name?”\n");
        while(error == 0) {
            characterName = uiManager.askForString("-> Enter your name: ");
            if(characterName.matches(".*\\d.*")){
                System.out.println("\nTavern keeper: “C'mon don't fool around and tell me your real name, will ya?”");
                System.out.println("Character name can't include numbers or special characters\n");
            }else{
                error = 1;
            }
        }

        uiManager.showMessage("\nTavern keeper: “Hello, " +  characterName + ", be welcome.”\n" + "“And now, if I may break the fourth wall, who is your Player?”\n");

        playerName = uiManager.askForString("-> Enter the player’s name: ");

        uiManager.showMessage("\nTavern keeper: “I see, I see...”\n" + "“Now, are you an experienced adventurer?”\n");

        error = 0;
        while(error == 0) {
            characterLevel = uiManager.askForInteger("-> Enter the character’s level [1..10]: ");
            if(characterLevel > 10 || characterLevel < 1){
                System.out.println("\nTavern keeper: “I don't think you could be at that level, c'mon tell me the truth”");
                System.out.println("Character level can't be lower than 1 or higher than 10\n");
            }
            else {
                error = 1;
            }
        }

        int experience = characterManager.experienceCalculator(characterLevel);

        uiManager.showMessage("\nTavern keeper: “Oh, so you are level "+ characterLevel + "!”\n" + "“Great, let me get a closer look at you...”\n");
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

        int bodySum = Integer.parseInt(stat1);
        int mindSum = Integer.parseInt(stat2);
        int spiritSum = Integer.parseInt(stat3);

        uiManager.showMessage("\nThe new character " + characterName + " has been created.\n");
        String characterClass = "Aventurer";

        characterManager.createCharacter(characterName, playerName, experience, bodySum, mindSum, spiritSum, characterClass);
    }


    private void listCharacters(){
        uiManager.showMessage("Tavern keeper: “Lads! The Boss wants to see you, come here!”\n" + "“Who piques your interest?”");
        String playerName = uiManager.askForString("-> Enter the name of the Player to filter: \n");

        Character[] character = characterManager.filteredPlayers(playerName);
        if(character != null){
            uiManager.showMessage("You watch as some adventurers get up from their chairs and approach you.\n");
            uiManager.showMessage("\t" + Arrays.toString(character));
            uiManager.showMessage("\t\n0. Back");

            int characterPicked = uiManager.askForInteger("Who would you like to meet [0..5]: ");
            if(characterPicked != 0){
                Character characterChoosen = character[characterPicked];
                uiManager.showMessage("Tavern keeper: “Hey" + characterChoosen  + " get here; the boss wants to see you!”\n");
                uiManager.showMessage("\t" + characterChoosen.toString());

                uiManager.showMessage("[Enter name to delete, or press enter to cancel]\n");
                String characterDelete = uiManager.askForString("Do you want to delete Jinx? ");

                if(characterDelete != null){
                    uiManager.showMessage("Tavern keeper: “I’m sorry kiddo, but you have to leave.”\n");
                    uiManager.showMessage("Character" + characterChoosen + " left the Guild.\n");

                    characterManager.deleteCharacter("nombre");
                }

            }else{
                uiManager.showMessage("Tavern keeper: “Don't worry mate you don't need to decide now. Come back when you decided who you want to meet”\n");
            }
        }else{
            uiManager.showMessage("Tavern keeper: “That player has never created a character. Come back later”\n");
        }
    }
}
