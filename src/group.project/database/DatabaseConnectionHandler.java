package group.project.database;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


import group.project.App.InvIDNotFoundException;


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

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws Exception {
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

            String tempServerQuery = "SELECT SID FROM Server WHERE SID = (?)";
            PrintablePreparedStatement pst = new PrintablePreparedStatement(connection.prepareStatement(tempServerQuery), tempServerQuery, false);

            pst.setInt(1, p7.getSid());
            ResultSet serverResult = pst.executeQuery();
            if(!serverResult.next()) {
                throw new Exception("Server ID not found.");
            }

            String tempPlayerQuery = "SELECT PNAME, SID FROM PLAYER_7 WHERE PNAME = (?) AND SID = (?)";
            pst = new PrintablePreparedStatement(connection.prepareStatement(tempPlayerQuery), tempPlayerQuery, false);

            pst.setString(1, p7.getPname());
            pst.setInt(2, p7.getSid());

            ResultSet playerResult = pst.executeQuery();
            if(playerResult.next()) {
                throw new Exception("Duplicate player name + server ID combo.");
            }


            String tempQuery = "SELECT WNAME, WID FROM Weapon_3 WHERE WNAME = (?) AND WID = (?)";
            pst = new PrintablePreparedStatement(connection.prepareStatement(tempQuery), tempQuery, false);

            if((p7.getWid() == -1 && !Objects.equals(p7.getWname(), "")) || (p7.getWid() != -1 && Objects.equals(p7.getWname(), ""))) {
                throw new Exception("Weapon name and ID must both be empty or both be non-empty.");
            }

            if(Objects.equals(p7.getWname(), "")) {
                ps2.setNull(3, java.sql.Types.VARCHAR);
                pst.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps2.setString(3, p7.getWname());
                pst.setString(1, p7.getWname());
            }

            if(p7.getWid() == -1) {
                ps2.setNull(4, java.sql.Types.INTEGER);
                pst.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps2.setInt(4, p7.getWid());
                pst.setInt(2, p7.getWid());
            }

            ResultSet wnamewidResult = pst.executeQuery();
            if(!wnamewidResult.next() && p7.getWid() != -1) {
                throw new Exception("Weapon name or ID not found");
            }

            ps2.setInt(5, p7.getExp());

            String tempGuildQuery = "SELECT gname FROM Guild_3 WHERE gname = (?)";
            pst =  new PrintablePreparedStatement(connection.prepareStatement(tempGuildQuery), tempGuildQuery, false);

            if(Objects.equals(p7.getGname(), "")) {
                ps2.setNull(6, java.sql.Types.VARCHAR);
                pst.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps2.setString(6, p7.getGname());
                pst.setString(1, p7.getGname());
            }

            ResultSet gnameResult = pst.executeQuery();
            if(!gnameResult.next() && !Objects.equals(p7.getGname(), "")) {
                throw new Exception("Guild name not found.");
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
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateShop(Integer shopID, Integer ownerID, String status) throws SQLException {
        try {
            String query = "UPDATE Shop SET ";
            String ownerIDpart = "ownerid = (?)";
            String statuspart = "status = (?) ";
            String endpart = "WHERE shopid = (?)";

            // one of these if statements must be true

            if(ownerID != -1) {
                if(!Objects.equals(status, "")) {
                    ownerIDpart = ownerIDpart + ", ";
                } else {
                    ownerIDpart = ownerIDpart + " ";
                }
                query = query + ownerIDpart;
            }

            if(!Objects.equals(status, "")) {
                query = query + statuspart;

            }

            query = query + endpart;


            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            if(ownerID != -1 && !Objects.equals(status, "")) {
                ps.setInt(1, ownerID);
                ps.setString(2, status);
                ps.setInt(3, shopID);
            } else {
                if(ownerID == -1) {
                    ps.setString(1, status);
                    ps.setInt(2, shopID);
                } else {
                    ps.setInt(1, ownerID);
                    ps.setInt(2, shopID);

                }
            }

            int rowCount = ps.executeUpdate();
            System.out.println("Rowcount: " + rowCount);
            if(rowCount == 0) {
                System.out.println(WARNING_TAG + " Shop to be updated does not exist.");
            }
            connection.commit();
            ps.close();
            System.out.println("Success!");

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
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

    public DivisionModel[] selectDivision(int lvl) {
        ArrayList<DivisionModel> result = new ArrayList<>();
        try {
            String queries = "SELECT DISTINCT D.pname AS name, COUNT(DISTINCT D.qname) AS qc FROM Does D INNER JOIN Quest Q1 ON D.qname = Q1.qname "
                    + "WHERE D.PROGRESS = 100 AND Q1.MINLEVEL <= (?) "
                    + "GROUP BY D.pname HAVING COUNT(DISTINCT D.qname) = (SELECT COUNT(Q2.qname) FROM Quest Q2 "
                    + "WHERE Q2.MINLEVEL <= (?))";

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queries), queries, false);
            ps.setInt(1, lvl);
            ps.setInt(2, lvl);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                DivisionModel model = new DivisionModel(rs.getString("name"),
                        rs.getInt("qc"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new DivisionModel[result.size()]);
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

    public ItemModel[] selectInvItem(Integer invID, Integer value) throws InvIDNotFoundException {
        ArrayList<ItemModel> result = new ArrayList<ItemModel>();
        try{
            // Check to see if inventory exists with selected id
            String query1 = "SELECT * FROM Inventory WHERE invid = (?)";
            PrintablePreparedStatement ps1 = new PrintablePreparedStatement(connection.prepareStatement(query1), query1, false);

            ps1.setInt(1, invID);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                throw new InvIDNotFoundException("Inventory ID not found");
            }

            // if inventory exists, proceed
            String query = "SELECT * FROM Inventory JOIN Item ON Inventory.invid = Item.invid "
            + " WHERE Inventory.invid = (?) AND value > (?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ps.setInt(1, invID);
            ps.setInt(2, value);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ItemModel model = new ItemModel(rs.getString("iname"),
                        rs.getInt("iid"),
                        rs.getInt("invid"),
                        rs.getString("questname"),
                        rs.getInt("value"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ItemModel[result.size()]);
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

    public ShopModel[] getShopInfo() {
        ArrayList<ShopModel> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM Shop";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ShopModel model = new ShopModel(rs.getInt("shopid"),
                        rs.getInt("ownerid"),
                        rs.getString("status"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ShopModel[result.size()]);
    }

    public String[] fetchTableNames() {
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "SELECT table_name FROM user_tables"; // Query to fetch table names for the current user
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tableName = rs.getString("table_name");
                result.add(tableName);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new String[result.size()]);
    }

    public String[] fetchAttributesFromTable(String tableName) {
        ArrayList<String> result = new ArrayList<>();
        try {
            // Execute a query to get the column names from the specified table
            String query = "SELECT column_name FROM user_tab_columns WHERE table_name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();

            // Add column names to the list
            while (rs.next()) {
                String columnName = rs.getString("column_name");
                result.add(columnName);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new String[result.size()]);
    }

    public ResultSetModel projectionOnTable(String[] selectedAttributes, String tableName) {

        try {
            String attributesString = String.join(", ", selectedAttributes);
            String selectQuery = "SELECT " + attributesString;
            String fromQuery = " FROM " + tableName;
            String query = selectQuery + fromQuery;

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), query, false);
            ResultSet rs = ps.executeQuery();
            System.out.println("Success!");

            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();
            ArrayList<String> headers = new ArrayList<>();
            for(int i = 1; i <= numCols; i++) {
                headers.add(md.getColumnName(i));
            }
            ArrayList<ArrayList<String>> rows = new ArrayList<>();
            rs.beforeFirst();
            while(rs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                for(int i = 1; i <= numCols; i++) {
                    temp.add(rs.getString(i));
                }
                rows.add(temp);
            }

            ResultSetModel rsm = new ResultSetModel(headers, rows, numCols);

            rs.close();
            ps.close();
            return rsm;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return null;
        }
    }

    public ArrayList<AvgLevelModel> getAvgLevelInGuild() {
        ArrayList<AvgLevelModel> result = new ArrayList<>();
        try {
            String query = """
                    SELECT g3.gname AS ggn, AVG(avg_level)
                        AS avg_guild_level FROM
                                               (SELECT p7.gname AS gn, AVG(p2.lvl) as avg_level
                                                FROM Player_7 p7, Player_2 p2
                                                WHERE p7.EXP = p2.EXP
                                                GROUP BY p7.gname, pname),
                                               Guild_3 g3
                                           WHERE g3.gname = gn
                                           GROUP BY g3.gname""";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String guildName = rs.getString("ggn");
                BigDecimal avgLevel = rs.getBigDecimal("avg_guild_level");
                avgLevel = avgLevel.setScale(2, RoundingMode.CEILING);

                AvgLevelModel alm = new AvgLevelModel(guildName, avgLevel);
                result.add(alm);
            }
            System.out.println("Success!");
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
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
