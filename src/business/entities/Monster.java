package business.entities;

public class Monster {
    String monsterName;
    String monsterChallenge;
    int monsterXpDrop;
    int monsterHitPoints;
    int monsterInitiative;
    String monsterDice;
    String damageType;

    public Monster(String monsterName, String monsterChallenge, int monsterXpDrop, int monsterHitPoints, int monsterInitiative, String monsterDice, String damageType){
        this.monsterName = monsterName;
        this.monsterChallenge = monsterChallenge;
        this.monsterXpDrop = monsterXpDrop;
        this.monsterHitPoints = monsterHitPoints;
        this.monsterInitiative = monsterInitiative;
        this.monsterDice = monsterDice;
        this.damageType = damageType;
    }


    public String getMonsterName() {
        return monsterName;
    }

    public String getMonsterChallenge() {
        return monsterChallenge;
    }

    public int getMonsterHitPoints() {
        return monsterHitPoints;
    }

    public int getMonsterInitiative() {
        return monsterInitiative;
    }

    public int getMonsterXpDrop() {
        return monsterXpDrop;
    }

    public String getDamageType() {
        return damageType;
    }

    public String getMonsterDice() {
        return monsterDice;
    }

}
