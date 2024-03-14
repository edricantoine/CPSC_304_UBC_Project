package group.project.model;

public class ItemModel {
    private final String iname;
    private final int iid;
    private final int invid;
    private final String questname;
    private final int value;

    public ItemModel(String iname, int iid, int invid, String questname, int value) {
        this.iname = iname;
        this.iid = iid;
        this.invid = invid;
        this.questname = questname;
        this.value = value;
    }

    public String getIname() {
        return iname;
    }

    public int getIid() {
        return iid;
    }

    public int getInvid() {
        return invid;
    }

    public String getQuestname() {
        return questname;
    }

    public int getValue() {
        return value;
    }
}
