import javax.swing.*;
import java.awt.*;

public class InsertPanel {
    public JPanel getInsertPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Add labels and text fields to the panel

        // We can re-use this
        String[][] setup = {
                {"Player Name", ""},
                {"Server ID", "1"},
                {"Weapon ID", ""},
                {"Weapon Name", ""},
                {"Level", "1"},
                {"EXP", "0"},
                {"Health", "50"},
                {"Mana", "40"},
                {"Guild Name", ""},
                {"Role", ""}
        };

        for(int i = 0; i < setup.length; i++) {
            JLabel label1 = new JLabel(setup[i][0]);
            JTextField textField1 = new JTextField(20);
            textField1.setText(setup[i][1]);
            panel.add(label1);
            panel.add(textField1);
        }

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
                System.out.println(textField.getText());
            }
        }
        // Show alert dialog with collected values
        JOptionPane.showMessageDialog(panel, message.toString(), "Item Successfully Added!",
                JOptionPane.INFORMATION_MESSAGE);
    }
}