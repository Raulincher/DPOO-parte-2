package persistance;

import business.entities.Character;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class CharacterDAO {

    private Gson gson;

    public CharacterDAO() {
        this.gson = new Gson();
    }

    public Character[] readCharacterJSON(){

        Character[] character = null;

        try {
            character = gson.fromJson(new FileReader("src/characters.json"), Character[].class);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return character;

    }

    public void saveCharacter(Character character) {
        /*JSONObject jsonObject = new JSONObject();

        String characterName = character.getCharacterName();
        String playerName = character.getPlayerName();
        int xp = character.getCharacterLevel();
        int body = character.getBody();
        int mind = character.getMind();
        int spirit = character.getSpirit();
        String characterClass = character.getCharacterClass();

        jsonObject.put("class", characterClass);
        jsonObject.put("name", characterName);
        jsonObject.put("player", playerName);
        jsonObject.put("xp", xp);
        jsonObject.put("body", body);
        jsonObject.put("mind", mind);
        jsonObject.put("spirit", spirit);*/

        try {
            FileWriter fw = new FileWriter("src/characters.json", true);
            gson.toJson(character, fw);
            fw.flush();
            fw.close();

            /*FileWriter file = new FileWriter("src/characters.json", true);
            file.write(jsonObject.toJSONString());
            file.close();*/
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Character generated correctly!");
    }

    public Character getCharacterByName(String characterName){
        Character character = null;

        return character;
    }
    public void deleteCharacterByName(String characterName){
        //Aqui va el codigo de eliminar respecto nombre
    }
    public int getSpiritByName(String characterName){

        int spirit = 0;
        //Aqui va el codigo de buscar respecto nombre
        return spirit;
    }
    public int getBodyByName(String characterName){

        int body = 0;
        //Aqui va el codigo de buscar respecto nombre
        return body;
    }
    public int getMindByName(String characterName){

        int mind = 0;
        //Aqui va el codigo de buscar respecto nombre
        return mind;
    }
    public int getXpByName(String characterName){

        int xp = 0;
        //Aqui va el codigo de buscar respecto nombre
        return xp;
    }
}
