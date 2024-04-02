package group.project.App;

import group.project.delegates.TransactionDelegate;

import javax.swing.*;
import java.util.List;

public class ProjectionPanel {
    private TransactionDelegate delegate = null;
    private String selectedTable = null;

    public JPanel getProjectionPanel(TransactionDelegate delegate) {
        this.delegate = delegate;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Display names of all tables in database
        JLabel dropdownLabel = new JLabel("Choose desired table");
        String[] tableNames = delegate.fetchTableNames();
        JComboBox<String> dropdown = new JComboBox<>(tableNames);
        panel.add(dropdownLabel);
        panel.add(dropdown);

        // Button to get attributes of desired table
        JButton getButton = new JButton("Get Attributes");
        getButton.addActionListener(e -> getAttributes(panel, dropdown));
        panel.add(getButton);


        return panel;
    }

    private void getAttributes(JPanel panel, JComboBox<String> dropdown) {
        String selectedTable = (String) dropdown.getSelectedItem();
        this.selectedTable = selectedTable;
        String[] attributes = delegate.fetchAttributesFromTable(selectedTable);
        createAttributesDropdown(panel, attributes, selectedTable);
    }

    private void createAttributesDropdown(JPanel panel, String[] attributes, String selectedTable) {
        JLabel attributesLabel = new JLabel("Choose attributes from table: " + selectedTable);
        JList<String> attributesList = new JList<>(attributes);
        attributesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(attributesList);

        panel.add(attributesLabel);
        panel.add(scrollPane);

        // Submit Button to do Projection Query
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> doQuery(panel, attributesList));
        panel.add(submitButton);

        panel.revalidate(); // Refresh the layout to reflect changes
        panel.repaint();
    }

    private void doQuery(JPanel panel, JList<String> attributesList) {
        // Get selected attributes
        List<String> selectedAttributes = attributesList.getSelectedValuesList();
        String[] selectedAttributesArray = selectedAttributes.toArray(new String[selectedAttributes.size()]);

        Class<?> result = delegate.projectionOnTable(selectedAttributesArray, selectedTable);
    }

}
