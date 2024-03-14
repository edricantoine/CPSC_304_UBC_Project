package group.project.model;

public class Weapon3Model {
    private final String wname;
    private final int wid;
    private final String type;
    private final String rarity;

    public Weapon3Model(String wname, int wid, String type, String rarity) {
        this.wname = wname;
        this.wid = wid;
        this.type = type;
        this.rarity = rarity;
    }

    public String getWname() {
        return wname;
    }

    public int getWid() {
        return wid;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }
}
