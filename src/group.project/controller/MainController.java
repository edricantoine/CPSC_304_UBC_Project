package group.project.controller;

import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;
import group.project.model.*;

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


    public void login(String username, String password) {
        boolean connected = dbHandler.login(username, password);
    }

}