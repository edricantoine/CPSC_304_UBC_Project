package group.project.controller;

import group.project.App.InvIDNotFoundException;
import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;
import group.project.model.AvgLevelModel;
import group.project.model.DivisionModel;
import group.project.model.InventoryModel;
import group.project.model.ItemModel;
import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;
import group.project.model.QuestModel;
import group.project.model.ResultSetModel;
import group.project.model.ShopModel;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// don't need this class actually, can delete later

public class MainController implements TransactionDelegate, LoginDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    public MainController() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws Exception {
        dbHandler.insertPlayer(p2, p4, p6, p7);
    }

    public void deleteNPC(ArrayList<Integer> nidsToDelete) throws Exception {
        dbHandler.deleteNPC(nidsToDelete);
    }

    public void updateShop(Integer shopID, Integer ownerID, String status) throws SQLException {
        dbHandler.updateShop(shopID, ownerID, status);
    }

    public Integer[] getRanksWithMostGuilds() {
       return dbHandler.getRanksWithMostGuilds();
    }

    public int getInventoryValue(int id) throws InvIDNotFoundException {
        return dbHandler.getInventoryValue(id);
    }

    public QuestModel[] selectQuests(String whereClause) {
        return dbHandler.selectQuests(whereClause);
    }
    public ShopModel[] getShopInfo() {
        return dbHandler.getShopInfo();
    }

    public ItemModel[] selectInvItem(Integer invID, Integer value) throws InvIDNotFoundException {
        return dbHandler.selectInvItem(invID, value);
    }

    public InventoryModel[] getInventoryInfo() {
        return dbHandler.getInventoryInfo();
    }

    public DivisionModel[] selectDivision(int lvl) {
        return dbHandler.selectDivision(lvl);
    }

    @Override
    public String[] fetchTableNames() {
        return dbHandler.fetchTableNames();
    }

    @Override
    public String[] fetchAttributesFromTable(String tableName) {
        return dbHandler.fetchAttributesFromTable(tableName);
    }

    @Override
    public ResultSetModel projectionOnTable(String[] selectedAttributes, String tableName) {
        return dbHandler.projectionOnTable(selectedAttributes, tableName);
    }

    @Override
    public ArrayList<AvgLevelModel> getAvgLevelInGuild() {
        return null;
    }

    public void login(String username, String password) {
        boolean connected = dbHandler.login(username, password);
    }

}