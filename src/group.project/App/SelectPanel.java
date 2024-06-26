package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.QuestModel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class SelectPanel {
    private TransactionDelegate delegate = null;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;

    public JPanel getSelectPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Create a label for the dropdown
        JLabel label = new JLabel("Select Quests by filter:");

        // Create a dropdown with the specified options
        JComboBox<String> dropdown = new JComboBox<>();
        dropdown.addItem("Quest name = 'Buy a Weapon'");
        dropdown.addItem("Min level >= 5");
        dropdown.addItem("Exp > 200");
        dropdown.addItem("Quest name = 'Buy a Weapon' or 'Buy a Potion'");
        dropdown.addItem("Min level >= 5 and Exp > 200");
        dropdown.addItem("Min level >= 5 or Exp > 200");


        // Set preferred size for dropdown
        dropdown.setPreferredSize(new Dimension(250, 30));

        // Add the label and dropdown to the panel
        panel.add(label);
        panel.add(dropdown);

        // Add the button to the panel
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> doQuery(panel, dropdown));
        panel.add(applyButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel, JComboBox<String> dropdown) {
        // Get the selected item from the dropdown
        String selectedOption = (String) dropdown.getSelectedItem();
        // Determine the integer value and string to send to the backend based on the selected option
        String whereClause;
        switch (Objects.requireNonNull(selectedOption)) {
            case "Quest name = 'Buy a Weapon'":
                whereClause = "qname = 'Buy a Weapon'";
                break;
            case "Min level >= 5":
                whereClause = "minlevel >= 5";
                break;
            case "Exp > 200":
                whereClause = "exp > 200";
                break;
            case "Quest name = 'Buy a Weapon' or 'Buy a Potion'":
                whereClause = "qname = 'Buy a Weapon' OR qname = 'Buy a Potion'";
                break;
            case "Min level >= 5 and Exp > 200":
                whereClause = "minlevel >= 5 AND exp > 200";
                break;
            case "Min level >= 5 or Exp > 200":
                whereClause = "minlevel >= 5 OR exp > 200";
                break;
            default:
                whereClause = "";
                break;
        }
        QuestModel[] quests = delegate.selectQuests(whereClause);
        displayQuests(panel, quests);
    }

    private void displayQuests(JPanel panel, QuestModel[] quests) {
        // Create a JTable to display quest information
        String[] columnNames = {"Quest Name", "Giver ID", "Experience", "Minimum Level", "Objectives"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Populate the table with quest information
        for (QuestModel quest : quests) {
            Object[] rowData = {quest.getQname(), quest.getGiverid(), quest.getExp(), quest.getMinlevel(), quest.getObjectives()};
            model.addRow(rowData);
        }

        // Create JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size

        // Show the table in a dialog
        JOptionPane.showMessageDialog(null, scrollPane, "Quest Information", JOptionPane.PLAIN_MESSAGE);
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
