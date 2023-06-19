package persistance;

import business.entities.Adventure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AdventureDAO {

    // Añadimos atributos y json
    private final Gson gson;
    public String adventurePath = "files/adventures.json";

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir AdventureDAO
     * No tendrá ni param ni return
     */
    public AdventureDAO() {

        // Creamos la variable gson y leemos el JSON en cuestión
        gson = new GsonBuilder().setPrettyPrinting().create();

        File jsonCharacterFile = new File(String.valueOf(adventurePath));

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
     * Esta función servirá para guardar la aventura en el json
     * una vez esté creada
     *
     * @param adventure, atributo de tipo Adventure que guardaremos
     * @return saved, bool para confirmar si se ha podido guardar o no
     */
    public boolean saveAdventure(Adventure adventure){
        FileWriter writer;
        ArrayList<Adventure> adventures = new ArrayList<>();
        Adventure[] currentAdventure;
        boolean saved = false;

        // A través de un try & catch trataremos de guardar la aventura en el json
        try
        {
            // Leeremos el Json en cuestión
            currentAdventure = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(adventurePath))), Adventure[].class);

            // Escribimos la aventura en este
            writer = new FileWriter(String.valueOf(adventurePath));
            if (currentAdventure != null) {
                // Añadimos en la lista todas las aventuras ya creadas
                adventures.addAll(Arrays.asList(currentAdventure));
            }

            // Añadimos la nueva aventura en cuestión, guardamos y cerramos
            adventures.add(adventure);

            gson.toJson(adventures, writer);

            writer.close();

            saved = true;

        }
        // En caso negativo mandaremos excepción
        catch(FileNotFoundException ignored)
        {

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return saved;
    }

    /**
     * Esta función servirá para coger todas las aventuras que ya
     * hayan sido creadas y estén guardadas en el JSON
     *
     * @return adventures, ArrayList con todas las aventuras creadas
     */
    public ArrayList<Adventure> getAllAdventures(){
        Adventure[] currentAdventure;
        ArrayList<Adventure> adventures = new ArrayList<>();

        // Abrimos un try & catch para leer el JSON
        try
        {
            // Leemos nuestro JSON
            currentAdventure = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(adventurePath))), Adventure[].class);

            if (currentAdventure != null) {
                // Añadimos lo creado al nuevo ArrayList
                adventures.addAll(Arrays.asList(currentAdventure));
            }else{
                // En caso de que no haya ninguna creada devolveremos un NULL
                adventures = null;
            }
        }

        // Añadiremos excepción en caso de que no lo lea bien
        catch (FileNotFoundException ignored)
        {

        }

        return adventures;
    }
}
