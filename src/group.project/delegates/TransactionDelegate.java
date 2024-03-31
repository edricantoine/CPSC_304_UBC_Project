package group.project.delegates;

import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;
import group.project.model.QuestModel;

import java.sql.SQLException;
import java.util.ArrayList;


public interface TransactionDelegate {
    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws SQLException;
    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete);
    public Integer[] getRanksWithMostGuilds();
    public int getTotalInventoryValue(int id);
    QuestModel[] selectQuests(String whereClause);
}
