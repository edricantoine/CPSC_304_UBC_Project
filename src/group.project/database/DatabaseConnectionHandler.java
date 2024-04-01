package group.project.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import group.project.model.InventoryModel;
import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;
import group.project.model.QuestModel;
import group.project.util.PrintablePreparedStatement;

// CITATION: THIS CODE TAKES HEAVILY FROM THE JAVA/ORACLE SAMPLE PROJECT CODE.

public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) {
        try {
            String query2 = "INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX (Player_2 (exp)) */ INTO Player_2 VALUES (?, ?)";
            PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(query2), query2, false);
            ps2.setInt(1, p2.getExp());
            ps2.setInt(2, p2.getLevel());
            ps2.executeUpdate();
            ps2.close();


            String query4 = "INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX (Player_4 (exp)) */ INTO Player_4 VALUES (?, ?)";
            ps2 = new PrintablePreparedStatement(connection.prepareStatement(query4), query4, false);
            ps2.setInt(1, p4.getExp());
            ps2.setInt(2, p4.getMana());
            ps2.executeUpdate();
            ps2.close();


            String query6 = "INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX (Player_6 (exp)) */ INTO Player_6 VALUES (?, ?)";
            ps2 = new PrintablePreparedStatement(connection.prepareStatement(query6), query6, false);
            ps2.setInt(1, p6.getExp());
            ps2.setInt(2, p6.getHealth());
            ps2.executeUpdate();
            ps2.close();


            String query7 = "INSERT INTO Player_7 VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps2 = new PrintablePreparedStatement(connection.prepareStatement(query7), query7, false);
            ps2.setString(1, p7.getPname());
            ps2.setInt(2, p7.getSid());


            if(Objects.equals(p7.getWname(), "")) {
                ps2.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps2.setString(3, p7.getWname());
            }

            if(p7.getWid() == -1) {
                ps2.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps2.setInt(4, p7.getWid());
            }

            ps2.setInt(5, p7.getExp());
            if(Objects.equals(p7.getGname(), "")) {
                ps2.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps2.setString(6, p7.getGname());
            }

            if(Objects.equals(p7.getRole(), "")) {
                ps2.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps2.setString(7, p7.getRole());
            }

            ps2.executeUpdate();
            connection.commit();
            ps2.close();
            System.out.println("Success!");

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete) {
        try {
            String query = "DELETE FROM NPC WHERE ";
            String queryNidPart = "nid = (?) ";
            String queryNamePart = "nname = (?) ";

            boolean isFirst = true;

            for(int i = 0; i < nidsToDelete.size(); i++) {
                if(isFirst) {
                    isFirst = false;
                    query = query + queryNidPart;
                } else {
                    query = query + "OR " + queryNidPart;
                }
            }

            for(int i = 0; i < namesToDelete.size(); i++) {
                if (isFirst) {
                    isFirst = false;
                    query = query + queryNamePart;
                } else {
                    query = query + "OR " + queryNamePart;
                }
            }

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            int index = 1;

            for(int i = 0; i < nidsToDelete.size(); i++) {
                ps.setInt(index, nidsToDelete.get(i));
                index++;
            }

            for(int i = 0; i < namesToDelete.size(); i++) {
                ps.setString(index, namesToDelete.get(i));
                index++;
            }

            int rowCount = ps.executeUpdate();
            if(rowCount == 0) {
                System.out.println(WARNING_TAG + " A NPC to be deleted does not exist.");
            }
            connection.commit();
            ps.close();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

    }

    public Integer[] getRanksWithMostGuilds() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            String query = "SELECT g.rank as rank FROM Guild_3 g GROUP BY g.rank HAVING COUNT(g.gname) = (SELECT MAX(numGuilds) FROM (SELECT COUNT(g2.gname) AS numGuilds FROM Guild_3 g2 GROUP BY g2.rank))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Integer toAdd = rs.getInt("rank");
                result.add(toAdd);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Integer[result.size()]);
    }

    public int getInventoryValue(int id) {
        int result = 0;
        try {
            String query =
                    "SELECT iname, SUM(value) as item_total_value " +
                    "FROM Inventory JOIN Item ON (Inventory.invid = Item.invid) " +
                    "WHERE Inventory.invid = (?) " +
                    "GROUP BY iname";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int itemValue = rs.getInt("item_total_value");
                result +=  itemValue;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    public QuestModel[] selectQuests(String whereClause) {
        ArrayList<QuestModel> result = new ArrayList<QuestModel>();
        try {
            String query = "SELECT * FROM Quest WHERE ";
            query += whereClause;

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                QuestModel model = new QuestModel(rs.getString("qname"),
                        rs.getInt("giverid"),
                        rs.getInt("exp"),
                        rs.getInt("minlevel"),
                        rs.getString("objectives"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new QuestModel[result.size()]);
    }

    public InventoryModel[] getInventoryInfo() {
        ArrayList<InventoryModel> result = new ArrayList<InventoryModel>();

        try {
            String query = "SELECT * FROM Inventory";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                InventoryModel model = new InventoryModel(rs.getInt("invid"),
                        rs.getString("pname"),
                        rs.getInt("sid"),
                        rs.getInt("sz"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new InventoryModel[result.size()]);
    }


    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if(connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);
            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }

    }
}
