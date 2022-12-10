package business;

import business.entities.Adventure;
import business.entities.Monster;
import persistance.AdventureDAO;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AdventureManager {

    AdventureDAO adventureDAO;


    public AdventureManager(AdventureDAO adventureDAO){
        this.adventureDAO = adventureDAO;

    }

    public void setMonstersEncounter(ArrayList<Monster> monsters, ArrayList<ArrayList<Monster>> encounterMonsters, int monsterOption, int lastQuantity ,int monsterQuantity, int auxEncounter){

        int i = 0;
        while(i < monsterQuantity) {
            encounterMonsters.get(auxEncounter).add(lastQuantity + i, monsters.get(monsterOption - 1));
            i++;
        }

    }

    public void monsterNamesRepeated(ArrayList<String> names, ArrayList<Monster> monsters, int z){
        int  j = 0;

        if(Objects.equals(names.get(0), "hola")){
            names.set(0, monsters.get(0).getMonsterName());
        }else {
            for (int i = 0; i < monsters.size() || j < names.size(); i++) {
                if (!names.get(j).equals(monsters.get(i).getMonsterName())) {
                    System.out.println(names.size());

                    if(names.size() <= z - 1){
                        System.out.println(names.size());
                        System.out.println("here 1");
                        names.add(z - 1, monsters.get(i).getMonsterName());
                        j++;
                    }else{
                        System.out.println("here 2");
                        names.set(z - 1, monsters.get(i).getMonsterName());
                        j++;
                    }
                }
                System.out.println(names.size());

                System.out.println(j);
            }
        }

    }


    public boolean createAdventure(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters){

        return adventureDAO.saveAdventure(new Adventure(adventureName, encounters, monsters));

    }

}
