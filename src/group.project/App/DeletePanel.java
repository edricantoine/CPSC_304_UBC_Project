package group.project.App;

import group.project.delegates.TransactionDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DeletePanel {
    private TransactionDelegate delegate = null;
    private JTextField nidsField = null;
    private JTextField nnamesField = null;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;
    public JPanel getDeletePanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new GridLayout(0, 2));

        JLabel nidsLabel = new JLabel("NPC IDs to delete");
        JLabel cLabel = new JLabel("(separate with comma)");
        //JLabel nnamesLabel = new JLabel("NPC names to delete");
        //JLabel cLabel2 = new JLabel("(separate with comma)");
        JLabel blank = new JLabel("");
        //JLabel blank2 = new JLabel("");

        JTextField nidsField = new JTextField(50);
        JTextField nnamesField = new JTextField(50);

        this.nidsField = nidsField;
        this.nnamesField = nnamesField;

        panel.add(nidsLabel);
        panel.add(cLabel);
        panel.add(nidsField);
        panel.add(blank);

        JButton doButton = new JButton("Delete NPCs");
        doButton.addActionListener(e -> doQuery(panel));
        panel.add(doButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        String stringNids = this.nidsField.getText();
        String stringNames = this.nnamesField.getText();
        stringNames = stringNames.replaceAll("[^a-zA-Z0-9],","");
        if(stringNames.length() > 50) {
            JOptionPane.showMessageDialog(panel, "Input is too long (should be <= 50 characters)");
            return;
        }
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> nids = new ArrayList<Integer>();

        if(stringNids.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter values for IDs to delete.");
            return;
        }

        if(!stringNames.isEmpty()) {
            String[] tempNames = stringNames.split(",");
            names.addAll(Arrays.asList(tempNames));
        }

        try {
            if (!stringNids.isEmpty()) {
                String[] splitNids = stringNids.split(",");
                for (String splitNid : splitNids) {
                    nids.add(Integer.parseInt(splitNid));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Only enter integers as NPC IDs.");
            return;
        }
        try {
            delegate.deleteNPC(nids);
            JOptionPane.showMessageDialog(panel,"Success!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, e.getMessage());
        }

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
