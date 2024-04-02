package group.project.controller;

import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;
import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;
import group.project.model.QuestModel;

import java.util.ArrayList;

public class MainController implements TransactionDelegate, LoginDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    public MainController() {
        dbHandler = new DatabaseConnectionHandler();
    }

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) {
        dbHandler.insertPlayer(p2, p4, p6, p7);
    }

    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete) {
        dbHandler.deleteNPC(nidsToDelete, namesToDelete);
    }

    public void updateShop(String shopID, String ownerID, String status) {
        dbHandler.updateShop(shopID, ownerID, status);
    }

    public Integer[] getRanksWithMostGuilds() {
       return dbHandler.getRanksWithMostGuilds();
    }

    public int getTotalInventoryValue(int id) {
        return dbHandler.getTotalInventoryValue(id);
    }

    public QuestModel[] selectQuests(String whereClause) {
        return dbHandler.selectQuests(whereClause);
    }


    public void login(String username, String password) {
        boolean connected = dbHandler.login(username, password);
    }

}