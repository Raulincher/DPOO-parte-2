package persistance;

import business.entities.Monster;
import com.google.gson.Gson;

public class AdventureDAO {

    private Gson gson;

    public AdventureDAO() {
        this.gson = new Gson();
    }

    public Monster[] getMonstersByAdventure(String adventureName){

        Monster[] monsters = null;

        //codigo de busqueda de monstruos en json

        return monsters;
    }


    public int getMonsterQuantityByName(String monsterName){

        int quantity = 0;

        //codigo para contar cuantos monstruos con x nombre hay

        return quantity;
    }
}
