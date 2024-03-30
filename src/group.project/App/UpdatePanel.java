package group.project.App;

import javax.swing.*;
import java.awt.*;

public class UpdatePanel {
    public JPanel getUpdatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Add labels and text fields to the panel
        JLabel label1 = new JLabel("Item Name: ");
        JTextField textField1 = new JTextField(20);
        panel.add(label1);
        panel.add(textField1);

        JLabel label2 = new JLabel("Associated Quest (Name): ");
        JTextField textField2 = new JTextField(20);
        panel.add(label2);
        panel.add(textField2);

        JLabel label3 = new JLabel("Value: ");
        JTextField textField3 = new JTextField(20);
        panel.add(label3);
        panel.add(textField3);

        // Add button to the panel for producing the alert
        JButton alertButton = new JButton("Add");
        alertButton.addActionListener(e -> showAlert(panel));
        panel.add(alertButton);

        return panel;
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