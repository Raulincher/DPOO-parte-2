package business.entities;

public class Character {

    String characterName, playerName, characterClass;
    int characterLevel, body, mind, spirit;

    public Character(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, String characterClass){
        this.characterName = characterName;
        this.playerName = playerName;
        this.characterLevel = characterLevel;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.characterClass = characterClass;
    }

    public String getCharacterName(){return characterName;}
    public String getPlayerName() {return playerName;}
    public int getBody() {return body;}
    public int getCharacterLevel() {return characterLevel;}
    public int getMind() {return mind;}
    public int getSpirit() {return spirit;}
    public String getCharacterClass() {return characterClass;}
}
