package group.project.model;

public class QuestItemModel {
    private final String qiname;
    private final int qiid;
    private final String type;
    private final String description;

    public QuestItemModel(String qiname, int qiid, String type, String description) {
        this.qiname = qiname;
        this.qiid = qiid;
        this.type = type;
        this.description = description;
    }

    public String getQiname() {
        return qiname;
    }

    public int getQiid() {
        return qiid;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
