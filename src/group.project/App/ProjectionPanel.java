package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.ResultSetModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ProjectionPanel {
    private TransactionDelegate delegate = null;
    private String selectedTable = null;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;

    private JScrollPane currScrollPane;
    private JButton currSubmitButton;
    private JLabel currAttrLabel;

    public JPanel getProjectionPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
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

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void getAttributes(JPanel panel, JComboBox<String> dropdown) {
        String selectedTable = (String) dropdown.getSelectedItem();
        this.selectedTable = selectedTable;
        String[] attributes = delegate.fetchAttributesFromTable(selectedTable);
        createAttributesDropdown(panel, attributes, selectedTable);
    }

    private void createAttributesDropdown(JPanel panel, String[] attributes, String selectedTable) {
        if(currScrollPane != null) {
            panel.remove(currScrollPane);
        }
        if(currAttrLabel != null) {
            panel.remove(currAttrLabel);
        }
        if(currSubmitButton != null) {
            panel.remove(currSubmitButton);
        }

        JLabel attributesLabel = new JLabel("Choose attributes from table: " + selectedTable);
        JList<String> attributesList = new JList<>(attributes);
        attributesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(attributesList);
        currScrollPane = scrollPane;
        currAttrLabel = attributesLabel;
        panel.add(attributesLabel);
        panel.add(scrollPane);

        // Submit Button to do Projection Query
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> doQuery(panel, attributesList));
        this.currSubmitButton = submitButton;
        panel.add(submitButton);

        panel.revalidate(); // Refresh the layout to reflect changes
        panel.repaint();
    }

    private void doQuery(JPanel panel, JList<String> attributesList) {
        // Get selected attributes
        List<String> selectedAttributes = attributesList.getSelectedValuesList();
        String[] selectedAttributesArray = selectedAttributes.toArray(new String[selectedAttributes.size()]);

        ResultSetModel result = delegate.projectionOnTable(selectedAttributesArray, selectedTable);
        if(result == null) {
            JOptionPane.showMessageDialog(panel, "The query failed. Please try again.");
            return;
        } else {
            displayAttrs(panel, result.headers, result.rows, result.numCols, 0);
        }
    }

    private void displayAttrs(JPanel panel, ArrayList<String> headers, ArrayList<ArrayList<String>> rows, int numCols, int numRows) {
        String[] headerNames = new String[numCols];
        for(int i = 0; i < headers.size(); i++) {
            headerNames[i] = headers.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(headerNames, 0);
        JTable table = new JTable(model);

        for(int i = 0; i < rows.size(); i++) {
            String[] data = new String[rows.get(i).size()];
            for(int j = 0; j < rows.get(i).size(); j++) {
                data[j] = rows.get(i).get(j);
            }
            model.addRow(data);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(null, sp, "Selected Attributes", JOptionPane.PLAIN_MESSAGE);
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
