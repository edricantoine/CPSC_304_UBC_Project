package group.project.delegates;

import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;

public interface TransactionDelegate {
    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7);
    public void deleteNPC(int nidToDelete, String nameToDelete);
}
