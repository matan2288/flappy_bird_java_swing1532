package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.ScoreboardFileDataHandler;
import java.util.LinkedHashMap;

public class Scoreboard extends JPanel {
    private ScoreboardFileDataHandler scoreboardDataObject = new ScoreboardFileDataHandler();
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btn;

    public Scoreboard(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        btn = new JButton("Back to Home");

        // Setup table with initial data
        updateTable();

        btn.addActionListener(e -> {
            frame.showScreen(PanelIndex.Home);
        });

        add(btn);

        // Listen for when panel becomes visible/hidden
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                removeAll();
                updateTable();
                add(btn);
                revalidate();
                repaint();
            }
        });
    }

    private void updateTable() {
        LinkedHashMap<String, String> scoreboardData = scoreboardDataObject
                .turnFileOutputToHashmap(scoreboardDataObject.readFile());

        String[] columnNames = { "Username", "Score" };
        Object[][] tableData = new Object[scoreboardData.size()][2];
        int row = 0;
        for (String username : scoreboardData.keySet()) {
            tableData[row][0] = username;
            tableData[row][1] = scoreboardData.get(username);
            row++;
        }

        table = new JTable(tableData, columnNames);
        table.setEnabled(false);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
        add(scrollPane);
    }
}
