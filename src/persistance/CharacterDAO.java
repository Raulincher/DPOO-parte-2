package persistance;

import business.entities.Character;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CharacterDAO {

    private Gson gson;

    public CharacterDAO() {
        this.gson = new Gson();
    }

    public Character[] readCharacterJSON(){

        Character[] character = null;
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/characters.json"));

            // convert a JSON string to a User object
            character = gson.fromJson(reader, Character[].class);

            // close reader
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }


        System.out.println(character[0]);


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

    public Character[] getCharacterByPlayer(String playerName){
        Character[] character = null;
        if(playerName != null){

            //buscar por nombre
        }else{
            //devolver todos
        }
        return character;
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
