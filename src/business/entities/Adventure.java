package business.entities;

import java.util.ArrayList;

/**
 * Clase aventura la cual nos permitira manejar aventuras
 */
public class Adventure {
    //preparando variables
    String name;
    int encounters;
    ArrayList<ArrayList<Monster>> encounterMonsters;

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir la Adventure
     *
     * @param name, nombre de la Adventure
     * @param encounters, num de encuentros de la Adventure
     * @param encounterMonsters, ArrayList de los encuentros de la Adventure
     */
    public Adventure(String name, int encounters, ArrayList<ArrayList<Monster>> encounterMonsters){
        this.name = name;
        this.encounterMonsters = encounterMonsters;
        this.encounters = encounters;

    }

    /**
     * Esta función llamará al nombre de la aventura
     *
     * @return name, que será el nombre de la aventura
     */
    public String getAdventureName(){return name;}

    /**
     * Esta función llamará a la ArrayList de todos los monstruos
     * que se encontrarán en aquella aventura
     *
     * @return encounterMonsters, que será la ArrayList en cuestión
     */
    public ArrayList<ArrayList<Monster>> getAdventureEncounterMonsters() {return encounterMonsters;}

    /**
     * Esta función llamará al num de encuentros de la aventura
     *
     * @return encounters, que será el num en cuestión
     */
    public int getEncounters() {return encounters;}

}
