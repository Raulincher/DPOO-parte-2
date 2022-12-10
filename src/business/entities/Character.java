package business.entities;

public class Character {

    String name, player;
    int xp, body, mind, spirit;

    public Character(String characterName, String playerName, int characterLevel, int body, int mind, int spirit){
        this.name = characterName;
        this.player = playerName;
        this.xp = characterLevel;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
    }

    public String getCharacterName(){return name;}
    public String getPlayerName() {return player;}
    public int getBody() {return body;}
    public int getCharacterLevel() {return xp;}
    public int getMind() {return mind;}
    public int getSpirit() {return spirit;}
}
