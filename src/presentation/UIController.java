package presentation;

import business.AdventureManager;
import business.CharacterManager;
import business.MonsterManager;
import business.entities.Adventure;
import business.entities.Character;
import business.entities.Monster;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        int totalAdventures = 0;
        boolean validationMonster = true;


        uiManager.showMessage("Welcome to Simple LSRPG.\n");
        uiManager.showMessage("\nLoading data...");
        ArrayList<Monster> monsters = monsterManager.getAllMonsters();

        if(monsters.size() > 0){

            uiManager.showMessage("Data was successfully loaded.\n\n\n");

            do {
                ArrayList<Character> characters = characterManager.getAllCharacters();
                for (Character character: characters) {
                    i++;
                    totalCharacters = i;
                }
                i = 0;
                ArrayList<Adventure> adventures = adventureManager.getAdventuresList();
                for (Adventure adventure: adventures) {
                    i++;
                    totalAdventures = i;
                }
                if(totalCharacters < 3){
                    uiManager.showMainMenuDissabled();
                    option = uiManager.askForInteger("\nYour answer: ");
                    if(option != 4){
                        executeOption(option);
                    }
                }else if(totalAdventures == 0){
                    uiManager.showMainMenu();
                    option = uiManager.askForInteger("\nYour answer: ");
                    if(option != 4){
                        executeOption(option);
                    }else{
                        uiManager.showMessage("\nYou need to create an adventure before playing one.\n");
                    }
                }else{
                    uiManager.showMainMenu();
                    option = uiManager.askForInteger("\nYour answer: ");
                    executeOption(option);
                }
            } while(option != 5);
        }else{
            uiManager.showMessage("Error: The monsters.json file can’t be accessed.\n");
        }

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
        boolean exist = false;
        uiManager.showMessage("""
                Tavern keeper: “Oh, so you are new to this land.”
                “What’s your name?”
                """);
        while(error == 0 ) {
            characterName = uiManager.askForString("-> Enter your name: ");

            boolean correct = characterManager.nameCheck(characterName);
            characterName = characterManager.fixName(characterName);
            if(!correct){
                uiManager.showMessage("\nTavern keeper: “C'mon don't fool around and tell me your real name, will ya?”");
                uiManager.showMessage("Character name can't include numbers or special characters\n");
            }else{
                exist = characterManager.characterNameDisponibility(characterName);
                if(exist){
                    uiManager.showMessage("\nTavern keeper: “Sorry lad this character name already exists”\n");
                }else{
                    error = 1;
                }
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
                uiManager.showMessage("\nTavern keeper: “I don't think you could be at that level, c'mon tell me the truth”");
                uiManager.showMessage("Character level can't be lower than 1 or higher than 10\n");
            }
            else {
                error = 1;
            }
        }

        int experience = characterManager.experienceCalculator(characterLevel);

        uiManager.showMessage("\nTavern keeper: “Oh, so you are level "+ characterLevel + "!”\n" + "“Great, let me get a closer look at you...”\n");
        uiManager.showMessage("Generating your stats...\n");

        //mind, body, spirit

        int[] body = characterManager.diceRoll2D6();
        int[] mind = characterManager.diceRoll2D6();
        int[] spirit = characterManager.diceRoll2D6();

        uiManager.showMessage("Body: You rolled " + (body[0] + body[1]) + " (" + body[0] + " and "+ body[1]+ ").");
        uiManager.showMessage("Mind: You rolled " + (mind[0] + mind[1]) + " (" + mind[0] + " and " + mind[1] + ").");
        uiManager.showMessage("Spirit: You rolled " + (spirit[0] + spirit[1]) + " (" + spirit[0] + " and "+ spirit[1] + ").");

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

        //class
        //String characterClass = "Adventurer";

        boolean saved = characterManager.createCharacter(characterName, playerName, experience, bodySum, mindSum, spiritSum);


        if (saved){
            uiManager.showMessage("\nThe new character " + characterName + " has been created.\n");
        }else{
            uiManager.showMessage("\nTavern keeper: “Im sorry friend but " + characterName +  " couldn't be found on the guild. Try it again next time.");
            uiManager.showMessage("There is an error in the save of your character.");
        }
    }


    private void listCharacters(){
        int i = 0;

        uiManager.showMessage("Tavern keeper: “Lads! The Boss wants to see you, come here!”\n" + "“Who piques your interest?”");
        String playerName = uiManager.askForString("-> Enter the name of the Player to filter: ");

        ArrayList<Character> character = characterManager.filteredPlayers(playerName);
        if(character.get(0) != null){
            uiManager.showMessage("You watch as some adventurers get up from their chairs and approach you.\n");
            while(i < character.size() ){
                uiManager.showMessage("\t" + (i+1) +"." + character.get(i).getCharacterName());
                i++;
            }
            uiManager.showMessage("\t\n0. Back");

            int characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
            if((characterPicked) > character.size() || (characterPicked) < 0) {
                while ((characterPicked) > character.size() || (characterPicked) < 1) {
                    uiManager.showMessage("Tavern keeper: “Please choose an existing character”\n");
                    characterPicked = uiManager.askForInteger("Who would you like to meet [0.." + character.size() + "]: ");
                }
            }

            if(characterPicked != 0){
                Character characterChosen = character.get(characterPicked - 1);

                int bodyChosen = characterChosen.getBody();
                String bodyIntToString;

                if(bodyChosen >=  0){
                    bodyIntToString = "+" + bodyChosen;
                }else{
                    bodyIntToString = String.valueOf(bodyChosen);
                }

                int mindChosen = characterChosen.getMind();
                String mindIntToString;

                if(mindChosen >=  0){
                    mindIntToString = "+" + mindChosen;
                }else{
                    mindIntToString = String.valueOf(mindChosen);
                }

                int spiritChosen = characterChosen.getSpirit();
                String spiritIntToString;

                if(spiritChosen >=  0){
                    spiritIntToString = "+" + spiritChosen;
                }else{
                    spiritIntToString = String.valueOf(spiritChosen);
                }

                uiManager.showMessage("Tavern keeper: “Hey" + characterChosen.getCharacterName()  + " get here; the boss wants to see you!”\n");
                uiManager.showMessage("* " + "Name:   " + characterChosen.getCharacterName());
                uiManager.showMessage("* " + "Player: " + characterChosen.getPlayerName());
                uiManager.showMessage("* " + "Class:  Adventurer" );
                uiManager.showMessage("* " + "level:  " + characterManager.revertXpToLevel(characterChosen.getCharacterLevel()));
                uiManager.showMessage("* " + "XP:     " + characterChosen.getCharacterLevel());
                uiManager.showMessage("* " + "Body:   " + bodyIntToString);
                uiManager.showMessage("* " + "Mind:   " + mindIntToString);
                uiManager.showMessage("* " + "Spirit: " + spiritIntToString);

                uiManager.showMessage("[Enter name to delete, or press enter to cancel]\n");
                String characterDelete = uiManager.askForString("Do you want to delete " + characterChosen.getCharacterName() + " ? ");

                boolean erased = characterManager.deleteCharacter(characterDelete);
                if(erased){
                    uiManager.showMessage("Tavern keeper: “I’m sorry kiddo, but you have to leave.”\n");
                    uiManager.showMessage("Character " + characterChosen.getCharacterName() + " left the Guild.\n");
                }

            }else{
                uiManager.showMessage("Tavern keeper: “Don't worry mate you don't need to decide now. Come back when you decided who you want to meet”\n");
            }
        }else{
            uiManager.showMessage("Tavern keeper: “That player has never created a character. Come back later”\n");
        }
    }

    public void adventureCreation(){

        int error = 0;
        int adventureEncounters = 0;
        int lastQuantity = 0;
        int monsterQuantity = 0;
        int auxEncounter = 0;
        int option;
        int i;
        int totalMonsters = 0;
        int monsterOption;
        int monsterDeleteOption = 0;
        int j = 0;
        ArrayList<ArrayList<Monster>> encounterMonsters;
        ArrayList<String> monstersQuantityAndNames = new ArrayList<String>(1);

        uiManager.showMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
        String adventureName = uiManager.askForString("-> Name your adventure: ");

        uiManager.showMessage("\nTavern keeper: “You plan to undertake " + adventureName + " , really?”\n" + "“How long will that take?”\n");

        while(error == 0) {
            adventureEncounters = uiManager.askForInteger("-> How many encounters do you want [1..4]: ");
            if(adventureEncounters > 4 || adventureEncounters < 1){
                uiManager.showMessage("\nTavern keeper: “That number of encounters is impossible to be truth, ya fooling around with me aren't ya”");
                uiManager.showMessage("Number of encounters should be between 1 and 4\n");
            }
            else {
                error = 1;
            }
        }

        uiManager.showMessage("\nTavern keeper: “"+ adventureEncounters +" encounters? That is too much for me...”");

        encounterMonsters = new ArrayList<ArrayList<Monster>>(adventureEncounters);

        encounterMonsters = adventureManager.initializeEncounters(encounterMonsters, adventureEncounters);


        while(auxEncounter < adventureEncounters){
            do {
                uiManager.showMessage("\n\n* Encounter " + (auxEncounter + 1) + " / " + adventureEncounters + "");
                uiManager.showMessage("* Monsters in encounter");
                i = 0;
                boolean exist = false;

                if(encounterMonsters.get(auxEncounter).get(0) != null){
                    for (i = 0; i < monstersQuantityAndNames.size();i++) {
                        String auxName = monstersQuantityAndNames.get(i);
                        String[] auxNameSplit = auxName.split("\\d+");
                        uiManager.showMessage("\t" + (i+1) + " " + auxNameSplit[0] + " (" + auxName.replaceAll("[^0-9]", "") + ")");
                    }
                }else{
                    uiManager.showMessage("  # Empty");
                }
                uiManager.showAdventureMenu();
                option = uiManager.askForInteger("\n-> Enter an option [1..3]: ");

                if(option == 2 && encounterMonsters.get(auxEncounter).get(0) == null){
                    uiManager.showMessage("\nTavern keeper: “Sorry pal you can't erase monsters if your adventure don't have any, i'll let you add some first”");
                    uiManager.showMessage("The tavern keeper shows you the add monster menu\n");
                    option = 1;
                }

                switch (option) {
                    case 1 -> {
                        ArrayList<Monster> monsters = monsterManager.getAllMonsters();

                        i = 0;

                        for (Monster monster : monsters) {
                            uiManager.showMessage((i + 1) + ". " + monster.getMonsterName() + " (" +monster.getMonsterChallenge()+ ")");
                            i++;
                            totalMonsters = i;
                        }

                        monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + totalMonsters + "]: ");

                        if(monsterOption > monsters.size() || monsterOption < 1) {
                            while(monsterOption > monsters.size() || monsterOption < 1) {
                                uiManager.showMessage("Tavern keeper: “Please choose an existing monster”\n");
                                monsterOption = uiManager.askForInteger("-> Choose a monster to add [1.." + monsters.size() + "]: ");
                            }
                        }

                        lastQuantity = monsterQuantity + lastQuantity;
                        error = 1;
                        while(error == 1) {
                            monsterQuantity = uiManager.askForInteger("-> How many " + monsters.get(monsterOption - 1).getMonsterName() + " do you want to add: ");

                            if(monsterQuantity < 1){
                                uiManager.showMessage("\nTavern keeper: “Please add a correct number of monsters”");
                                uiManager.showMessage("Quantity must be greater than 0”\n");
                            }
                            else{
                                error = 0;
                            }
                        }

                        lastQuantity = adventureManager.capacityEnsurance(auxEncounter, lastQuantity, monsterQuantity, encounterMonsters);

                        if(encounterMonsters.get(auxEncounter) != null){
                            exist = adventureManager.checkMonsterTypeOfEncounter(encounterMonsters.get(auxEncounter), monsters, monsterOption);
                        }else{
                            exist = false;
                        }

                        if(exist){
                            uiManager.showMessage("\nTavern keeper: “You can't add more than 2 different type of boss in your encounter”");
                        }else {
                            adventureManager.setMonstersEncounter(monsters, encounterMonsters, monsterOption, lastQuantity, monsterQuantity, auxEncounter);
                            adventureManager.setMonstersNames(monstersQuantityAndNames, monsters, monsterQuantity, monsterOption);
                        }
                    }

                    case 2 -> {
                        monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");

                        if(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                            while(monsterDeleteOption > monstersQuantityAndNames.size() || monsterDeleteOption < 1) {
                                uiManager.showMessage("Tavern keeper: “Please choose an existing monster”\n");
                                monsterDeleteOption = uiManager.askForInteger("Which monster do you want to delete: ");
                            }
                        }

                        int removedCounter = 0;
                        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);

                        removedCounter = adventureManager.removeMonsterFromEncounter(encounterMonsters, monstersQuantityAndNames, monsterDeleteOption, lastQuantity, monsterQuantity, auxEncounter);

                        lastQuantity = lastQuantity - removedCounter;

                        uiManager.showMessage(removedCounter + " " + monsterToBeErased  + " were removed from the encounter.");
                    }
                    case 3 -> {

                        if(encounterMonsters.get(auxEncounter).get(0) == null){
                            uiManager.showMessage("\nYou can't create and encounter without monsters\n");
                        }else{
                            auxEncounter++;
                            monsterQuantity = 0;
                            lastQuantity = 0;
                            monstersQuantityAndNames = new ArrayList<String>(1);
                        }
                    }
                    default -> {
                        uiManager.showMessage("\nTavern keeper: “I can't understand you, could you repeat it to me, please?”");
                        uiManager.showMessage("\nValid options are between 1 to 3 (including them)”");
                    }
                }
            }while(option != 3);
        }

        boolean adventureSaved = adventureManager.createAdventure(adventureName, adventureEncounters, encounterMonsters);

        if(adventureSaved){
            uiManager.showMessage("\nTavern keeper: “Your adventure is ready whenever you want to play it”");
        }else{
            uiManager.showMessage("\nTavern keeper: “I don't know an adventure like that could be carry on, make sure to do it correctly”");
            uiManager.showMessage("\nSomething went wrong in the creation of your adventure");
        }
    }


    public void adventurePlay(){
        int characterQuantity = 0;
        int adventureSelection = 0;
        double average = 0;
        int[] playersLife = {0};
        int[] totalPlayersLife = {0};
        int defeated = 0;
        int[] saveNumber = new int[5];
        int counterEncounters = 0;

        int monstersDefeat = 0; //number of monsters encounter defeat
        int charactersDefeat = 0; //number of characters defeat
        uiManager.showMessage("""
                Tavern keeper: “So, you are looking to go on an adventure?”
                “Where do you fancy going?”
                """);
        uiManager.showMessage("Available adventures:\n");
        //listar aventuras en el JSON
        ArrayList<Adventure> adventures = adventureManager.getAdventuresList();
        for(int i = 0; i < adventures.size(); i++){
            uiManager.showMessage((i+1) + ". " + adventures.get(i).getAdventureName() );
        }

        adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");

        if((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
            while ((adventureSelection) > adventures.size() || (adventureSelection) < 1) {
                uiManager.showMessage("Tavern keeper: “Please choose an existing adventure”\n");
                adventureSelection = uiManager.askForInteger("-> Choose an adventure: ");
            }
        }
        uiManager.showMessage("Tavern keeper: “" + adventures.get(adventureSelection - 1).getAdventureName() + " it is!" + "” \n “And how many people shall join you?”");

        characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
        if((characterQuantity) > 5 || (characterQuantity) < 3) {
            while ((characterQuantity) > 5 || (characterQuantity) < 3)  {
                uiManager.showMessage("Tavern keeper: “Please choose a correct number of characters”\n");
                characterQuantity = uiManager.askForInteger("-> Choose a number of characters [3..5]: ");
            }
        }
        uiManager.showMessage("Tavern keeper: “Great, " + characterQuantity + " it is.”\n" + "“Who among these lads shall join you?”");

        int i = 0, j = 0;
        ArrayList<Character> characterInParty = new ArrayList<>(characterQuantity);
        String characterName = null;
        String[] characterNamesList = null;

        for(i = 0; i < characterQuantity; i++){
            characterInParty.add(i, null);
        }


        //creation of the adventure party
        do{
            i = 0;
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Your party (" + j + " / "+ characterQuantity +"):\n");

            while(i < characterQuantity){
                if(characterInParty.get(i) == null){
                    uiManager.showMessage((i+1) + ". Empty");
                }else{
                    uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
                }
                i++;
            }
            uiManager.showMessage("------------------------------");
            uiManager.showMessage("Available characters:");
            i = 0;
            ArrayList<Character> characters = characterManager.getAllCharacters();
            for (Character character: characters) {
                uiManager.showMessage((i+1) + "." + character.getCharacterName());
                i++;
            }
            int CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");



            if (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size()) {
                    uiManager.showMessage("Tavern keeper: “Please choose an existing character”\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                }
            }
            int w = 0;
            for (w=0; w<5; w++) {
                if (saveNumber[w] == CharacterPartySelected) {
                    uiManager.showMessage("Tavern keeper: “You've already chosen this character!”\n");
                    CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    while (CharacterPartySelected < 1 || CharacterPartySelected > characters.size() || saveNumber[w] == CharacterPartySelected) {
                        uiManager.showMessage("Tavern keeper: “Come on! Just choose an existing new character.”\n");
                        CharacterPartySelected = uiManager.askForInteger("-> Choose character "+ (j+1) + " in your party: \n");
                    }
                }
            }

            saveNumber[j] = CharacterPartySelected;
            characterInParty.set(j, characters.get(CharacterPartySelected - 1));
            j++;
        }while(j < characterQuantity);

        i = 0;
        while(i < characterQuantity){
            if(characterInParty.get(i) == null){
                uiManager.showMessage((i+1) + ". Empty");
            }else{
                uiManager.showMessage((i+1) + "." + characterInParty.get(i).getCharacterName());
            }
            i++;
        }

        uiManager.showMessage("\nTavern keeper: “Great, good luck on your adventure lads!”\n");
        uiManager.showMessage("The “" + adventures.get(adventureSelection - 1).getAdventureName()  +"” will start soon...\n");
        counterEncounters = 0;
        int adventureEncounters = adventures.get(adventureSelection - 1).getEncounters();

        //Combat phases

        do{
            uiManager.showMessage("---------------------");
            uiManager.showMessage("Starting Encounter "+ (counterEncounters + 1) +":");

            ArrayList<Monster> monstersInEncounter = adventures.get(adventureSelection - 1).getAdventureEncounterMonsters().get(counterEncounters);
            ArrayList<String> storedName = new ArrayList<>(0);

            adventureManager.countSameMonstersInEncounter(storedName, monstersInEncounter);
            i = 0;
            long count = 0;
            while(i < storedName.size()){
                int finalI1 = i;
                count = monstersInEncounter.stream().filter(m -> m.getMonsterName().equals(storedName.get(finalI1))).count();
                uiManager.showMessage("\t- "+ count + "x " + storedName.get(i));
                i++;
            }

            uiManager.showMessage("---------------------");

            //preparation phase
            uiManager.showMessage("-------------------------");
            uiManager.showMessage("*** Preparation stage ***");
            //get monsters from this battle and encounter number
            uiManager.showMessage("-------------------------\n");
            i = 0;
            while(i < characterQuantity){
                //get characterName from party
                uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses Self-Motivated. Their Spirit increases in +1");
                //update character spirit +1
                int temporalSpirit = characterInParty.get(i).getSpirit();
                temporalSpirit = temporalSpirit + 1;
                characterInParty.get(i).setSpirit(temporalSpirit);
                i++;
            }

            uiManager.showMessage("Rolling initiative...\n");
            int z;
            int monsterQuantity = monstersInEncounter.size();
            int diceRoll = characterManager.diceRollD12();;
            int roundCounter = 0;
            ArrayList<String> charactersLife = new ArrayList<>(0);
            ArrayList<String> monstersLife = new ArrayList<>(0);
            ArrayList<String> listOfPriorities = adventureManager.listOfPriorities(characterQuantity, monsterQuantity, diceRoll, characterInParty, monstersInEncounter );

            adventureManager.orderListOfPriorities(listOfPriorities);

            i = 0;
            while(i < listOfPriorities.size()){

                String[] auxCompareName = listOfPriorities.get(i).split("\\d+");
                String compareName = auxCompareName[0];



                int compareInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));

                uiManager.showMessage("\t- " + compareInitiative + "   " + compareName);

                i++;
            }
            i = 0;

            uiManager.showMessage("\n\n--------------------");
            uiManager.showMessage("*** Combat stage ***");
            uiManager.showMessage("--------------------");

            //storing lives in format, name actual live/total live
            adventureManager.setMonstersLifeList(monstersLife, monstersInEncounter, listOfPriorities, characterQuantity);
            i = 0;
            adventureManager.setAdventurersLifeList(characterInParty, charactersLife);

            do{
                int q = 0;
                int damage = 0;
                String[] auxName;
                String actualName;
                int isCrit = 0;
                int smallerCharacterLife = 0;
                int smallerMonsterLife = 0;
                int lastMonsterIndex = 0;
                int lastCharacterIndex = 0;
                int totalLife = 0;
                int actualLife = 0;
                int flag = 0;
                String attackedMonster;
                String compareName;
                String actualDice = null;
                ArrayList<String> monstersDamage = new ArrayList<>(0);

                uiManager.showMessage("Round "+  (roundCounter + 1) + ":");
                uiManager.showMessage("Party :");

                z = 0;
                while(z < characterInParty.size()) {

                    auxName = charactersLife.get(z).split("\\d+");
                    actualName = auxName[0];
                    String[] auxLife = charactersLife.get(z).split("/");

                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                    totalLife = Integer.parseInt(auxLife[1]);
                    if(z == 0){
                        uiManager.showMessage("\t- "+ actualName + "\t\t\t"+ actualLife + " / " + totalLife + " hit points");
                    }else{
                        uiManager.showMessage("\t- "+ actualName + "\t\t"+ actualLife + " / " + totalLife + " hit points");
                    }
                    z++;
                }

                adventureManager.enemyDice(monstersDamage, monstersInEncounter);

                //battling
                while(q < listOfPriorities.size()){
                    isCrit = characterManager.diceRollD10();
                    i = 0;
                    //isCrit = 1;
                    if(isCrit == 2){

                        auxName = listOfPriorities.get(q).split("\\d+");
                        actualName = auxName[0];
                        z = 0;
                        charactersDefeat = 0;
                        lastCharacterIndex = 0;
                        flag = 0;

                        while(z < characterInParty.size()){
                            String[] auxLife = charactersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerCharacterLife = actualLife;
                                    lastCharacterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerCharacterLife > actualLife){
                                        smallerCharacterLife = actualLife;
                                        lastCharacterIndex = z;
                                    }
                                }

                            }else{
                                charactersDefeat++;
                                flag = 0;
                            }

                            z++;
                        }
                        z = 0;
                        monstersDefeat = 0;
                        lastMonsterIndex = 0;
                        flag = 0;
                        while(z < monstersInEncounter.size()){
                            String[] auxLife = monstersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerMonsterLife = actualLife;
                                    lastMonsterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerMonsterLife > actualLife){
                                        smallerMonsterLife = actualLife;
                                        lastMonsterIndex = z;
                                    }
                                }

                            }else{
                                monstersDefeat++;
                                flag = 0;
                            }
                            z++;
                        }

                        if(charactersDefeat < characterInParty.size()) {
                            z = 0;
                            while (z < characterInParty.size()) {
                                if (actualName.equals(characterInParty.get(z).getCharacterName())) {
                                    String[] auxLife = charactersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    if(actualLife != 0) {
                                        damage = characterManager.characterDamageCalculator(actualName);
                                        auxName = monstersLife.get(lastMonsterIndex).split("\\d+");
                                        attackedMonster = auxName[0];
                                        uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Sword slash.");
                                    }
                                    z = characterInParty.size();
                                }
                                z++;
                            }
                        }

                        if(monstersDefeat < monstersInEncounter.size()){
                            z = 0;
                            while(z < monstersInEncounter.size()){
                                auxName = monstersLife.get(z).split("\\d+");
                                compareName = auxName[0];
                                if(actualName.equals(compareName)){
                                    String[] auxLife = monstersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    i = 0;
                                    while(i < monstersDamage.size()){
                                        String[] auxDice = monstersDamage.get(i).split(" ");
                                        compareName = auxDice[0];
                                        if(actualName.equals(compareName)){
                                            actualDice = auxDice[1];
                                        }else{
                                            actualDice = "d4";
                                        }
                                        i++;
                                    }
                                    if(actualLife != 0 && q == z + characterQuantity) {
                                        damage = monsterManager.monsterDamageCalculator(actualDice);
                                        uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(lastCharacterIndex).getCharacterName());
                                        z = monstersInEncounter.size();
                                    }
                                }
                                z++;
                            }
                        }

                        z = 0;
                        j = 0;
                        i = 0;
                        if(monstersDefeat < monstersInEncounter.size() && charactersDefeat < characterInParty.size() ){
                            while(z < listOfPriorities.size()){
                                while (j < monstersLife.size()) {
                                    auxName = monstersLife.get(j).split("\\d+");
                                    compareName = auxName[0];
                                    if (actualName.equals(compareName)) {
                                        String[] auxLife = charactersLife.get(lastCharacterIndex).split("/");

                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1]);


                                        int total = actualLife - (damage*2);
                                        if(total < 0){
                                            total = 0;
                                        }
                                        auxLife = monstersLife.get(j).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                                        if(actualLife != 0 && j + characterQuantity == q) {
                                            uiManager.showMessage("Critical hit and deals " + (damage * 2) + " physical damage.");
                                            if (total == 0) {
                                                uiManager.showMessage(characterInParty.get(lastCharacterIndex).getCharacterName() + " falls unconscious.");
                                            }
                                            charactersLife.set(lastCharacterIndex, characterInParty.get(lastCharacterIndex).getCharacterName() + total + "/" + totalLife);
                                            z = listOfPriorities.size();
                                            j = monstersInEncounter.size();
                                        }

                                    }
                                    j++;
                                }

                                while(i < characterInParty.size()) {
                                    if(actualName.equals(characterInParty.get(i).getCharacterName())) {

                                        String[] auxLife = monstersLife.get(lastMonsterIndex).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1]);

                                        int total = actualLife - (damage*2);
                                        if(total < 0){
                                            total = 0;
                                        }
                                        auxLife = charactersLife.get(i).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));


                                        if(actualLife != 0) {
                                            uiManager.showMessage("Critical hit and deals " + (damage*2) + " physical damage.");
                                            auxName = monstersLife.get(lastMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];
                                            if (total == 0) {
                                                uiManager.showMessage(attackedMonster + " dies.");
                                            }

                                            z = listOfPriorities.size();
                                            i = characterInParty.size();

                                            monstersLife.set(lastMonsterIndex, attackedMonster + total + "/" + totalLife);
                                        }
                                    }
                                    i++;
                                }

                                z++;
                            }
                        }


                    }else if(isCrit == 1){
                        auxName = listOfPriorities.get(q).split("\\d+");
                        actualName = auxName[0];
                        z = 0;
                        charactersDefeat = 0;
                        lastCharacterIndex = 0;
                        flag = 0;

                        while(z < characterInParty.size()){
                            String[] auxLife = charactersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerCharacterLife = actualLife;
                                    lastCharacterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerCharacterLife > actualLife){
                                        smallerCharacterLife = actualLife;
                                        lastCharacterIndex = z;
                                    }
                                }

                            }else{
                                charactersDefeat++;
                                flag = 0;
                            }

                            z++;
                        }
                        z = 0;
                        monstersDefeat = 0;
                        lastMonsterIndex = 0;
                        flag = 0;
                        while(z < monstersInEncounter.size()){
                            String[] auxLife = monstersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerMonsterLife = actualLife;
                                    lastMonsterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerMonsterLife > actualLife){
                                        smallerMonsterLife = actualLife;
                                        lastMonsterIndex = z;
                                    }
                                }

                            }else{
                                monstersDefeat++;
                                flag = 0;
                            }
                            z++;
                        }

                        if(charactersDefeat < characterInParty.size()) {
                            z = 0;
                            while (z < characterInParty.size()) {
                                if (actualName.equals(characterInParty.get(z).getCharacterName())) {
                                    String[] auxLife = charactersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    if(actualLife != 0) {
                                        damage = characterManager.characterDamageCalculator(actualName);
                                        auxName = monstersLife.get(lastMonsterIndex).split("\\d+");
                                        attackedMonster = auxName[0];
                                        uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Sword slash.");
                                    }
                                    z = characterInParty.size();
                                }
                                z++;
                            }
                        }

                        if(monstersDefeat < monstersInEncounter.size()){
                            z = 0;
                            while(z < monstersInEncounter.size()){
                                auxName = monstersLife.get(z).split("\\d+");
                                compareName = auxName[0];
                                if(actualName.equals(compareName)){
                                    String[] auxLife = monstersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    i = 0;
                                    while(i < monstersDamage.size()){
                                        String[] auxDice = monstersDamage.get(i).split(" ");
                                        compareName = auxDice[0];
                                        if(actualName.equals(compareName)){
                                            actualDice = auxDice[1];
                                        }else{
                                            actualDice = "d4";
                                        }
                                        i++;
                                    }
                                    if(actualLife != 0 && q == z + characterQuantity) {
                                        damage = monsterManager.monsterDamageCalculator(actualDice);
                                        uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(lastCharacterIndex).getCharacterName());
                                        z = monstersInEncounter.size();
                                    }
                                }
                                z++;
                            }
                        }

                        z = 0;
                        j = 0;
                        i = 0;
                        if(monstersDefeat < monstersInEncounter.size() && charactersDefeat < characterInParty.size() ){
                            while(z < listOfPriorities.size()){
                                while (j < monstersLife.size()) {
                                    auxName = monstersLife.get(j).split("\\d+");
                                    compareName = auxName[0];
                                    if (actualName.equals(compareName)) {
                                        String[] auxLife = charactersLife.get(lastCharacterIndex).split("/");

                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1]);


                                        int total = actualLife - damage;
                                        if(total < 0){
                                            total = 0;
                                        }
                                        auxLife = monstersLife.get(j).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                                        if(actualLife != 0 && j + characterQuantity == q) {
                                            uiManager.showMessage("Hit and deals " + (damage) + " physical damage.");
                                            if (total == 0) {
                                                uiManager.showMessage(characterInParty.get(lastCharacterIndex).getCharacterName() + " falls unconscious.");
                                            }
                                            charactersLife.set(lastCharacterIndex, characterInParty.get(lastCharacterIndex).getCharacterName() + total + "/" + totalLife);
                                            z = listOfPriorities.size();
                                            j = monstersInEncounter.size();
                                        }

                                    }
                                    j++;
                                }

                                while(i < characterInParty.size()) {
                                    if(actualName.equals(characterInParty.get(i).getCharacterName())) {

                                        String[] auxLife = monstersLife.get(lastMonsterIndex).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                        totalLife = Integer.parseInt(auxLife[1]);

                                        int total = actualLife - damage;
                                        if(total < 0){
                                            total = 0;
                                        }
                                        auxLife = charactersLife.get(i).split("/");
                                        actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));


                                        if(actualLife != 0) {
                                            uiManager.showMessage("Hit and deals " + (damage) + " physical damage.");
                                            auxName = monstersLife.get(lastMonsterIndex).split("\\d+");
                                            attackedMonster = auxName[0];
                                            if (total == 0) {
                                                uiManager.showMessage(attackedMonster + " dies.");
                                            }

                                            z = listOfPriorities.size();
                                            i = characterInParty.size();

                                            monstersLife.set(lastMonsterIndex, attackedMonster + total + "/" + totalLife);
                                        }
                                    }
                                    i++;
                                }

                                z++;
                            }
                        }
                    }else{
                        auxName = listOfPriorities.get(q).split("\\d+");
                        actualName = auxName[0];
                        z = 0;
                        charactersDefeat = 0;
                        lastCharacterIndex = 0;
                        flag = 0;

                        while(z < characterInParty.size()){
                            String[] auxLife = charactersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));

                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerCharacterLife = actualLife;
                                    lastCharacterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerCharacterLife > actualLife){
                                        smallerCharacterLife = actualLife;
                                        lastCharacterIndex = z;
                                    }
                                }

                            }else{
                                charactersDefeat++;
                                flag = 0;
                            }

                            z++;
                        }
                        z = 0;
                        monstersDefeat = 0;
                        lastMonsterIndex = 0;
                        flag = 0;
                        while(z < monstersInEncounter.size()){
                            String[] auxLife = monstersLife.get(z).split("/");
                            actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                            if(actualLife != 0){
                                if(flag == 0){
                                    smallerMonsterLife = actualLife;
                                    lastMonsterIndex = z;
                                    flag = 1;
                                }else{
                                    if(smallerMonsterLife > actualLife){
                                        smallerMonsterLife = actualLife;
                                        lastMonsterIndex = z;
                                    }
                                }

                            }else{
                                monstersDefeat++;
                                flag = 0;
                            }
                            z++;
                        }

                        if(charactersDefeat < characterInParty.size()) {
                            z = 0;
                            while (z < characterInParty.size()) {
                                if (actualName.equals(characterInParty.get(z).getCharacterName())) {
                                    String[] auxLife = charactersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    if(actualLife != 0) {
                                        damage = characterManager.characterDamageCalculator(actualName);
                                        auxName = monstersLife.get(lastMonsterIndex).split("\\d+");
                                        attackedMonster = auxName[0];
                                        uiManager.showMessage("\n" + actualName + " attacks " + attackedMonster + " with Sword slash.");
                                    }
                                    z = characterInParty.size();
                                }
                                z++;
                            }
                        }

                        if(monstersDefeat < monstersInEncounter.size()){
                            z = 0;
                            while(z < monstersInEncounter.size()){
                                auxName = monstersLife.get(z).split("\\d+");
                                compareName = auxName[0];
                                if(actualName.equals(compareName)){
                                    String[] auxLife = monstersLife.get(z).split("/");
                                    actualLife = Integer.parseInt(auxLife[0].replaceAll("[^0-9]", ""));
                                    i = 0;
                                    while(i < monstersDamage.size()){
                                        String[] auxDice = monstersDamage.get(i).split(" ");
                                        compareName = auxDice[0];
                                        if(actualName.equals(compareName)){
                                            actualDice = auxDice[1];
                                        }else{
                                            actualDice = "d4";
                                        }
                                        i++;
                                    }
                                    if(actualLife != 0 && q == z + characterQuantity) {
                                        damage = monsterManager.monsterDamageCalculator(actualDice);
                                        uiManager.showMessage("\n" + actualName + " attacks " + characterInParty.get(lastCharacterIndex).getCharacterName());
                                        z = monstersInEncounter.size();
                                    }
                                }
                                z++;
                            }
                        }
                        uiManager.showMessage("Fails and deals 0 physical damage fail");
                    }


                    if(charactersDefeat == characterInParty.size()){
                        q = listOfPriorities.size();
                    }
                    else if(monstersDefeat == monstersInEncounter.size()){
                        q = listOfPriorities.size();
                    }
                    else{
                        q++;
                    }
                }

                if(charactersDefeat == characterInParty.size()){
                    defeated = 1;
                }else{
                    uiManager.showMessage("\nEnd of round "+ (roundCounter + 1) +".\n");
                    roundCounter++;
                }

            }while(monstersDefeat != monstersInEncounter.size() && charactersDefeat != characterInParty.size());

            if(defeated == 0){
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

                //Bandage time
                i = 0;
                while(i < characterQuantity){

                    String[] parts = charactersLife.get(i).split("/");
                    int temporalLife = Integer.parseInt(parts[0].replaceAll("[^0-99]", ""));
                    int restLife =  Integer.parseInt(parts[1]);
                    if(temporalLife != 0) {
                        //int healing = characterManager.BandageTime(characterNamesList[i]);
                        int diceRollHeal = characterManager.diceRollD8();
                        int characterMind = characterInParty.get(i).getMind();
                        int characterCuration = diceRollHeal + characterMind;
                        int characterBandage =  characterCuration + temporalLife;

                        if(characterBandage > restLife) {
                            characterBandage = restLife;
                        }

                        charactersLife.set(i, characterInParty.get(i).getCharacterName() + characterBandage + "/" + restLife);

                        uiManager.showMessage(characterInParty.get(i).getCharacterName() + " uses BandageTime. Heals " + characterCuration + " hit points");
                    }else{
                        uiManager.showMessage(characterInParty.get(i).getCharacterName() + " is unconscious");
                    }
                    i++;
                }
            }else{
                counterEncounters = 6;
            }
            counterEncounters++;
        }while(counterEncounters < adventureEncounters);

        if(defeated != 0) {
            uiManager.showMessage("""
                    Tavern keeper: “Lad, wake up. Yes, your party fell unconscious.”
                    “Don’t worry, you are safe back at the Tavern.”
                    """);
        }else{
            uiManager.showMessage("Congratulations, your party completed “"+ adventures.get(adventureSelection - 1).getAdventureName() +"”\n");
        }

    }
}
