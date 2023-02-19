package business;

import business.entities.Character;
import persistance.CharacterDAO;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterManager {

    CharacterDAO characterDAO;

    public CharacterManager(CharacterDAO characterDAO){
        this.characterDAO = characterDAO;
    }

    public boolean createCharacter(String characterName, String playerName, int characterLevel, int body, int mind, int spirit){
        return characterDAO.saveCharacter(new Character(characterName, playerName, characterLevel, body, mind, spirit));
    }


    public int[] diceRoll2D6(){

        int[] roll = {0, 0};

        Random rand = new Random();
        int upperbound = 6;
        roll[0] = rand.nextInt(upperbound) + 1;
        roll[1] = rand.nextInt(upperbound) + 1;

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

        int xp;
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

    public void updateCharacterSpirit(){
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
    }

    public int getCharacterSpirit(String characterName){
        int spirit = 0;
        int i = 0;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();

        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                spirit = characters.get(i).getSpirit();
            }
            i++;
        }
        return spirit;
    }

    public int getCharacterBody(String characterName){
        int body = 0;
        int i = 0;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();

        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                body = characters.get(i).getBody();
            }
            i++;
        }
        return body;
    }

    public int getCharacterMind(String characterName){
        int mind = 0;
        int i = 0;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();

        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                mind = characters.get(i).getMind();
            }
            i++;
        }
        return mind;
    }

    public int getCharacterXp(String characterName){
        int xp = 0;
        int i = 0;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();

        while(i < characters.size()){
            if(characterName.equals(characters.get(i).getCharacterName())){
                xp = characters.get(i).getCharacterLevel();
            }
            i++;
        }
        return xp;
    }

    /*public int initiativeCalculator(String characterName){

        int roll = diceRollD12();
        int initiative = 0;
        int spirit = getCharacterSpirit(characterName);

        initiative = roll + spirit;

        return initiative;
    }*/

    public int diceRollD12(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    public int diceRollD10(){
        int roll = 0;
        int damage;

        Random rand = new Random();
        int upperbound = 10;
        roll = rand.nextInt(upperbound) + 1;

        if(roll == 1){
            damage = 0;
        }else if(roll < 10){
            damage = 1;
        }else{
            damage = 2;
        }

        return damage;
    }

    public int diceRollD6(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 7;
        roll = rand.nextInt(upperbound);

        if(roll == 0){
            roll = 1;
        }
        return roll;
    }

    public int diceRollD8(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 9;
        roll = rand.nextInt(upperbound);

        return roll;
    }



    public int characterDamageCalculator(String characterName){
        int damage = 0;
        int body = getCharacterBody(characterName);

        damage = diceRollD6() + body;

        return damage;
    }

    public int BandageTime(String characterName){
        int healing = 0;
        int mind = getCharacterMind(characterName);

        healing = diceRollD8() + mind;

        return healing;
    }




    public int revertXpToLevel(int xp){
        int level;
        if(xp >= 0 && xp <= 99){
            level = 1;
        }else if(xp >= 100 && xp <= 199){
            level = 2;
        }else if(xp >= 200 && xp <= 299){
            level = 3;
        }else if(xp >= 300 && xp <= 399){
            level = 4;
        }else if(xp >= 400 && xp <= 499){
            level = 5;
        }else if(xp >= 500 && xp <= 599){
            level = 6;
        }else if(xp >= 600 && xp <= 699){
            level = 7;
        }else if(xp >= 700 && xp <= 799){
            level = 8;
        }else if(xp >= 800 && xp <= 899){
            level = 9;
        }else{
            level = 10;
        }

        return level;
    }

    public int initialLifeCalculator(String characterName){

        int life;
        int body = getCharacterBody(characterName);
        int xp = getCharacterXp(characterName);
        int level = revertXpToLevel(xp);

        life = (10 + body) * level;

        return life;
    }

    public boolean characterNameDisponibility(String name){

        boolean exist = false;
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
        int i = 0;

        while(i < characters.size() && !exist){
            if(name.toLowerCase(Locale.ROOT).matches(characters.get(i).getCharacterName().toLowerCase(Locale.ROOT))){
                exist = true;
            }
            i++;
        }

        return exist;
    }


    public String fixName(String name){
        String auxName = name;
        String[] auxNameSplit = auxName.split(" ");
        String fixedName = null;
        int i = 0;

        while(i < auxNameSplit.length){
            auxNameSplit[i] = auxNameSplit[i].substring(0,1).toUpperCase() + auxNameSplit[i].substring(1);
            if (i == 0){
                fixedName = auxNameSplit[i];
            }else{
                fixedName = fixedName.concat(auxNameSplit[i]);
            }
            fixedName = fixedName.concat(" ");
            i++;
        }

        fixedName = fixedName.substring(0, fixedName.length() - 1);

        return fixedName;
    }
    public ArrayList<Character> getAllCharacters(){
        return characterDAO.readCharacterJSON();
    }


    public boolean nameCheck(String name){
        boolean correct;

        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        correct = !hasDigit.find() && !hasSpecial.find();

        return correct;
    }

    public boolean levelUpCheck(int xp, int playerLevel){

        boolean levelUp = false;

        int pastLevel = revertXpToLevel(playerLevel);
        int actualLevel = revertXpToLevel(playerLevel + xp);
        int auxCheck = actualLevel - pastLevel;
        if(auxCheck != 0){
            levelUp = true;
        }
        return levelUp;
    }



    public ArrayList<Character> filteredPlayers(String playerName){
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
        ArrayList<Character> filteredCharacters = null;
        int i = 0;
        int j = 0;
        boolean noCoincidence = true;
        if(!Objects.equals(playerName, "")){
            while(i < characters.size()){
                if(characters.get(i).getPlayerName().toLowerCase(Locale.ROOT).contains(playerName.toLowerCase(Locale.ROOT))){
                    j++;
                }
                i++;
            }

            filteredCharacters = new ArrayList<>(j);
            for (i = 0; i < j; i++) {
                filteredCharacters.add(characters.get(i));
            }

            i = 0;
            j = 0;
            while(i < characters.size()){
                if(characters.get(i).getPlayerName().toLowerCase(Locale.ROOT).contains(playerName.toLowerCase(Locale.ROOT))){
                    filteredCharacters.set(j, characters.get(i));
                    j++;
                    noCoincidence = false;
                }else{
                    if(j == 0){
                        noCoincidence = true;
                    }
                }
                i++;
            }
        }else{

            filteredCharacters = getAllCharacters();
            noCoincidence = false;
        }

        if(noCoincidence){
            filteredCharacters = new ArrayList<>(1);
            filteredCharacters.add(null);
        }

        return filteredCharacters;
    }

    public Character getCharacterByName(String characterName){
        ArrayList<Character> characters = characterDAO.readCharacterJSON();
        Character filteredCharacter = null;

        int i = 0;

        while(i < characters.size()){
            if(characters.get(i).getCharacterName().toLowerCase(Locale.ROOT).contains(characterName.toLowerCase(Locale.ROOT))){
                filteredCharacter = characters.get(i);
                i = characters.size();
            }
            i++;
        }
        return filteredCharacter;
    }
    public boolean deleteCharacter(String characterName){
       return characterDAO.deleteCharacterByName(characterName);
    }
    public void levelUpdate(Character character, int gainedXp){
        characterDAO.updateCharacterLevel(character,gainedXp);
    }


}
