package business.entities;

import com.google.gson.annotations.Expose;

import java.util.Random;

public class Character {
    int actualLife, totalLife;
    @Expose
    String name, player;
    @Expose
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
    public Character(String characterName, String playerName, int characterLevel, int body, int mind, int spirit, int actualLife, int totalLife){
        this.name = characterName;
        this.player = playerName;
        this.xp = characterLevel;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.actualLife = actualLife;
        this.totalLife = totalLife;
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


    public int getTotalLife() {
        return totalLife;
    }

    public void setTotalLife(int totalLife) {
        this.totalLife = totalLife;
    }

    public int getActualLife() {
        return actualLife;
    }


    public void setActualLife(int actualLife) {
        this.actualLife = actualLife;
    }

    /**
     * Esta función servirà para calcular la vida inicial
     * de cada personaje
     *
     * @param level, que será el nivel
     * @return life, vida que tendrá el personaje
     */
    public int initialLifeCalculator(int level) {
        int life;
        int body = getBody();

        // Calculamos la vida con la fórmula
        life = (10 + body) * level;

        return life;
    }

    /**
     * Esta función servirá para calcular la iniciativa del
     * personaje
     *
     * @return initiative, iniciativa del personaje
     */
    public int initiative() {
        int initiative;
        int spirit = getSpirit();
        int d12 = diceRollD12();

        // Calculamos la iniciativa del adventuer
        initiative = d12 + spirit;

        return initiative;
    }


    /**
     * Esta función servirá para realizar el ataque Sword Slash
     * del adventurer
     *
     *
     * @return ataque del personaje
     */
    public int attack(){
        int body = getBody();
        int d6 = diceRollD6();
        // Calculamos con la fórmula
        return d6 + body;
    }


    /**
     * Esta función servirá para calcular cuánto se curará
     * el adventurer
     *
     * @return curación del personaje
     */
    public int bandageTime(){
        int mind = getMind();
        int d8 = diceRollD8();
        // Calculamos con la fórmula
        return mind + d8;
    }

    /**
     * Esta función genera un número entre el 1 y el 12 simulando tirar
     * un dado de 12 caras
     *
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD12(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 12
        Random rand = new Random();
        int upperbound = 12;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }


    /**
     * Esta función genera un número entre el 1 y el 6 simulando tirar
     * un dado de 6 caras
     *
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD6(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 7
        Random rand = new Random();
        int upperbound = 6;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }


    /**
     * Esta función genera un número entre el 1 y el 8 simulando tirar
     * un dado de 8 caras
     *
     * La usamos en los hijos de la clase Character
     *
     * @return roll, int que será el número random generado
     */
    public int diceRollD8(){
        int roll;

        // Usaremos la clase Random para sacar el número aleatorio con upperbound de 9
        Random rand = new Random();
        int upperbound = 8;
        roll = rand.nextInt(upperbound) + 1;

        return roll;
    }

}
