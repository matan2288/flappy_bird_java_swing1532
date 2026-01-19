package panels;

import MainFrame.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import GameLogic.ScoreboardFileDataHandler;
import java.util.LinkedHashMap;

public class Scoreboard extends JPanel {
    private ScoreboardFileDataHandler scoreboardDataObject = new ScoreboardFileDataHandler();
    private JTable table;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JLabel titleLabel;
    private JPanel contentPanel;

    public Scoreboard(MainFrame frame) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Title
        titleLabel = new JLabel("Scoreboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Content panel for the table
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(contentPanel, gbc);

        // Back button
        backButton = createStyledButton("Back to home");
        backButton.addActionListener(e -> frame.showScreen(PanelIndex.Home));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(backButton, gbc);

        // Setup table with initial data
        updateTable();

        // Listen for when panel becomes visible
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                updateTable();
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

        // Create styled table
        table = new JTable(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Style the table
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(200, 230, 200));
        
        // Style the header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        
        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Create scroll pane with styled border
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 250));
        scrollPane.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Update content panel
        contentPanel.removeAll();
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 35));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
