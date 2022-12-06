package business;

import java.util.Random;

public class CharacterManager {

    public CharacterManager(){

    }


    public void createCharacter(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass){





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
}
