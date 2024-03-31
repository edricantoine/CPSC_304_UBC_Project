package group.project.App;

import group.project.database.DatabaseConnectionHandler;
import group.project.delegates.LoginDelegate;
import group.project.delegates.TransactionDelegate;
import group.project.model.*;

import javax.swing.*;
import java.awt.*;
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
        if(connected) {
            loginWindow.dispose();
            frame = new JFrame("My Application");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create main panel
            mainPanel = new JPanel();
            mainPanel.setLayout(new FlowLayout());


            // DEMO UPDATE - REMAKE THIS
//        JButton button = new JButton("+ Add Item");
//        UpdatePanel up = new UpdatePanel();
//        updatePanel = up.getUpdatePanel();
//        button.addActionListener(e -> switchScreen(updatePanel));

            JButton insertButton = new JButton("Insert Player");
            JButton deleteButton = new JButton("Delete NPC");
            JButton selectButton = new JButton("Select Quests");

            DeletePanel dp = new DeletePanel();
            InsertPanel ip = new InsertPanel();
            SelectPanel sp = new SelectPanel();
            deletePanel = dp.getDeletePanel(this);
            insertPanel = ip.getInsertPanel(this);
            selectPanel = sp.getSelectPanel(this);

            insertButton.addActionListener(e -> switchScreen(insertPanel));
            deleteButton.addActionListener(e -> switchScreen(deletePanel));
            selectButton.addActionListener(e -> switchScreen(selectPanel));


            // Add all Buttons
//        mainPanel.add(button); // replace this
            mainPanel.add(insertButton);
            mainPanel.add(deleteButton);
            mainPanel.add(selectButton);

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