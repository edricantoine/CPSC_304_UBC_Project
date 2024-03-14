package group.project.model;

public class ServerModel {
    private final int sid;
    private final String sname;
    private final String region;

    public ServerModel(int sid, String sname, String region) {
        this.sid = sid;
        this.sname = sname;
        this.region = region;
    }

    public int getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }

    public String getRegion() {
        return region;
    }
}
