package business;

import business.entities.Monster;
import persistance.AdventureDAO;

public class AdventureManager {

    AdventureDAO adventureDAO;


    public AdventureManager(AdventureDAO adventureDAO){
        this.adventureDAO = adventureDAO;

    }

    public Monster[] getAdventureMonsters(String adventureName){

        return adventureDAO.getMonstersByAdventure(adventureName);

    }

    public int countMonsters(String monsterName){
      return adventureDAO.getMonsterQuantityByName(monsterName);
    }


}
