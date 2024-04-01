package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.InventoryModel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class AggrGroupByPanel {
    private TransactionDelegate delegate = null;

    public JPanel getAggrGroupByPanel(TransactionDelegate delegate) {
        this.delegate = delegate;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel label = new JLabel("Get Inventory total value");

        // Display Players and Inventory IDs in a table
        JScrollPane scrollPane = displayPlayerAndInventoryID(delegate);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Text field for inventory ID
        JTextField inventoryIdField = new JTextField(10);
        JPanel inventoryIdPanel = new JPanel();
        inventoryIdPanel.add(new JLabel("Inventory ID:"));
        inventoryIdPanel.add(inventoryIdField);

        // Add the label and text field to the panel
        panel.add(label);
        panel.add(inventoryIdPanel);

        // Add the button to the panel
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> doQuery(panel, inventoryIdField.getText()));
        panel.add(applyButton);

        return panel;
    }

    private JScrollPane displayPlayerAndInventoryID(TransactionDelegate delegate) {
        InventoryModel[] inventories = delegate.getInventoryInfo();
        String[] columnNames = {"Player Name", "Inventory ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Populate the table
        for (InventoryModel inventory : inventories) {
            Object[] rowData = {inventory.getPname(), inventory.getInvid()};
            model.addRow(rowData);
        }

        // Create JScrollPane to hold the table
        return new JScrollPane(table);
    }

    private void doQuery(JPanel panel, String inventoryIdText) {
        int inventoryValue = 0;

        try {
            int inventoryId = Integer.parseInt(inventoryIdText);
            inventoryValue = delegate.getInventoryValue(inventoryId);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid inventory ID. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        displayInventoryValue(panel, inventoryValue);
    }

    private void displayInventoryValue(JPanel panel, int inventoryValue) {
        String message = "Inventory Value: " + inventoryValue;
        JOptionPane.showMessageDialog(panel, message, "Inventory Value", JOptionPane.INFORMATION_MESSAGE);
    }

}
