package business;

import business.entities.Character;
import persistance.CharacterDAO;

import java.util.Random;

public class CharacterManager {

    CharacterDAO characterDAO;

    public CharacterManager(CharacterDAO characterDAO){
        this.characterDAO = characterDAO;
    }

    public Character[] getAllCharacters(){
        return characterDAO.readCharacterJSON();
    }


    public void createCharacter(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass){

        Character character = new Character(characterName, playerName, characterLevel, body, mind, spirit, characterClass);
        characterDAO.saveCharacter(character);

    }

    public int[] diceRoll(){

        int[] roll = {0, 0};

        Random rand = new Random();
        int upperbound = 7;
        roll[0] = rand.nextInt(upperbound);
        roll[1] = rand.nextInt(upperbound);

        return roll;
    }


    public String statCalculator(int[] stat){

        String statBonus;

        if(stat[0] + stat[1] <= 2){
            statBonus = "-1";
        }else if(stat[0] + stat[1] <= 5 && stat[0] + stat[1] >= 3){
            statBonus = "+0";
        }else if(stat[0] + stat[1] <= 9 && stat[0] + stat[1] >= 6){
            statBonus = "+1";
        }else if(stat[0] + stat[1] <= 11 && stat[0] + stat[1] >= 10){
            statBonus = "+2";
        }else {
            statBonus = "+3";
        }
        return statBonus;
    }

    public int experienceCalculator(int level){

        int xp = 0;
        if(level == 1){
            xp = 0;
        }else if(level == 2){
            xp = 100;
        }else if(level == 3){
            xp = 200;
        }else if(level == 4){
            xp = 300;
        }else if(level == 5){
            xp = 400;
        }else if(level == 6){
            xp = 500;
        }else if(level == 7){
            xp = 600;
        }else if(level == 8){
            xp = 700;
        }else if(level == 9){
            xp = 800;
        }else{
            xp = 900;
        }
        return xp;
    }


    public Character[] filteredPlayers(String playerName){
        return characterDAO.getCharacterByPlayer(playerName);
    }

    public void deleteCharacter(String characterName){
        characterDAO.deleteCharacterByName(characterName);
    }
}
