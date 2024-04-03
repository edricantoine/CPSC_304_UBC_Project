package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.DivisionModel;
import group.project.model.ItemModel;
import group.project.model.Player7Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DivisionPanel {
    private TransactionDelegate delegate = null;
    private JTextField lvlField = null;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;
    public JPanel getDivisionPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new GridLayout(0, 2));

        JLabel lvlLabel = new JLabel("Maximum Quest Level");

        JTextField lvlField = new JTextField(50);

        this.lvlField = lvlField;

        panel.add(lvlLabel);
        panel.add(lvlField);

        JButton doButton = new JButton("Get Players who Completed All Quests");
        doButton.addActionListener(e -> doQuery(panel));
        panel.add(doButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        try {
            Integer lvl = Integer.parseInt(this.lvlField.getText());

            DivisionModel[] players = delegate.selectDivision(lvl);
            displayItems(panel, players, lvl);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Please enter valid integer values for Inventory ID and Value.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayItems(JPanel panel, DivisionModel[] players, int lvl) {

        String[] columnNames = {"Player Name", "Quests Completed < " + lvl};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (DivisionModel player : players) {
            Object[] rowData = {player.getName(), player.getQuestsCompleted()};
            model.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size

        JOptionPane.showMessageDialog(null, scrollPane, "Item Information", JOptionPane.PLAIN_MESSAGE);
    }

    private void switchScreen(JPanel panel, JFrame frame) {
        frame.remove(this.thisPanel);

        frame.add(panel);

        frame.revalidate();
        frame.repaint();
    }
}
