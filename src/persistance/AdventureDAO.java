package persistance;

import business.entities.Adventure;
import business.entities.Character;
import business.entities.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AdventureDAO {

    private final Gson gson;
    public String adventurePath = "files/adventures.json";
    public AdventureDAO() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        File jsonCharacterFile = new File(String.valueOf(adventurePath));

        if (!jsonCharacterFile.exists())
        {
            try {
                jsonCharacterFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveAdventure(Adventure adventure){
        FileWriter writer;
        ArrayList<Adventure> adventures = new ArrayList<>();
        Adventure[] currentAdventure;
        boolean saved = false;

        try
        {
            currentAdventure = gson.fromJson(gson.newJsonReader(new FileReader(String.valueOf(adventurePath))), Adventure[].class);

            writer = new FileWriter(String.valueOf(adventurePath));
            if (currentAdventure != null) {
                adventures.addAll(Arrays.asList(currentAdventure));
            }
            adventures.add(adventure);

            gson.toJson(adventures, writer);

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
}
