package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.ItemModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JoinPanel {
    private TransactionDelegate delegate = null;
    private JTextField invIdField = null;
    private JTextField valueField = null;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;
    public JPanel getJoinPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new GridLayout(0, 1));

        JLabel invIdLabel = new JLabel("Inventory to search\n" +
                "(by ID)");
        JLabel valueLabel = new JLabel("Return all items\n" +
                "with value greater than");

        valueLabel.setSize(10, 10);

        JTextField invIdField = new JTextField(50);
        JTextField valueField = new JTextField(50);


        this.invIdField = invIdField;
        this.valueField = valueField;

        panel.add(invIdLabel);
        panel.add(invIdField);
        panel.add(valueLabel);
        panel.add(valueField);

        JButton doButton = new JButton("Get All Items");
        doButton.addActionListener(e -> doQuery(panel));
        panel.add(doButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        try {
            Integer invID = Integer.parseInt(this.invIdField.getText());
            Integer value = Integer.parseInt(this.valueField.getText());

            ItemModel[] items = delegate.selectInvItem(invID, value);
            displayItems(panel, items);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Please enter valid number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvIDNotFoundException e) {
            JOptionPane.showMessageDialog(panel, e.getMessage(), "Inventory ID Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayItems(JPanel panel, ItemModel[] items) {

        String[] columnNames = {"Item ID", "Item Name", " Inventory ID", "Quest Name", "Value"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (ItemModel item : items) {
            Object[] rowData = {item.getIid(), item.getIname(), item.getInvid(), item.getQuestname(), item.getValue()};
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
