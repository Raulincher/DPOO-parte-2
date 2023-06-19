package persistance;

import business.entities.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase DAO de los monstruos donde hablaremos con el Json para obtener la info necesaria
 */
public class MonsterDAO {

    // Añadimos atributos y json
    private final Gson gson;
    public String monsterPath = "files/monsters.json";

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir CharacterDAO
     * No tendrá ni param ni return
     */
    public MonsterDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Esta función servirá para recoger una lista con
     * todos los Monsters que hayan en el JSON
     *
     * @return monsters, ArrayList en cuestión
     */
    public ArrayList<Monster> getListOfMonsters(){

        Monster[] currentMonsters;
        ArrayList<Monster> monsters = new ArrayList<>();

        // Abrimos un try & catch para leer el JSON
        try
        {
            currentMonsters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(monsterPath))), Monster[].class);

            // Nos guardamos lo escrito
            if (currentMonsters != null) {
                monsters.addAll(Arrays.asList(currentMonsters));
            }else{
                // En caso contrario lo marcamos como null
                monsters = null;
            }
        }
        // Añadimos excepción por si no puede leer el JSON
        catch (FileNotFoundException ignored)
        {

        }

        return monsters;
    }

}
