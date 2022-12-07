package presentation;

import business.AdventureManager;
import business.CharacterManager;
import business.MonsterManager;
import business.entities.Character;
import business.entities.Monster;

import java.util.Arrays;
import java.util.Objects;

public class UIController {
    private final UIManager uiManager;
    private final AdventureManager adventureManager;
    private final CharacterManager characterManager;
    private final MonsterManager monsterManager;

    public UIController(UIManager uiManager, AdventureManager adventureManager, CharacterManager characterManager, MonsterManager monsterManager) {
        this.uiManager = uiManager;
        this.adventureManager = adventureManager;
        this.characterManager = characterManager;
        this.monsterManager = monsterManager;
    }


    public void run() {
        int option;
        int i = 0;
        int totalCharacters = 0;

        do {
            Character[] characters = characterManager.getAllCharacters();
            for (Character character: characters) {
                i++;
                totalCharacters = i;
            }
            if(totalCharacters < 3){
                uiManager.showMainMenuDissabled();
                option = uiManager.askForInteger("\nYour answer: ");
                if(option != 4){
                    executeOption(option);
                }
            }else{
                uiManager.showMainMenu();
                option = uiManager.askForInteger("\nYour answer: ");
                executeOption(option);
            }
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
                adventureCreation();
                break;
            case 4:
                adventurePlay();
                break;
            case 5:
                uiManager.showMessage("\nTavern keeper: “Are you leaving already? See you soon, adventurer.”\n");
                break;
            default:
                uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repeat it to me, please?”");
                uiManager.showMessage("\nValid options are between 1 to 5 (including them)”");
                break;
        }
    }

    private void characterCreation(){
        int error = 0;
        String characterName = "NoCharacterName";
        String playerName = "NoPlayerName";
        int characterLevel = 0;
        uiManager.showMessage("""
                Tavern keeper: “Oh, so you are new to this land.”
                “What’s your name?”
                """);
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

        uiManager.showMessage("""

                Tavern keeper: “I see, I see...”
                “Now, are you an experienced adventurer?”
                """);

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

        int[] body = characterManager.diceRoll2D6();
        int[] mind = characterManager.diceRoll2D6();
        int[] spirit = characterManager.diceRoll2D6();

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
        int i = 0;

        uiManager.showMessage("Tavern keeper: “Lads! The Boss wants to see you, come here!”\n" + "“Who piques your interest?”");
        String playerName = uiManager.askForString("-> Enter the name of the Player to filter: \n");

        Character[] character = characterManager.filteredPlayers(playerName);
        if(character != null){
            uiManager.showMessage("You watch as some adventurers get up from their chairs and approach you.\n");
            while(i < character.length ){
                uiManager.showMessage("\t" + (i+1) +"." + character[i].getCharacterName());
                i++;
            }
            uiManager.showMessage("\t\n0. Back");

            int characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.length + "]: ");
            if(characterPicked != 0){
                Character characterChosen = character[characterPicked - 1];

                int bodyChoseed = characterChosen.getBody();
                String bodyIntToString;

                if(bodyChoseed >=  0){
                    bodyIntToString = "+" + bodyChoseed;
                }else{
                    bodyIntToString = String.valueOf(bodyChoseed);
                }

                int mindChoseed = characterChosen.getMind();
                String mindIntToString;

                if(mindChoseed >=  0){
                    mindIntToString = "+" + mindChoseed;
                }else{
                    mindIntToString = String.valueOf(mindChoseed);
                }

                int spiritChoseed = characterChosen.getSpirit();
                String spiritIntToString;

                if(spiritChoseed >=  0){
                    spiritIntToString = "+" + spiritChoseed;
                }else{
                    spiritIntToString = String.valueOf(spiritChoseed);
                }

                uiManager.showMessage("Tavern keeper: “Hey" + characterChosen.getCharacterName()  + " get here; the boss wants to see you!”\n");
                uiManager.showMessage("* " + "Name:   " + characterChosen.getCharacterName());
                uiManager.showMessage("* " + "Player: " + characterChosen.getPlayerName());
                uiManager.showMessage("* " + "Class:  " + characterChosen.getCharacterClass());
                uiManager.showMessage("* " + "XP:     " + characterChosen.getCharacterLevel());
                uiManager.showMessage("* " + "Body:   " + bodyIntToString);
                uiManager.showMessage("* " + "Mind:   " + mindIntToString);
                uiManager.showMessage("* " + "Spirit: " + spiritIntToString);

                uiManager.showMessage("[Enter name to delete, or press enter to cancel]\n");
                String characterDelete = uiManager.askForString("Do you want to delete " + characterChosen.getCharacterName() + " ? ");

                if(!Objects.equals(characterDelete, "")){
                    uiManager.showMessage("Tavern keeper: “I’m sorry kiddo, but you have to leave.”\n");
                    uiManager.showMessage("Character " + characterChosen.getCharacterName() + " left the Guild.\n");

                    characterManager.deleteCharacter(characterChosen.getCharacterName());
                }

            }else{
                uiManager.showMessage("Tavern keeper: “Don't worry mate you don't need to decide now. Come back when you decided who you want to meet”\n");
            }
        }else{
            uiManager.showMessage("Tavern keeper: “That player has never created a character. Come back later”\n");
        }
    }

    public void adventureCreation(){
        uiManager.showMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
        String adventureName = uiManager.askForString("-> Name your adventure: ");

        uiManager.showMessage("\nTavern keeper: “You plan to undertake " + adventureName + " , really?”\n" + "“How long will that take?”\n");

        int error = 0;
        int adventureEncounters = 0;

        while(error == 0) {
            adventureEncounters = uiManager.askForInteger("-> How many encounters do you want [1..4]: ");
            if(adventureEncounters > 4 || adventureEncounters < 1){
                System.out.println("\nTavern keeper: “That number of encounters is impossible to be truth, ya fooling around with me aren't ya”");
                System.out.println("Number of encounters should be between 1 and 4\n");
            }
            else {
                error = 1;
            }
        }

        uiManager.showMessage("\nTavern keeper: “"+ adventureEncounters +" encounters? That is too much for me...”");

        int auxEncounter = 0;
        while(auxEncounter < adventureEncounters){
            int option;
            int monsterQuantity = 0;
            int i = 0;
            do {
                uiManager.showMessage("\n\n* Encounter " + (auxEncounter + 1) + " / " + adventureEncounters + "");
                uiManager.showMessage("* Monsters in encounter");

                Monster[] adventureMonsters = adventureManager.getAdventureMonsters(adventureName);

                if(adventureMonsters != null){
                    for (Monster monster: adventureMonsters) {
                        uiManager.showMessage((i+1) + monster.getMonsterName() + adventureManager.countMonsters(monster.getMonsterName()));
                        i++;
                    }
                }else{
                    uiManager.showMessage("  # Empty");
                }
                uiManager.showAdventureMenu();
                option = uiManager.askForInteger("\n-> Enter an option [1..3]: ");

                if(option == 2 && adventureMonsters == null){
                    uiManager.showMessage("\nTavern keeper: “Sorry pal you can't erase monsters if your adventure don't have any, i'll let you add some first”");
                    uiManager.showMessage("The tavern keeper shows you the add monster menu\n");
                    option = 1;
                }

                switch (option) {
                    case 1 -> {
                        Monster[] monsters = monsterManager.getAllMonsters();
                        i = 0;
                        int totalMonsters = 0;
                        for (Monster monster : monsters) {
                            uiManager.showMessage((i + 1) + ". " + monster.getMonsterName() + " (" +monster.getMonsterChallenge()+ ")");
                            i++;
                            totalMonsters = i;
                        }
                        int monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + totalMonsters + "]: ");
                        monsterQuantity = uiManager.askForInteger("-> How many " + monsters[monsterOption - 1].getMonsterName() + " do you want to add: ");
                    }

                    //adventureManager.addMonster(Monster monster, int monsterQuantity);
                    case 2 -> {
                        int monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");

                        //metodo para que con la posicion del monstruo en la aventura pillemos el nombre y la cantidad

                        uiManager.showMessage(monsterQuantity + /*monsterName*/ monsterQuantity + " were removed from the encounter.");
                    }

                    //adventureManager.removeMonster();
                    case 3 -> auxEncounter++;
                    default -> {
                        uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repeat it to me, please?”");
                        uiManager.showMessage("\nValid options are between 1 to 3 (including them)”");
                    }
                }
            }while(option != 3);

            //CreateAdventure(adventureName, adventureEncounters, );

        }
    }


    public void adventurePlay(){
        int characterQuantity = 0;
        int adventureSelection = 0;
        double average = 0;
        int[] totalPlayersLife = {0};
        int[] playersLife = null;

        int numOfMonsters = 3; //get monsters in encounter
        int monstersDefeat = 0; //number of monsters encounter defeat
        int charactersDefeat = 0; //number of characters defeat
        uiManager.showMessage("""
                Tavern keeper: “So, you are looking to go on an adventure?”
                “Where do you fancy going?”
                """);
        uiManager.showMessage("Available adventures:\n");
        //listar aventuras en el JSON
        //adventureManager.getAdventures();
        adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");


        uiManager.showMessage("""
                Tavern keeper: “adventureName”
                “And how many people shall join you?”
                """);
        characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");


        uiManager.showMessage("Tavern keeper: “Great, " + characterQuantity + " it is.”\n" + "“Who among these lads shall join you?”");
        int i = 0, j = 0;
        Character[] characterInParty = null;
        String characterName = null;
        String[] characterNamesList = null;

        //creation of the adventure party
        do{
            i = 0;
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Your party (" + j + " / "+ characterQuantity +"):\n");

            while(i < characterQuantity){
                //characterName = characterInParty[i].getCharacterName();
                if(characterName == null){
                    uiManager.showMessage((i+1) + ". Empty");

                }else{
                    uiManager.showMessage((i+1) + "." + characterName);

                }
                i++;
            }
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Available characters:");
            i = 0;
            Character[] characters = characterManager.getAllCharacters();
            /*for (Character character: characters) {
                uiManager.showMessage((i+1) + "." + character.getCharacterName());
                characterNamesList[i] = character.getCharacterName();
                i++;
            }*/
            int CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");

            //characterInParty[j] = characterManager.filteredCharacter(/*characterNamesList[CharacterPartySelected]*/"pepe");
            j++;
        }while(j < characterQuantity);


        uiManager.showMessage("Tavern keeper: “Great, good luck on your adventure lads!”\n");
        uiManager.showMessage("The “" + adventureSelection  +"” will start soon...\n");
        j = 0;
        int adventureEncounters = 0;

        //Combat phases

        do{
            uiManager.showMessage("---------------------");
            uiManager.showMessage("Starting Encounter 1:");
            //get monsters from this adventure and encounter number
            uiManager.showMessage("---------------------");

            //preparation phase
            uiManager.showMessage("-------------------------");
            uiManager.showMessage("*** Preparation stage ***");
            //get monsters from this battle and encounter number
            uiManager.showMessage("-------------------------\n");
            i = 0;
            while(i < characterQuantity){
                //get characterName from party
                uiManager.showMessage("characterName uses Self-Motivated. Their Spirit increases in +1");
                //update character spirit +1
                i++;
            }

            uiManager.showMessage("Rolling initiative...\n");

            i = 0;
            int monsterQuantity = 0;
            int[] initiative = null;
            while(i < characterQuantity){
                //initiative[i] = characterManager.initiativeCalculator(/*characterNamesList[i]*/ "pepe");
                i++;
            }

            //get monsters initiative

            //make a list with initiatives ordered


            //Combat phase
            uiManager.showMessage("--------------------");
            uiManager.showMessage("*** Combat stage ***");
            uiManager.showMessage("--------------------");

            i = 0;
            do{
                uiManager.showMessage("Round "+  i + ":");
                uiManager.showMessage("Party :");
                int z = 0;

                //showing characters lifes
                if(playersLife == null){
                    while(z < characterQuantity){
                        characterNamesList = new String[]{"Hello", "World", "dhdhdj"};
                        playersLife = new int[]{0, 34, 56};
                        totalPlayersLife = new int[]{0, 0, 0};
                        //if characters life equals null it means no initial life is set
                        //playersLife[z] =  characterManager.initialLifeCalculator(characterNamesList[z]);
                        uiManager.showMessage("\t- "+ characterNamesList[z] + "\t"+ playersLife[z] + " / " + playersLife[z] + " hit points");
                        totalPlayersLife[z] = playersLife[z];
                        z++;
                    }
                }else{
                    //if it isn't null it means we only need to show characters life
                    while(z < characterQuantity) {
                        uiManager.showMessage("\t- "+ characterNamesList[z] + "\t"+ playersLife[z] + " / " + totalPlayersLife[z] + " hit points");
                        z++;
                    }

                }
                int q = 0;
                int sum = 0;

                //battling
                while(q < characterQuantity + numOfMonsters){

                    //int damage = characterManager.damageCalculator("pepe"/*Aqui ira la lista conjunta de velocidades para decidir quien ataca primero*/);

                    for (z = 0; i < characterQuantity; i++) {
                        sum += playersLife[i];
                    }
                    average = sum / characterQuantity;
                    if (average == 0){
                        q = characterQuantity + numOfMonsters;
                    }else{
                        q++;
                    }
                }

                if(average != 0){
                    uiManager.showMessage("End of round "+ i +".\n");
                    i++;
                }else{
                    charactersDefeat = characterQuantity;
                }

            }while(monstersDefeat == numOfMonsters || charactersDefeat == characterQuantity);

            if(average != 0){
                uiManager.showMessage("All enemies are defeated.");
                uiManager.showMessage("--------------------");
                uiManager.showMessage("*** Short rest stage ***");
                uiManager.showMessage("--------------------");
                i = 0;
                boolean levelUp = false;
                while(i < characterQuantity){
                    if(levelUp){
                        uiManager.showMessage("characterName gains sumallmonstersexperience xp. characterName levels up. They are now lvl 5!");

                    }else{
                        uiManager.showMessage("characterName gains sumallmonstersexperience xp");
                    }

                    i++;

                }
                i = 0;
                while(i < characterQuantity){

                    if(playersLife[i] != 0) {
                        //int healing = characterManager.BandageTime(characterNamesList[i]);
                        uiManager.showMessage("characterName uses BandageTime. Heals " + /*healing*/ "healing number" + " hit points");
                    }else{
                        uiManager.showMessage("characterName is unconscious");
                    }

                    i++;
                }
            }else{

                j = 6;
            }
            j++;
        }while(j < adventureEncounters);

        if(average == 0) {
            uiManager.showMessage("""
                    Tavern keeper: “Lad, wake up. Yes, your party fell unconscious.”
                    “Don’t worry, you are safe back at the Tavern.”
                    """);
        }else{
            uiManager.showMessage("Congratulations, your party completed “adventureName”\n");
        }

    }
}
