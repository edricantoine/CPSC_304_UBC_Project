package group.project.controller;

import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;
import group.project.model.InventoryModel;
import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;
import group.project.model.QuestModel;
import group.project.model.ResultSetModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// don't need this class actually, can delete later

public class MainController implements TransactionDelegate, LoginDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    public MainController() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws SQLException {
        dbHandler.insertPlayer(p2, p4, p6, p7);
    }

    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete) {
        dbHandler.deleteNPC(nidsToDelete, namesToDelete);
    }

    public void updateShop(Integer shopID, Integer ownerID, String status) throws SQLException {
        dbHandler.updateShop(shopID, ownerID, status);
    }

    public Integer[] getRanksWithMostGuilds() {
       return dbHandler.getRanksWithMostGuilds();
    }

    public int getInventoryValue(int id) {
        return dbHandler.getInventoryValue(id);
    }

    public QuestModel[] selectQuests(String whereClause) {
        return dbHandler.selectQuests(whereClause);
    }

    public InventoryModel[] getInventoryInfo() {
        return dbHandler.getInventoryInfo();
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


    public void login(String username, String password) {
        boolean connected = dbHandler.login(username, password);
    }

}