package group.project.delegates;

import group.project.model.AvgLevelModel;
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
    ArrayList<AvgLevelModel> getAvgLevelInGuild();

    ResultSetModel projectionOnTable(String[] selectedAttributes, String tableName);
}
