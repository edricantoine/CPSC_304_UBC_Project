package group.project.model;

public class InventoryModel {
    private final int invid;
    private final String pname;
    private final int sid;
    private final int size;

    public InventoryModel(int invid, String pname, int sid, int size) {
        this.invid = invid;
        this.pname = pname;
        this.sid = sid;
        this.size = size;
    }

    public int getInvid() {
        return invid;
    }

    public String getPname() {
        return pname;
    }

    public int getSid() {
        return sid;
    }

    public int getSize() {
        return size;
    }
}
