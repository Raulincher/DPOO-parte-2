package business;

import business.entities.Monster;
import persistance.MonsterDAO;

import java.util.ArrayList;

public class MonsterManager {

    MonsterDAO monsterDAO;

    public MonsterManager(MonsterDAO monsterDAO) {

        this.monsterDAO = monsterDAO;
    }

    public ArrayList<Monster> getAllMonsters(){return monsterDAO.getListOfMonsters();}

}
