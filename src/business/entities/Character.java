package business.entities;

public class Character {

    String name, player;
    int xp, body, mind, spirit;

    //Creamos constructor con todos los atributos
    /**
     * Esta función servirá para construir el Character
     *
     * @param characterName, nombre del Character
     * @param playerName, nombre del jugador que lo crea
     * @param characterLevel, nivel del Character
     * @param body, body del Character
     * @param mind, mind del Character
     * @param spirit, spirit del Character
     */
    public Character(String characterName, String playerName, int characterLevel, int body, int mind, int spirit){
        this.name = characterName;
        this.player = playerName;
        this.xp = characterLevel;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
    }

    /**
     * Esta función llamará al nombre del character
     *
     * @return name, que será el nombre del character
     */
    public String getCharacterName(){return name;}

    /**
     * Esta función llamará al nombre del jugador
     *
     * @return player, que será el nombre del jugador
     */
    public String getPlayerName() {return player;}

    /**
     * Esta función llamará al body del character
     *
     * @return body, que será el body del character
     */
    public int getBody() {return body;}

    /**
     * Esta función llamará al nivel del character
     *
     * @return xp, que será el nivel del character
     */
    public int getCharacterLevel() {return xp;}

    /**
     * Esta función llamará a la mind del character
     *
     * @return mind, que será la mind del character
     */
    public int getMind() {return mind;}

    /**
     * Esta función llamará al spirit del character
     *
     * @return spirit, que será el spirit del character
     */
    public int getSpirit() {return spirit;}

    /**
     * Esta función servirá para actualizar la experiencia del character
     *
     * @param xp, que será la nueva experiencia del character
     */
    public void setXp(int xp) {this.xp = xp;}

    /**
     * Esta función servirá para actualizar el body del character
     *
     * @param body, que será el nuevo body del character
     */
    public void setBody(int body) {this.body = body;}

    /**
     * Esta función servirá para actualizar la mind del character
     *
     * @param mind, que será la nueva mind del character
     */
    public void setMind(int mind) {this.mind = mind;}

    /**
     * Esta función servirá para actualizar el spirit del character
     *
     * @param spirit, que será el nuevo spirit del character
     */
    public void setSpirit(int spirit) {this.spirit = spirit;}
}
