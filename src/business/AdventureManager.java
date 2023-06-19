package business;

import business.entities.Adventure;
import business.entities.Monster;
import business.entities.Character;
import persistance.AdventureDAO;

import java.util.ArrayList;
import java.util.Locale;

public class AdventureManager {

    AdventureDAO adventureDAO;
    CharacterManager characterManager;

    // Creamos constructores
    /**
     * Esta función servirá para construir el AdventureManager
     *
     * @param adventureDAO, lo vincularemos con su respectivo DAO
     * @param characterManager, lo vincularemos con la clase CharacterManager
     */
    public AdventureManager(AdventureDAO adventureDAO, CharacterManager characterManager){
        this.adventureDAO = adventureDAO;
        this.characterManager = characterManager;
    }


    /**
     * Esta función indica si el nombre que se le quiere poner al personaje está ya en
     * uso o está disponible
     *
     * @param name, valor que contendrá el posible nombre del personaje
     * @return exist, bool que dirá si el nombre está disponible o no
     */
    public boolean adventureNameDisponibility(String name){

        boolean exist = false;
        ArrayList<Adventure> adventures;
        adventures = adventureDAO.getAllAdventures();
        int i = 0;

        if(adventures != null){
            // Analizamos con un bucle si el nombre coincide en la ArrayList
            while(i < adventures.size() && !exist){
                if(name.toLowerCase(Locale.ROOT).matches(adventures.get(i).getAdventureName().toLowerCase(Locale.ROOT))){
                    exist = true;
                }
                i++;
            }
        }

        return exist;
    }

    /**
     * Esta función servirá para construir actualizar el número de monsters
     * que habrá en cada encuentro
     *
     * @param monsters, ArrayList con todos los monsters
     * @param encounterMonsters, lista de ArrayLists con los monsters de cada encuentro
     * @param monsterOption, tipo de monstruos a añadir
     * @param lastQuantity, índice con el último añadido
     * @param monsterQuantity, cantidad de monstruos a añadir
     */
    public void setMonstersEncounter(ArrayList<Monster> monsters, ArrayList<ArrayList<Monster>> encounterMonsters, int monsterOption, int lastQuantity ,int monsterQuantity, int auxEncounter){

        int i = 0;

        // Abrimos bucle para añadir hasta el último monster en cuestión
        while(i < monsterQuantity) {
            encounterMonsters.get(auxEncounter).add(lastQuantity + i, monsters.get(monsterOption - 1));
            i++;
        }

    }

    /**
     * Esta función servirá para comprobar que a un encounter se le pueden añadir
     * los suficientes monsters
     *
     * @param encounter, num de encuentro
     * @param encounterMonsters, lista de ArrayLists con los monsters de cada encuentro
     * @param lastQuantity, índice con el último añadido
     * @param monsterQuantity, cantidad de monstruos a añadir
     * @return lastQuantity, int con la última cantidad de encuentros que hay
     */
    public int capacityEnsurance(int encounter, int lastQuantity, int monsterQuantity, ArrayList<ArrayList<Monster>> encounterMonsters){

        // Primero nos aseguramos de que el encuentro esté inicializado
        // En caso contrario lo inicializamos
        if(encounterMonsters.get(encounter).get(0) == null){
            encounterMonsters.set(encounter, new ArrayList<Monster>(monsterQuantity));
            lastQuantity = 0;
        }else{
            // Nos aseguramos de que quepa otro monster
            encounterMonsters.get(encounter).ensureCapacity(lastQuantity);
        }
        return lastQuantity;
    }

    /**
     * Esta función servirá para actualizar nuestra lista de monsters con sus cantidades
     * y sus nombres
     *
     * @param monstersQuantityAndNames, ArrayList con la cantidad de monstruos y sus respectivos nombres
     * @param monsters, ArrayList con todos los monsters
     * @param monsterQuantity, cantidad de monstruos
     * @param monsterOption, tipo de monstruos
     */
    public void setMonstersNames(ArrayList<String> monstersQuantityAndNames, ArrayList<Monster> monsters, int monsterQuantity, int monsterOption){
        int auxSize = monstersQuantityAndNames.size();

        // Nos aseguramos de que hayan monstruos añadidos en el encuentro
        if(monstersQuantityAndNames.size() == 0){
            monstersQuantityAndNames.add(0, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
        }
        else{

            // En caso que sí, iniciamos un bucle a través de todos los monsters con sus nombres
            for(int j = 0; j < monstersQuantityAndNames.size(); j++){
                String auxName = monstersQuantityAndNames.get(j);
                String[] auxNameSplit = auxName.split("\\d+");

                // En caso que el monster no esté añadido, lo añadimos a la ArrayList
                if(!auxNameSplit[0].matches(monsters.get(monsterOption - 1).getMonsterName())){
                    if(auxSize == monstersQuantityAndNames.size() && j == monstersQuantityAndNames.size() - 1){
                        monstersQuantityAndNames.add(j + 1, monsters.get(monsterOption - 1).getMonsterName() + monsterQuantity);
                    }

                }else{
                    // Si el monster ya está en la lista, actualizamos su cantidad y frenamos el bucle
                    auxName = monstersQuantityAndNames.get(j);
                    int auxQuantity = Integer.parseInt(auxName.replaceAll("[^0-9]", ""));
                    if(auxSize == monstersQuantityAndNames.size()){
                        monstersQuantityAndNames.set(j, monsters.get(monsterOption - 1).getMonsterName() + (monsterQuantity + auxQuantity));
                        j = monstersQuantityAndNames.size();
                    }
                }

            }

        }

    }

    /**
     * Esta función servirá para borrar un monster de un encuentro ya establecido
     *
     * @param monstersQuantityAndNames, ArrayList con la cantidad de monstruos y sus respectivos nombres
     * @param encounterMonsters, lista de ArrayLists con los monsters de cada encuentro
     * @param monsterQuantity, cantidad de monstruos a eliminar
     * @param lastQuantity, índice con el último añadido
     * @param monsterDeleteOption, num del monster que eliminaremos
     * @param auxEncounter, int que nos dice el encuentro del que estamos hablando
     * @return removedCounter, cantidad de monstruos eliminados
     */
    public int removeMonsterFromEncounter(ArrayList<ArrayList<Monster>> encounterMonsters, ArrayList<String> monstersQuantityAndNames, int monsterDeleteOption, int lastQuantity, int monsterQuantity, int auxEncounter){
        String monsterToBeErased = monstersQuantityAndNames.get(monsterDeleteOption - 1);
        String[] auxNameSplit = monsterToBeErased.split("\\d+");
        int removedCounter = 0;

        lastQuantity = lastQuantity + monsterQuantity;

        // Iniciamos un bucle a través de todos los monsters de los encuentros
        for(int i = 0; i < lastQuantity; i++){
            String comparedName = encounterMonsters.get(auxEncounter).get(i - removedCounter).getMonsterName();

            // Si el nombre coincide con el monster que queremos eliminar, lo eliminamos
            if(comparedName.equals(auxNameSplit[0])){
                encounterMonsters.get(auxEncounter).remove(i - removedCounter);
                removedCounter++;

                // Si el encuentro queda sin monstruos, lo dejamos en NULL
                if(encounterMonsters.get(auxEncounter).size() == 0){
                    encounterMonsters.get(auxEncounter).add(0,null);

                }
            }
        }
        monstersQuantityAndNames.remove(monsterDeleteOption - 1);
        return removedCounter;
    }

    /**
     * Esta función servirá para recoger todas las adventures
     *
     * @return se devolverá en una ArrayList todas las adventures
     */
    public ArrayList<Adventure> getAdventuresList(){
        return adventureDAO.getAllAdventures();
    }

    /**
     * Esta función servirá para comprobar si ya se ha añadido algún boss
     * en el encounter
     *
     * @param monstersInEncounter, lista de todos los monsters del encuentro
     * @param monstersList, lista con todos los monsters
     * @param option, monster a verificar
     * @return exist, bool para confirmar si se puede añadir otro boss
     */
    public boolean checkMonsterTypeOfEncounter(ArrayList<Monster> monstersInEncounter, ArrayList<Monster> monstersList, int option){

        boolean exist = false;
        int i = 0;
        int bossCounter = 0;

        // Iniciamos un bucle en la lista de monsters del encuentro
        while(i < monstersInEncounter.size()){

            // En caso que algún monster sea Boss, lo añadimos al contador
            if(monstersInEncounter.get(i).getMonsterChallenge().equals("Boss")){
                bossCounter++;
            }
            i++;
        }

        // Si el monster que queremos añadir es un boss, y hay más de 2 bosses en el encuentro
        // activamos bool
        if(monstersList.get(option - 1).getMonsterChallenge().equals("Boss") && bossCounter >= 1){
            exist = true;
        }

        return exist;
    }

    /**
     * Esta función servirá para crear una lista con todas las vidas
     * de los personajes
     *
     * @param characterInParty, lista de todos los personajes en el grupo
     */
    public void setAdventurersLifeList(ArrayList<Character> characterInParty){
        int z = 0;
        // Abrimos un if para comprobar si hay alguna vida establecida
        while(z < characterInParty.size()) {
            characterInParty.get(z).setTotalLife(characterInParty.get(z).initialLifeCalculator(characterManager.revertXpToLevel(characterInParty.get(z).getCharacterLevel())));
            characterInParty.get(z).setActualLife(characterInParty.get(z).getTotalLife());
            z++;
        }
    }

    /**
     * Esta función servirá para contar los monstruos vivos de la batalla
     *
     * @param monsters lista con todas los monstruos en batalla
     * @return counter, contador de monstruos vivos
     */
    public int countAliveMonsters(ArrayList<Monster> monsters){
        int counter = 0;
        for (Monster monster : monsters) {
            if (monster.getActualHitPoints() > 0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Esta función servirá para contar los personajes que estén
     * muertos en ese instante
     *
     * @param characters lista con todos los characters en batalla
     * @return número total de muertos
     */
    public int countDeadCharacters(ArrayList<Character> characters){
        int deadCounter = 0;

        // A través de un bucle revisamos cuáles no tienen vida
        for (Character character : characters) {
            int actualLife = character.getActualLife();
            if (actualLife == 0) {
                deadCounter++;
            }
        }

        return deadCounter;
    }


    /**
     * Esta función servirá para buscar el monster que tenga menos vida
     *
     * @param monsters lista con todas los monstruos en batalla
     * @return índice del monster con menos vida
     */
    public int smallestEnemyLife(ArrayList<Monster> monsters){
        int index = 0;
        int z = 0;
        int smallerMonsterLife = 0;
        int flag = 0;

        // Abrimos bucle a través de la lista de vidas de los monsters
        while(z < monsters.size()){
            int actualLife = monsters.get(z).getActualHitPoints();
            // Iremos comparando entre ellas, en caso de que se detecte una menor actualizaremos índice
            if (actualLife != 0) {
                if (flag == 0) {
                    smallerMonsterLife = actualLife;
                    index = z;
                    flag = 1;
                } else {
                    if (smallerMonsterLife > actualLife) {
                        smallerMonsterLife = actualLife;
                        index = z;
                    }
                }
            }
            z++;
        }

        return index;
    }



    /**
     * Esta función servirá para buscar el personaje que tenga menos vida
     *
     * @param characters lista con todos los characters en batalla
     * @return índice del personaje con menos vida
     */
    public int smallestCharacterLife(ArrayList<Character> characters){
        int z = 0;
        int flag = 0;
        int index = 0;
        int smallerCharacterLife = 0;

        // Abrimos bucle a través de toda la lista de vidas
        while(z < characters.size()){

            int actualLife = characters.get(z).getActualLife();

            // Si el personaje no está muerto iremos comparando vida por vida
            if(actualLife != 0){
                if(flag == 0){
                    smallerCharacterLife = actualLife;
                    index = z;
                    flag = 1;
                }
                // En caso de que la nueva vida de la lista sea menor, actualizamos el índice del personaje
                else{
                    if(smallerCharacterLife > actualLife){
                        smallerCharacterLife = actualLife;
                        index = z;
                    }
                }
            }
            z++;
        }
        return index;
    }

    /**
     * Esta función servirá para crear una lista con todas las prioridades
     * ordenadas según la iniciativa de cada personaje, para saber cuál
     * se moverá primero
     *
     * @param listOfPriorities, lista de todas las prioridades
     */
    public void orderListOfPriorities(ArrayList<String> listOfPriorities){
        int i = 0;
        int z;
        boolean auxFlag;

        // Iniciamos un bucle que recorra todas las prioridades
        while(i < listOfPriorities.size()){
            z = 0;
            String[] auxName;
            String actualName;
            int actualInitiative;
            auxFlag = false;

            //comprobamos si la iniciativa es negativa
            if(listOfPriorities.get(i).contains("-")){
                auxName = listOfPriorities.get(i).split("-");
                actualName = auxName[0];
                String auxInitiative = auxName[1];
                actualInitiative = Integer.parseInt("-" + auxInitiative);
            }else{
                auxName = listOfPriorities.get(i).split("\\d+");
                actualName = auxName[0];

                actualInitiative = Integer.parseInt(listOfPriorities.get(i).replaceAll("[^0-9]", ""));
            }

            // Volvemos a recorrer las prioridades con otro contador para poder compararlas
            while(z < listOfPriorities.size()){
                String[] auxCompareName;
                String compareName;
                int compareInitiative;

                //comprobamos si la iniciativa es negativa
                if(listOfPriorities.get(z).contains("-")){
                    auxCompareName = listOfPriorities.get(z).split("-");
                    compareName = auxCompareName[0];
                    String auxInitiative = auxCompareName[1];
                    compareInitiative = Integer.parseInt("-" + auxInitiative);
                }else{
                    auxCompareName = listOfPriorities.get(z).split("\\d+");
                    compareName = auxCompareName[0];
                    compareInitiative = Integer.parseInt(listOfPriorities.get(z).replaceAll("[^0-9]", ""));
                }

                // Comparamos iniciativas para cambiar el orden
                if(actualInitiative < compareInitiative && i < z){
                    listOfPriorities.set(i, compareName + compareInitiative);
                    listOfPriorities.set(z, actualName + actualInitiative);
                    auxFlag = true;
                    z = listOfPriorities.size();
                }

                z++;
            }
            i++;
            if(auxFlag){
                i = 0;
            }
        }
    }

    /**
     * Esta función servirá para crear una lista con todas las prioridades
     * según la iniciativa de cada personaje y monster sin ordenar
     *
     * @param characterInParty, lista de personajes en el grupo
     * @param characterQuantity, int con el num de personajes
     * @param monsterQuantity, int con el num de monsters
     * @param monstersInEncounter lista con los monsters del encuentro
     * @return listOfPriorities, lista de prioridades sin ordenar
     */
    public ArrayList<String> listOfPriorities(int characterQuantity, int monsterQuantity, ArrayList<Character> characterInParty, ArrayList<Monster> monstersInEncounter){
        ArrayList<String> listOfPriorities = new ArrayList<>(0);
        int i = 0;
        int z = 0;
        int characterInitiative;
        int diceroll;

        // Iniciamos un bucle que dure la cantidad de personajes y monsters combinada
        while(i < characterQuantity + monsterQuantity){

            // Recogemos todas las iniciativas de los personajes
            if(i < characterInParty.size()){
                characterInitiative = characterInParty.get(i).initiative();
                listOfPriorities.add(z, characterInParty.get(i).getCharacterName() + characterInitiative);
                z++;
            }

            // Recogemos todas las iniciativas de los monsters
            if(i < monstersInEncounter.size()){
                diceroll = characterManager.diceRollD12();
                listOfPriorities.add(z,monstersInEncounter.get(i).getMonsterName() + (monstersInEncounter.get(i).getMonsterInitiative() * diceroll));
                z++;
            }
            i++;
        }

        return listOfPriorities;
    }

    /**
     * Esta función servirá para crear una lista con todos los monsters
     * que se encontrarán en toda la aventura, será una lista de listas
     *
     * @param encounterMonsters, lista de todos los monsters del encuentro
     * @param adventureEncounters, int con todos los encuentros de la adventure
     * @return encounterMonsters, ArrayList en cuestión
     */
    public ArrayList<ArrayList<Monster>> initializeEncounters(ArrayList<ArrayList<Monster>> encounterMonsters, int adventureEncounters){

        // Iniciamos bucle que recorra todos los encuentros y avamos añadiendo las ArrayLists
        for (int i = 0; i < adventureEncounters; i++) {
            encounterMonsters.add(i, new ArrayList<Monster>(1));
            encounterMonsters.get(i).add(0,null);
        }
        return encounterMonsters;
    }


    /**
     * Esta función realiza los cálculos de daño de los diferentes PJs
     *
     * @param character, personaje que ataca
     *
     * @return damage, daño bruto calculado
     */
    public int calculateDamage(Character character){
        int damage;

        //función de la clase padre Character
        damage = character.attack();

        return damage;
    }

    /**
     * Esta función permite determinar si un ataque ha fallado
     *
     * @param isCrit, indice que indica cuando un ataque es critico, normal o fallo
     *
     * @return boolean que indica el fallo
     */
    public boolean failedAttack(int isCrit){
        return isCrit != 1 && isCrit != 2;
    }


    /**
     * Esta función servirá para tratar el daño que recibirán los personajes, a excepción del mago
     *
     * @param actualLife, vida actual del mago
     * @param isCrit, indice que indica si el golpe es critico
     * @param damage, indicador del daño que se recibe
     *
     * @return total, int que devolverá el total de vida restante del personaje
     */
    public int applyDamage(int isCrit, int actualLife, int damage ){
        int total = 0;

        if(isCrit == 2){
            total = actualLife - (damage*2);
            if(total < 0){
                total = 0;
            }
        }else if(isCrit == 1){
            total = actualLife - (damage);
            if(total < 0){
                total = 0;
            }
        }

        return total;
    }


    /**
     * Esta función servirá para sumar toda la exp que sueltan los monstruos al vencer en un encuentro
     *
     * @param monstersInEncounter, lista con lso monstruos dentro del encuentro
     *
     * @return xpSum, int. que representa la suma total de toda la exp
     */
    public int sumAllMonsterXp(ArrayList<Monster> monstersInEncounter){
        int xpSum = 0, i = 0;
        while(i < monstersInEncounter.size()){
            xpSum = xpSum + monstersInEncounter.get(i).getMonsterXpDrop();
            i++;
        }
        return xpSum;
    }


    /**
     * Esta función servirá para que los PJ usen las habilidades en la fase de descanso
     *
     * @param character, PJ que usará la habilidad
     *
     * @return curation, número que nos dirá la cantidad de curación realizada por el PJ. En el caso de que su habilidad trate de eso
     */
    public int applyAbilitiesRestPhase(Character character){

        int curation = 0;
        int total;
        //efectuamos habilidad
        curation = character.bandageTime();
        total = character.getActualLife() + curation;
        if(total >= character.getTotalLife()){
            total = character.getTotalLife();
        }
        character.setActualLife(total);

        return curation;
    }

    /**
     * Esta función servirá para contar cuantos monsters habrán
     * en un mismo encuentro
     *
     * @param storedName, lista con los nombres de monsters almacenados
     * @param monstersInEncounter, lista de todos los monsters del encuentro
     */
    public void countSameMonstersInEncounter(ArrayList<String> storedName, ArrayList<Monster> monstersInEncounter){

        int i = 0;
        int j = 0;
        int z = 0;
        boolean repeated = true;

        // Iniciamos bucle a través de todos los monster
        while(i < monstersInEncounter.size()){
            z = 0;

            // En la primera oleada guardamos el primer nombre
            if(i == 0){
                storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                j++;
            }else{

                // En las demás, en un bucle comparamos los otros monsters con los que guardemos
                while(z < storedName.size()){
                    repeated = storedName.get(z).equals(monstersInEncounter.get(i).getMonsterName());
                    if(repeated){
                        z = storedName.size();
                    }else{
                        z++;
                    }
                }
                // Si no está repetido guardamos nombre
                if(!repeated){
                    storedName.add(j, monstersInEncounter.get(i).getMonsterName());
                    j++;
                }
            }

            i++;
        }
    }

    /**
     * Esta función servirá para crear una adventure
     *
     * @param adventureName, nombre deseado de la aventura
     * @param encounters, número de encuentros deseados
     * @param monsters, lista de lista de todos los monsters que habrán
     * @return se guardará la aventura a través del DAO
     */
    public boolean createAdventure(String adventureName, int encounters, ArrayList<ArrayList<Monster>> monsters){
        return adventureDAO.saveAdventure(new Adventure(adventureName, encounters, monsters));
    }
}
