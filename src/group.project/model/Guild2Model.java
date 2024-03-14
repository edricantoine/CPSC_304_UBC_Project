package group.project.model;

public class Guild2Model {
    private final int rank;
    private final int goldBonus;

    public Guild2Model(int rank, int goldBonus) {
        this.rank = rank;
        this.goldBonus = goldBonus;
    }

    public int getRank() {
        return rank;
    }

    public int getGoldBonus() {
        return goldBonus;
    }
}
