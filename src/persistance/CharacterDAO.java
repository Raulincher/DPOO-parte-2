package persistance;

import business.entities.Character;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CharacterDAO {

    private final Gson gson;
    public String characterPath = "files/characters.json";

    public CharacterDAO() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        File jsonCharacterFile = new File(String.valueOf(characterPath));

        if (!jsonCharacterFile.exists())
        {
            try {
                jsonCharacterFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Character> readCharacterJSON()
    {
        Character[] currentCharacters;
        ArrayList<Character> characters = new ArrayList<>();

        try
        {
            currentCharacters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);

            if (currentCharacters != null) {
                characters.addAll(Arrays.asList(currentCharacters));
            }
        }
        catch (FileNotFoundException ignored)
        {

        }

        return characters;
    }



    public boolean saveCharacter(Character character) {

        FileWriter writer;
        ArrayList<Character> characters = new ArrayList<>();
        Character[] currentCharacter;
        boolean saved = false;

        try
        {
            currentCharacter = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);

            writer = new FileWriter(String.valueOf(characterPath));
            if (currentCharacter != null) {
                characters.addAll(Arrays.asList(currentCharacter));
            }
            characters.add(character);

            gson.toJson(characters, writer);

            writer.close();

            saved = true;

        }
        catch(FileNotFoundException ignored)
        {

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return saved;

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
