package business;

import business.entities.Monster;
import persistance.MonsterDAO;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MonsterManager {

    // Añadimos los componentes
    MonsterDAO monsterDAO;

    // Creamos los constructores
    /**
     * Esta función servirá para construir el MonsterManager
     *
     * @param monsterDAO, lo vincularemos con su respectivo DAO
     */
    public MonsterManager(MonsterDAO monsterDAO) {
        this.monsterDAO = monsterDAO;
    }

    /**
     * Esta función genera una ArrayList con todos los Monsters
     *
     * @return ArrayList con los Monsters
     */
    public ArrayList<Monster> getAllMonsters(){return monsterDAO.getListOfMonsters();}

    /**
     * Esta función calcula cuanto daño causará un Monster dependiendo
     * del tipo de azar que tenga
     *
     * @param diceType, es el tipo de azar que tiene el Monster
     * @return damage, que será el daño que causará
     */
    public int monsterDamageCalculator(String diceType){
        int damage = 0;

        // Abrimos los ifs dependiendo del tipo de azar que tenga el Monster y asignamos damage
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

    /**
     * Esta función genera un número entre el 1 y el 4 simulando tirar
     * un dado de 4 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD4(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 4
        Random rand = new Random();
        int upperbound = 4;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 20 simulando tirar
     * un dado de 20 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD20(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 20
        Random rand = new Random();
        int upperbound = 20;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 12 simulando tirar
     * un dado de 12 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD12(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 12
        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 10 simulando tirar
     * un dado de 10 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD10(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 10
        Random rand = new Random();
        int upperbound = 10;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 6 simulando tirar
     * un dado de 6 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD6(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 7
        Random rand = new Random();
        int upperbound = 7;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    /**
     * Esta función genera un número entre el 1 y el 8 simulando tirar
     * un dado de 8 caras
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD8(){
        int roll = 0;

        // A través de la variable random generamos el número con upperbound de 8
        Random rand = new Random();
        int upperbound = 9;
        roll = rand.nextInt(upperbound);

        return roll;
    }

    /**
     * Esta función permite poner la vida actual de los monstruos al máximo
     * para así dar inicio al encuentro
     *
     * @param monsters, lista de los monstruos del encuentro
     */
    public void setInitialMonsterLife(ArrayList<Monster> monsters){

        for (Monster monster : monsters) {
            monster.setActualHitPoints(monster.getMonsterHitPoints());
        }
    }


    /**
     * Esta función nos permite saber que número tiene el dado del monstruo
     *
     * @param dice, es el tipo de azar que tiene el Monster
     * @return diceNumber, será el número que tenga el dado en cuestión
     */
    public String diceNumber(String dice){
        String diceNumber;
        String[] auxDice = dice.split("d");
        diceNumber = auxDice[1];
        return diceNumber;
    }

}
