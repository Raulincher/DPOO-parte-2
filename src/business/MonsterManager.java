package business;

import business.entities.Monster;
import persistance.MonsterDAO;

public class MonsterManager {

    MonsterDAO monsterDAO;

    public MonsterManager(MonsterDAO monsterDAO) {

        this.monsterDAO = monsterDAO;
    }

    public Monster[] getAllMonsters(){return monsterDAO.getListOfMonsters();}
}
