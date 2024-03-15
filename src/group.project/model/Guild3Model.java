package group.project.model;

public class Guild3Model {
    private final String gname;
    private final int rank;

    public Guild3Model(String gname, int rank) {
        this.gname = gname;
        this.rank = rank;
    }

    public String getGname() {
        return gname;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return "Guild3";
    }
}
