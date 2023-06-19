package persistance;

import business.entities.Character;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Clase DAO de los PJs donde hablaremos con el Json para obtener la info necesaria
 */
public class CharacterDAO {

    // Añadimos atributos y json
    private final Gson gson;
    public String characterPath = "files/characters.json";

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir CharacterDAO
     * No tendrá ni param ni return
     */
    public CharacterDAO() {

        // Creamos la variable gson y leemos el JSON en cuestión
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        File jsonCharacterFile = new File(String.valueOf(characterPath));

        // En caso de que no se lea, creamos uno
        if (!jsonCharacterFile.exists())
        {
            try {
                jsonCharacterFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Esta función servirá para construir actualizar el nivel del
     * character en el JSON
     *
     * @param character, variable de tipo Character en cuestión
     * @param gainedXp, experiencia nueva a añadir
     */
    public void updateCharacterLevel(Character character, int gainedXp){

        Character[] currentCharacters;
        FileWriter writer;

        // Abrimos un try & catch para leer el JSON
        try
        {
            // Leemos el JSON y creamos una ArrayList para guardar los Characters
            currentCharacters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);
            ArrayList<Character> characters = new ArrayList<>(Arrays.asList(currentCharacters));

            // Abrimos bucle para buscar el Character que queremos modificar
            for (int i = 0; i < characters.size(); i++) {
                if (Objects.equals(character.getCharacterName(), characters.get(i).getCharacterName()))
                {
                    characters.get(i).setXp(gainedXp + character.getCharacterLevel());
                    i = characters.size();
                }
            }

            // Una vez encontrado lo modificamos en el JSON
            writer = new FileWriter(String.valueOf(characterPath));
            gson.toJson(characters, writer);
            writer.close();
        }
        // Añadimos excepción por si no puede leer el JSON
        catch (IOException ignored) {}

    }

    /**
     * Esta función servirá para borrar un personaje del JSON
     *
     * @param name, nombre del personaje que va a ser borrado
     * @return erased, bool para confirmar si se ha podido eliminar o no
     */
    public boolean deleteCharacterByName(String name)
    {
        Character[] currentCharacters;
        FileWriter writer;

        boolean erased = false;

        // Abrimos un try & catch para leer el JSON
        try
        {
            // Leemos el JSON y creamos una ArrayList para guardar los Characters
            currentCharacters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);
            ArrayList<Character> characters = new ArrayList<>(Arrays.asList(currentCharacters));

            // Abrimos bucle para buscar el Character que queremos eliminar
            for (int i = 0; i < characters.size(); i++) {
                if (Objects.equals(name, characters.get(i).getCharacterName()))
                {
                    characters.remove(i);
                    erased = true;
                    i = characters.size();
                }
            }

            // Una vez encontrado y eliminado, reescribimos el JSON
            writer = new FileWriter(String.valueOf(characterPath));
            gson.toJson(characters, writer);
            writer.close();
        }
        // Añadimos excepción por si no puede leer el JSON
        catch (IOException ignored) {}

        return erased;
    }

    /**
     * Esta función servirá para leer el JSON y sus personajes
     *
     * @return characters, ArrayList con todos los personajes creados en el JSON
     */
    public ArrayList<Character> readCharacterJSON()
    {
        Character[] currentCharacters;
        ArrayList<Character> characters = new ArrayList<>();

        // Abrimos un try & catch para leer el JSON
        try
        {
            currentCharacters = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);

            // Si tiene algun personaje guardado, nos lo guardamos
            if (currentCharacters != null) {
                characters.addAll(Arrays.asList(currentCharacters));
            }
        }
        // Añadimos excepción por si no puede leer el JSON
        catch (FileNotFoundException ignored)
        {

        }

        return characters;
    }

    /**
     * Esta función servirá para guardar un nuevo personaje en el JSON
     *
     * @param character, variable de tipo Character que contendrá el que guardaremos
     * @return saved, bool que confirmará si se ha guardado o no
     */
    public boolean saveCharacter(Character character) {

        FileWriter writer;
        ArrayList<Character> characters = new ArrayList<>();
        Character[] currentCharacter;
        boolean saved = false;

        // Abrimos un try & catch para leer el JSON
        try
        {
            currentCharacter = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(characterPath))), Character[].class);
            writer = new FileWriter(String.valueOf(characterPath));

            // Guardamos lo que ya esté escrito en el JSON
            if (currentCharacter != null) {
                characters.addAll(Arrays.asList(currentCharacter));
            }

            // Añadimos en la ArrayList el nuevo personaje, lo reescribimos y cerramos
            characters.add(character);

            gson.toJson(characters, writer);

            writer.close();

            saved = true;

        }
        // Añadimos excepción por si no puede leer el JSON
        catch(FileNotFoundException ignored)
        {

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return saved;

    }
}
