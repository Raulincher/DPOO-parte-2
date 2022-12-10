package business.entities;

import java.util.ArrayList;

public class Adventure {
    String name;
    int encounters;
    ArrayList<ArrayList<Monster>> encounterMonsters;

    public Adventure(String name, int encounters, ArrayList<ArrayList<Monster>> encounterMonsters){
        this.name = name;
        this.encounterMonsters = encounterMonsters;
        this.encounters = encounters;

    }

    public String getAdventureName(){return name;}
    public ArrayList<ArrayList<Monster>> getAdventureEncounterMonsters() {return encounterMonsters;}
    public int getEncounters() {return encounters;}
    public ArrayList<Monster> getEncounterMonsters(int i) {return encounterMonsters.get(i);}

}
