package group.project.model;

public class Player6Model {
    private final int exp;
    private final int health;

    public Player6Model(int exp, int health) {
        this.exp = exp;
        this.health = health;
    }

    public int getExp() {
        return exp;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return "Player6";
    }
}
