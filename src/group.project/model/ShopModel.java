package group.project.model;

public class ShopModel {
    private final int shopid;
    private final int ownerid;
    private final String status;

    public ShopModel(int shopid, int ownerid, String status) {
        this.shopid = shopid;
        this.ownerid = ownerid;
        this.status = status;
    }

    public int getShopid() {
        return shopid;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return "Shop";
    }
}
