package group.project.model;

public class Potion2Model {
    private final String type;
    private final String size;
    private final String effect;

    public Potion2Model(String type, String size, String effect) {
        this.type = type;
        this.size = size;
        this.effect = effect;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getEffect() {
        return effect;
    }

    public String getName() {
        return "Potion2";
    }
}
