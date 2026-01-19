package panels;

import MainFrame.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import GameLogic.User;
import GameLogic.ScoreboardFileDataHandler;

public class YourScore extends JPanel {
    private JLabel nameLabel;
    private JLabel scoreLabel;
    private JPanel namePanel;
    private JPanel scorePanel;
    private JButton continueButton;
    private JButton backHomeButton;
    private ScoreboardFileDataHandler scoreboardDataObject = new ScoreboardFileDataHandler();

    public YourScore(MainFrame frame, User currentUser) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Name panel with "NAME Your score is:" text
        namePanel = new JPanel();
        namePanel.setBackground(new Color(240, 240, 240));
        namePanel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        namePanel.setPreferredSize(new Dimension(250, 50));
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        namePanel.add(nameLabel);
        gbc.gridy = 0;
        add(namePanel, gbc);

        // Score panel with large score display
        scorePanel = new JPanel();
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        scorePanel.setPreferredSize(new Dimension(250, 80));
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        scorePanel.add(scoreLabel);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(scorePanel, gbc);

        // Continue button - goes to Scoreboard
        continueButton = createStyledButton("Scoreboard");
        continueButton.addActionListener(e -> {
            frame.showScreen(PanelIndex.Scoreboard);
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 10, 0);
        add(continueButton, gbc);

        // Back Home button
        backHomeButton = createStyledButton("Back Home");
        backHomeButton.addActionListener(e -> {
            currentUser.resetCurrentUserObject();
            frame.showScreen(PanelIndex.Home);
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 15, 0);
        add(backHomeButton, gbc);

        // Update labels when panel becomes visible
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                scoreboardDataObject.writeFile(currentUser.getUserName(), currentUser.getUserScore(), currentUser.getUserDifficulty());
                nameLabel.setText(currentUser.getUserName() + " Your score is:");
                scoreLabel.setText(String.valueOf(currentUser.getUserScore()));
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
