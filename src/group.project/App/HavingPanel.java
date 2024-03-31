package group.project.App;

import group.project.delegates.TransactionDelegate;

import javax.swing.*;

public class HavingPanel {
    private TransactionDelegate delegate = null;
    private JFrame frame;
    private JPanel mainPanel;

    private JPanel thisPanel;

    public JPanel getHavingPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.frame = frame;
        this.delegate = delegate;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        JButton havingButton = new JButton("Get ranks with most guilds");
        JButton backButton = new JButton("Back");
        havingButton.addActionListener(e -> getInfo(panel));
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(havingButton);
        panel.add(backButton);
        return panel;
    }

    public void getInfo(JPanel panel) {
        Integer[] ranksWithMostGuilds = delegate.getRanksWithMostGuilds();
        String msg = "The ranks with most guilds are: ";
        for (int i = 0; i < ranksWithMostGuilds.length; i++) {
            msg += "Rank " + ranksWithMostGuilds[i].toString();
            if(i < ranksWithMostGuilds.length - 1) {
                msg += ", ";
            }
        }
        JOptionPane.showMessageDialog(panel, msg);
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
