package group.project.model;

public class NPCModel {
    private final int nid;
    private final String nname;

    public NPCModel(int nid, String nname) {
        this.nid = nid;
        this.nname = nname;
    }

    public int getNid() {
        return nid;
    }

    public String getNname() {
        return nname;
    }
}
