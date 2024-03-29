package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.Player2Model;
import group.project.model.Player4Model;
import group.project.model.Player6Model;
import group.project.model.Player7Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class InsertPanel {
    private ArrayList<JTextField> fields;
    private TransactionDelegate delegate = null;
    public JPanel getInsertPanel(TransactionDelegate delegate) {
        this.delegate = delegate;
        JPanel panel = new JPanel();
        fields = new ArrayList<>();
        panel.setLayout(new GridLayout(0, 2));

        // Add labels and text fields to the panel

        // We can re-use this
        String[][] setup = {
                {"Player Name *", ""},
                {"Server ID *", "1"},
                {"Weapon ID", ""},
                {"Weapon Name", ""},
                {"Level *", "1"},
                {"EXP *", "0"},
                {"Health *", "50"},
                {"Mana *", "40"},
                {"Guild Name", ""},
                {"Role", ""}
        };

        for(int i = 0; i < setup.length; i++) {
            JLabel label1 = new JLabel(setup[i][0]);
            JTextField textField1 = new JTextField(20);
            textField1.setText(setup[i][1]);
            panel.add(label1);
            panel.add(textField1);
            fields.add(textField1);
        }

        // Add button to the panel for producing the alert
        JButton alertButton = new JButton("Add");
        alertButton.addActionListener(e -> doQuery(panel));
        panel.add(alertButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        // check for invalid values

        if(Objects.equals(fields.get(0).getText(), "") || Objects.equals(fields.get(1).getText(), "")
        || Objects.equals(fields.get(4).getText(), "") || Objects.equals(fields.get(5).getText(), "")
                || Objects.equals(fields.get(6).getText(), "") || Objects.equals(fields.get(7).getText(), "")) {
            // throw some error here?
            JOptionPane.showMessageDialog(panel, "Error: please fill in all required fields.");
            return;
        }

        String pname = fields.get(0).getText();
        int sid = Integer.parseInt(fields.get(1).getText());
        String wname = fields.get(3).getText();

        int wid;
        if(Objects.equals(fields.get(2).getText(), "")) {
            wid = -1;
        } else {
            wid = Integer.parseInt(fields.get(2).getText());
        }
        int exp = Integer.parseInt(fields.get(5).getText());
        String gname = fields.get(8).getText();
        String role = fields.get(9).getText();
        int lvl = Integer.parseInt(fields.get(4).getText());
        int mana = Integer.parseInt(fields.get(7).getText());
        int health = Integer.parseInt(fields.get(6).getText());

        Player7Model p7 = new Player7Model(pname, sid, wname, wid, exp, gname, role);
        Player6Model p6 = new Player6Model(exp, health);
        Player4Model p4 = new Player4Model(exp, mana);
        Player2Model p2 = new Player2Model(exp, lvl);
        delegate.insertPlayer(p2, p4, p6, p7);
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