package business;

import business.entities.Monster;
import persistance.MonsterDAO;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MonsterManager {

    MonsterDAO monsterDAO;

    public MonsterManager(MonsterDAO monsterDAO) {

        this.monsterDAO = monsterDAO;
    }

    public ArrayList<Monster> getAllMonsters(){return monsterDAO.getListOfMonsters();}


    public int monsterDamageCalculator(String diceType){
        int damage = 0;

        if(Objects.equals(diceType, "d4")){
            damage = diceRollD4();
        }else if(Objects.equals(diceType, "d6")){
            damage = diceRollD6();
        }else if(Objects.equals(diceType, "d8")){
            damage = diceRollD8();
        }else if(Objects.equals(diceType, "d10")){
            damage = diceRollD10();
        }else if(Objects.equals(diceType, "d12")){
            damage = diceRollD12();
        }else{
            damage = diceRollD20();
        }

        return damage;
    }


    public int diceRollD4(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 4;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }
    public int diceRollD20(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 20;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }
    public int diceRollD12(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    public int diceRollD10(){
        int roll = 0;

        Random rand = new Random();
        int upperbound = 10;
        roll = rand.nextInt(upperbound) + 1;

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

}
