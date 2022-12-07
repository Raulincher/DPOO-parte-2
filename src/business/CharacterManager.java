package business;

import business.entities.Character;
import persistance.CharacterDAO;

import java.util.Locale;
import java.util.Random;

public class CharacterManager {

    CharacterDAO characterDAO;

    public CharacterManager(CharacterDAO characterDAO){
        this.characterDAO = characterDAO;
    }

    public void createCharacter(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass){
        characterDAO.saveCharacter(new Character(characterName, playerName, characterLevel, body, mind, spirit, characterClass));
    }

    public int[] diceRoll2D6(){

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

    public int initiativeCalculator(String characterName){

        int roll = diceRollD12();
        int initiative = 0;
        int spirit = characterDAO.getSpiritByName(characterName);

        initiative = roll + spirit;

        return initiative;
    }

    public int diceRollD12(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 13;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    public int diceRollD10(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 11;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    public int diceRollD6(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 7;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    public int diceRollD8(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 9;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    public int initialLifeCalculator(String characterName){

        int life = 0;
        int body = characterDAO.getBodyByName(characterName);
        int xp = characterDAO.getXpByName(characterName);

        life = (10 + body) * revertXpToLevel(xp);

        return life;
    }

    public int damageCalculator(String characterName){
        int damage = 0;
        int body = characterDAO.getBodyByName(characterName);

        damage = diceRollD6() + body;

        return damage;
    }

    public int BandageTime(String characterName){
        int healing = 0;
        int mind = characterDAO.getMindByName(characterName);

        healing = diceRollD8() + mind;

        return healing;
    }


    public int revertXpToLevel(int xp){
        int level = 0;
        if(xp >= 0 && xp <= 99){
            level = 1;
        }else if(xp >= 200 && xp <= 199){
            level = 2;
        }else if(xp >= 300 && xp <= 299){
            level = 3;
        }else if(xp >= 400 && xp <= 399){
            level = 4;
        }else if(xp >= 500 && xp <= 499){
            level = 5;
        }else if(xp >= 600 && xp <= 599){
            level = 6;
        }else if(xp >= 700 && xp <= 699){
            level = 7;
        }else if(xp >= 800 && xp <= 799){
            level = 8;
        }else if(xp >= 900 && xp <= 899){
            level = 9;
        }else{
            level = 10;
        }

        return level;
    }
    public Character[] getAllCharacters(){
        return characterDAO.readCharacterJSON();
    }

    public Character[] filteredPlayers(String playerName){
        Character[] characters = characterDAO.readCharacterJSON();
        Character[] filteredCharacters = null;
        int i = 0;
        int j = 0;
        if(playerName != ""){
            while(i < characters.length){
                if(characters[i].getPlayerName().toLowerCase(Locale.ROOT).contains(playerName.toLowerCase(Locale.ROOT))){
                    filteredCharacters[j] = characters[i];
                    j++;
                    System.out.println("elpepep");
                }
                i++;
            }
        }else{

            filteredCharacters = getAllCharacters();

        }


        return filteredCharacters;
    }
    public Character filteredCharacter(String characterName){return characterDAO.getCharacterByName(characterName);}
    public void deleteCharacter(String characterName){
        characterDAO.deleteCharacterByName(characterName);
    }
}
