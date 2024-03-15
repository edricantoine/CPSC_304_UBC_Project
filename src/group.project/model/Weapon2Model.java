package group.project.model;

public class Weapon2Model {
    private final String type;
    private final String rarity;
    private final int damage;

    public Weapon2Model(String type, String rarity, int damage) {
        this.type = type;
        this.rarity = rarity;
        this.damage = damage;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return "Weapon2";
    }
}
