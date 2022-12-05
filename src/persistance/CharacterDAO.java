package persistance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class CharacterDAO {

    private final String path = "Characters.json";
    private FileReader fr;

    public void CharactersJsonDAO() throws FileNotFoundException {
        URL resource = ClassLoader.getSystemClassLoader().getResource(path);
        fr = new FileReader(resource.getFile());
    }

    public CharacterDAO() {
    }
}
