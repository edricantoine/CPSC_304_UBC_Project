package group.project.delegates;

import group.project.model.*;

import java.util.ArrayList;


public interface TransactionDelegate {
    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7);
    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete);
    public Integer[] getRanksWithMostGuilds();
    public int getInventoryValue(int id);
    QuestModel[] selectQuests(String whereClause);

    public InventoryModel[] getInventoryInfo();

}
