package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.QuestModel;
import group.project.model.ShopModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UpdatePanel {
    private TransactionDelegate delegate = null;
    private JTextField shopID = null;
    private JTextField ownerID = null;
    private JTextField status = null;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;
    public JPanel getUpdatePanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new GridLayout(0, 2));

        // Add labels and text fields to the panel
        JLabel label1 = new JLabel("Select ShopID to Update: ");
        JTextField textField1 = new JTextField(20);
        this.shopID = textField1;
        panel.add(label1);
        panel.add(textField1);

        JLabel label2 = new JLabel("Update OwnerID to: ");
        JTextField textField2 = new JTextField(20);
        this.ownerID = textField2;
        panel.add(label2);
        panel.add(textField2);

        JLabel label3 = new JLabel("Update Status to: ");
        JTextField textField3 = new JTextField(20);
        this.status = textField3;
        panel.add(label3);
        panel.add(textField3);

        // Add button to the panel for producing the alert
        JButton updateButton = new JButton("Update Shop");
//        alertButton.addActionListener(e -> showAlert(panel));
        updateButton.addActionListener(e -> doQuery(panel));
        panel.add(updateButton);

        // Add button to view shop info
        ShopModel[] shops = delegate.getShopInfo();
        JButton shopButton = new JButton("View Shops");
        shopButton.addActionListener(e -> displayShops(panel, shops));
        panel.add(shopButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        Integer intShopID = -1;
        Integer intOwnerID = -1;

        try {
            intShopID = Integer.parseInt(this.shopID.getText());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid shop ID");
            return;
        }

        if(!Objects.equals(this.ownerID.getText(), "")) {
            try {
                intOwnerID = Integer.parseInt(this.ownerID.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, "Invalid owner ID entered.");
                return;
            }
        }

        String strStatus = this.status.getText();
        strStatus = strStatus.replaceAll("[^a-zA-Z0-9]","");
        if(strStatus.length() > 50) {
            JOptionPane.showMessageDialog(panel, "Input is too long (should be <= 50 characters)");
            return;
        }

        if(Objects.equals(strStatus, "") && intOwnerID == -1) {
            JOptionPane.showMessageDialog(panel, "Please update some column.");
            return;
        }

        if(intShopID == -1) {
            JOptionPane.showMessageDialog(panel, "Please enter a shop ID.");
            return;
        }

        try{
            delegate.updateShop(intShopID, intOwnerID, strStatus);
            JOptionPane.showMessageDialog(panel, "Shop Successfully Updated!");
        } catch (SQLException e){
            if(e.getErrorCode() == 1) {
                String uniqueMsg = "Each shop owner must be unique - please try again.";
                JOptionPane.showMessageDialog(panel, uniqueMsg);
            } else if (e.getErrorCode() == 2291) {
                String uniqueMsg = "Owner ID not found - please try again.";
                JOptionPane.showMessageDialog(panel, uniqueMsg);
            } else {
                String msg = "An error occurred. Exception: " + e.getMessage();
                JOptionPane.showMessageDialog(panel, msg);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
        }
    }

    private void displayShops(JPanel panel, ShopModel[] shops) {
        // Create a JTable to display quest information
        shops = delegate.getShopInfo();
        String[] columnNames = {"Owner ID", "Shop ID", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Populate the table with quest information
        for (ShopModel shop : shops) {
            Object[] rowData = {shop.getOwnerid(), shop.getShopid(), shop.getStatus()};
            model.addRow(rowData);
        }

        // Create JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size

        // Show the table in a dialog
        JOptionPane.showMessageDialog(null, scrollPane, "Shop Information", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAlert(JPanel panel) {
        // Iterate through text fields in the panel and collect their values
        StringBuilder message = new StringBuilder();
        Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                message.append(label.getText()).append(": ");
            } else if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                message.append(textField.getText()).append("\n");
            }
        }
        // Show alert dialog with collected values
        JOptionPane.showMessageDialog(panel, message.toString(), "Item Successfully Added!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void switchScreen(JPanel panel, JFrame frame) {
        // Remove main panel from frame
        frame.remove(this.thisPanel);

        // Add second panel to frame
        frame.add(panel);

        // Repaint frame
        frame.revalidate();
        frame.repaint();
    }
}