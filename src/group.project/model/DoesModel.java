package group.project.model;

public class DoesModel {

    private final String qname;
    private final String pname;
    private final int sid;
    private final int progress;

    public DoesModel(String qname, String pname, int sid, int progress) {
        this.qname = qname;
        this.pname = pname;
        this.sid = sid;
        this.progress = progress;
    }

    public String getPname() {
        return pname;
    }

    public String getQname() {
        return qname;
    }

    public int getSid() {
        return sid;
    }

    public int getProgress() {
        return progress;
    }

    public String getName() {
        return "Does";
    }
}
