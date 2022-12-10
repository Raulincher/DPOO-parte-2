package persistance;

import business.entities.Character;
import business.entities.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MonsterDAO {
    private Gson  gson;
    public String monsterPath = "files/monsters.json";

    public MonsterDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public ArrayList<Monster> getListOfMonsters(){

        Monster[] currentMonsters;
        ArrayList<Monster> monsters = new ArrayList<>();

        try
        {
            currentMonsters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(monsterPath))), Monster[].class);

            if (currentMonsters != null) {
                monsters.addAll(Arrays.asList(currentMonsters));
            }else{
                monsters = null;
            }
        }
        catch (FileNotFoundException ignored)
        {

        }

        return monsters;
    }

}
