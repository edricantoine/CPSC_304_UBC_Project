package group.project.model;

public class Player7Model {
    private final String pname;
    private final int sid;
    private final String wname;
    private final int wid;
    private final int exp;
    private final String gname;
    private final String role;

    public Player7Model(String pname, int sid, String wname, int wid, int exp, String gname, String role) {
        this.pname = pname;
        this.sid = sid;
        this.wname = wname;
        this.wid = wid;
        this.exp = exp;
        this.gname = gname;
        this.role = role;
    }

    public String getPname() {
        return pname;
    }

    public int getSid() {
        return sid;
    }

    public String getWname() {
        return wname;
    }

    public int getWid() {
        return wid;
    }

    public int getExp() {
        return exp;
    }

    public String getGname() {
        return gname;
    }

    public String getRole() {
        return role;
    }
}
