package group.project.model;

public class QuestModel {
    private final String qname;
    private final int giverid;
    private final int exp;
    private final int minlevel;
    private final String objectives;

    public QuestModel(String qname, int giverid, int exp, int minlevel, String objectives) {
        this.qname = qname;
        this.giverid = giverid;
        this.exp = exp;
        this.minlevel = minlevel;
        this.objectives = objectives;
    }

    public String getQname() {
        return qname;
    }

    public int getGiverid() {
        return giverid;
    }

    public int getExp() {
        return exp;
    }

    public int getMinlevel() {
        return minlevel;
    }

    public String getObjectives() {
        return objectives;
    }
}
