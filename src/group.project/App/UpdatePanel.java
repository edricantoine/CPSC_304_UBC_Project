package group.project.App;

import group.project.delegates.TransactionDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdatePanel {
    private TransactionDelegate delegate = null;
    private JTextField shopID = null;
    private JTextField ownerID = null;
    private JTextField status = null;
    public JPanel getUpdatePanel(TransactionDelegate delegate) {
        this.delegate = delegate;
        JPanel panel = new JPanel();
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

        return panel;
    }

    private void doQuery(JPanel panel) {
        String strShopID = this.shopID.getText();
        String strOwnerID = this.ownerID.getText();
        String strStatus = this.status.getText();

        delegate.updateShop(strShopID, strOwnerID, strStatus);
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
}