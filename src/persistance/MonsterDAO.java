package persistance;

import business.entities.Character;
import business.entities.Monster;
import com.google.gson.Gson;

import java.io.FileReader;

public class MonsterDAO {
    private Gson  gson;

    public MonsterDAO() {
        this.gson = new Gson();
    }

    public Monster[] getListOfMonsters(){

        Monster[] monster = null;

        try {
            monster = gson.fromJson(new FileReader("src/monsters.json"), Monster[].class);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return monster;
    }

}
