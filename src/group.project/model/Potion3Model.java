package group.project.model;

public class Potion3Model {
    private final String ptname;
    private final int ptid;
    private final int shopid;
    private final String type;
    private final String size;
    private final int price;

    public Potion3Model(String ptname, int ptid, int shopid, String type, String size, int price) {
        this.ptname = ptname;
        this.ptid = ptid;
        this.shopid = shopid;
        this.type = type;
        this.size = size;
        this.price = price;
    }

    public String getPtname() {
        return ptname;
    }

    public int getPtid() {
        return ptid;
    }

    public int getShopid() {
        return shopid;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }
}
