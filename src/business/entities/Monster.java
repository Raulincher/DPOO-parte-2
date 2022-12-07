package business.entities;

public class Monster {
    String name;
    String challenge;
    int experience;
    int hitPoints;
    int initiative;
    String damageDice;
    String damageType;

    public Monster(String monsterName, String monsterChallenge, int monsterXpDrop, int monsterHitPoints, int monsterInitiative, String monsterDice, String damageType){
        this.name = monsterName;
        this.challenge = monsterChallenge;
        this.experience = monsterXpDrop;
        this.hitPoints = monsterHitPoints;
        this.initiative = monsterInitiative;
        this.damageDice = monsterDice;
        this.damageType = damageType;
    }


    public String getMonsterName() {
        return name;
    }

    public String getMonsterChallenge() {
        return challenge;
    }

    public int getMonsterHitPoints() {
        return experience;
    }

    public int getMonsterInitiative() {
        return hitPoints;
    }

    public int getMonsterXpDrop() {
        return initiative;
    }

    public String getDamageType() {
        return damageType;
    }

    public String getMonsterDice() {
        return damageDice;
    }

}
