package group.project.App;

import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;


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


import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyApplication implements TransactionDelegate, LoginDelegate {

    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    private JFrame frame;
    private JPanel mainPanel;

    private JPanel updatePanel;
    private JPanel insertPanel;
    private JPanel deletePanel;
    private JPanel selectPanel;
    private JPanel aggrGroupByPanel;

    private JPanel havingPanel;

    private JPanel projectionPanel;
    private JPanel joinPanel;

    private JPanel nestedAggPanel;
    private JPanel divisionPanel;

    public MyApplication() {
        dbHandler = new DatabaseConnectionHandler();
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    private void switchScreen(JPanel panel) {
        // Remove main panel from frame
        frame.remove(mainPanel);

        // Add second panel to frame
        frame.add(panel);

        // Repaint frame
        frame.revalidate();
        frame.repaint();
    }

    public void insertPlayer(Player2Model p2, Player4Model p4, Player6Model p6, Player7Model p7) throws SQLException {
        dbHandler.insertPlayer(p2, p4, p6, p7);
    }

    public void deleteNPC(ArrayList<Integer> nidsToDelete, ArrayList<String> namesToDelete) {
        dbHandler.deleteNPC(nidsToDelete, namesToDelete);
    }

    public void updateShop(Integer shopID, Integer ownerID, String status) throws SQLException, Exception {
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
    public ItemModel[] selectInvItem(Integer invID, Integer value) throws InvIDNotFoundException {
        return dbHandler.selectInvItem(invID, value);
    }


    public ShopModel[] getShopInfo() {
        return dbHandler.getShopInfo();
    }

    public InventoryModel[] getInventoryInfo() {
        return dbHandler.getInventoryInfo();
    }

    public String[] fetchTableNames() {
        return dbHandler.fetchTableNames();
    }

    public String[] fetchAttributesFromTable(String tableName) {
        return dbHandler.fetchAttributesFromTable(tableName);
    }

    public ResultSetModel projectionOnTable(String[] selectedAttributes, String tableName) {
        return dbHandler.projectionOnTable(selectedAttributes, tableName);
    }

    public ArrayList<AvgLevelModel> getAvgLevelInGuild() {
        return dbHandler.getAvgLevelInGuild();
    }
    public DivisionModel[] selectDivision(int lvl) {
        return dbHandler.selectDivision(lvl);
    }



    public void login(String username, String password) {
        boolean connected = dbHandler.login(username, password);
        if(connected) {
            loginWindow.dispose();
            frame = new JFrame("My Application");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create main panel
            mainPanel = new JPanel();
            mainPanel.setLayout(new FlowLayout());


            // DEMO UPDATE - REMAKE THIS
//        JButton button = new JButton("Update Shop");
//        UpdatePanel up = new UpdatePanel();
//        updatePanel = up.getUpdatePanel();
//        button.addActionListener(e -> switchScreen(updatePanel));

            JButton insertButton = new JButton("Insert Player");
            JButton deleteButton = new JButton("Delete NPC");
            JButton selectButton = new JButton("Select Quests");
            JButton updateButton = new JButton("Update Shop");
            JButton havingButton = new JButton("Find ranks with most guilds");
            JButton aggrGroupByButton = new JButton("Get Inventory Value"); // Aggregation Group By
            JButton projButton = new JButton("Select attributes from table");
            JButton joinButton = new JButton("Select valuable items from inventory (join)");
            JButton naggButton = new JButton("Get average player level from guilds");
            JButton divisionButton = new JButton("Get All Players Who Completed All Quests (division)");

            DeletePanel dp = new DeletePanel();
            InsertPanel ip = new InsertPanel();
            SelectPanel sp = new SelectPanel();
            UpdatePanel up = new UpdatePanel();

            HavingPanel hp = new HavingPanel();
            AggrGroupByPanel agbp = new AggrGroupByPanel();
            ProjectionPanel pjp = new ProjectionPanel();
            JoinPanel jp = new JoinPanel();
            NestedAggPanel nap = new NestedAggPanel();
            DivisionPanel divp = new DivisionPanel();

            deletePanel = dp.getDeletePanel(this, frame, mainPanel);
            insertPanel = ip.getInsertPanel(this, frame, mainPanel);
            selectPanel = sp.getSelectPanel(this, frame, mainPanel);
            havingPanel = hp.getHavingPanel(this, frame, mainPanel);
            updatePanel = up.getUpdatePanel(this, frame, mainPanel);
            aggrGroupByPanel= agbp.getAggrGroupByPanel(this, frame, mainPanel);
            projectionPanel = pjp.getProjectionPanel(this, frame, mainPanel);
            joinPanel = jp.getJoinPanel(this, frame, mainPanel);
            nestedAggPanel = nap.getNestedAggPanel(this, frame, mainPanel);
            divisionPanel = divp.getDivisionPanel(this, frame, mainPanel);

            insertButton.addActionListener(e -> switchScreen(insertPanel));
            deleteButton.addActionListener(e -> switchScreen(deletePanel));
            selectButton.addActionListener(e -> switchScreen(selectPanel));
            updateButton.addActionListener(e -> switchScreen(updatePanel));
            havingButton.addActionListener(e -> switchScreen(havingPanel));
            aggrGroupByButton.addActionListener(e -> switchScreen(aggrGroupByPanel));
            projButton.addActionListener(e -> switchScreen(projectionPanel));
            joinButton.addActionListener(e -> switchScreen(joinPanel));
            naggButton.addActionListener(e -> switchScreen(nestedAggPanel));
            divisionButton.addActionListener(e -> switchScreen(divisionPanel));


            // Add all Buttons
//        mainPanel.add(button); // replace this
            mainPanel.add(insertButton);
            mainPanel.add(deleteButton);
            mainPanel.add(selectButton);
            mainPanel.add(updateButton);
            mainPanel.add(havingButton);
            mainPanel.add(aggrGroupByButton);
            mainPanel.add(projButton);
            mainPanel.add(joinButton);
            mainPanel.add(naggButton);
            mainPanel.add(divisionButton);
            // Finish
            frame.add(mainPanel);
            frame.setVisible(true);
        } else {
            loginWindow.handleLoginFailed();
            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("Max login attempts reached.");
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        // Create instance of MyApplication
        SwingUtilities.invokeLater(MyApplication::new);
    }
}