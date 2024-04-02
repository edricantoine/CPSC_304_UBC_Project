package group.project.delegates;

import group.project.App.InvIDNotFoundException;
import group.project.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// CITATION: THIS CODE TAKES HEAVILY FROM THE JAVA/ORACLE SAMPLE PROJECT CODE.

public interface TransactionDelegate {
    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws SQLException;
    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete);
    public Integer[] getRanksWithMostGuilds();
    public int getInventoryValue(int id);
    QuestModel[] selectQuests(String whereClause);
    public InventoryModel[] getInventoryInfo();
    String[] fetchTableNames();
    String[] fetchAttributesFromTable(String tableName);
    ItemModel[] selectInvItem(Integer invID, Integer value) throws InvIDNotFoundException;

    ResultSetModel projectionOnTable(String[] selectedAttributes, String tableName);
}
