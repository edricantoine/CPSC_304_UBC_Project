package group.project.model;

public class DivisionModel {
    private final String name;
    private final int questsCompleted;

    public DivisionModel(String name, int qc) {
        this.name = name;
        this.questsCompleted = qc;
    }

    public int getQuestsCompleted() {
        return this.questsCompleted;
    }

    public String getName() {
        return this.name;
    }
}
