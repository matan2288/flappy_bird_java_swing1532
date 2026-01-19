package panels;

import MainFrame.*;
import GameLogic.User;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Home extends JPanel {
    private ImageIcon icon;
    private JLabel iconLabel;
    private JLabel welcomeLabel;
    private JButton gotoUserInfo;
    private JButton goToScoreboardButton;
    private JToggleButton musicToggle;

    public Home(MainFrame frame) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Welcome title
        welcomeLabel = new JLabel("Welcome to Flappy Bird!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        // Icon in a circular border
        icon = new ImageIcon(new ImageIcon("assets/flappybird.png").getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH));
        iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(80, 80));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(iconLabel, gbc);

        // Start button with rounded style
        gotoUserInfo = createStyledButton("Start");
        gotoUserInfo.addActionListener(e -> frame.showScreen(PanelIndex.UserInfo));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(gotoUserInfo, gbc);

        // Scoreboard button with rounded style
        goToScoreboardButton = createStyledButton("Scoreboard");
        goToScoreboardButton.addActionListener(e -> frame.showScreen(PanelIndex.Scoreboard));
        gbc.gridy = 3;
        add(goToScoreboardButton, gbc);

        // Music toggle button
        musicToggle = createMusicToggle();
        gbc.gridy = 4;
        gbc.insets = new Insets(25, 0, 10, 0);
        add(musicToggle, gbc);
    }

    private JToggleButton createMusicToggle() {
        JToggleButton toggle = new JToggleButton(User.isMusicEnabled() ? "🔊 Music ON" : "🔇 Music OFF");
        toggle.setSelected(User.isMusicEnabled());
        toggle.setPreferredSize(new Dimension(140, 40));
        toggle.setFont(new Font("SansSerif", Font.BOLD, 14));
        toggle.setFocusPainted(false);
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Style based on state
        updateToggleStyle(toggle);
        
        toggle.addActionListener(e -> {
            User.setMusicEnabled(toggle.isSelected());
            toggle.setText(toggle.isSelected() ? "🔊 Music ON" : "🔇 Music OFF");
            updateToggleStyle(toggle);
        });
        
        return toggle;
    }

    private void updateToggleStyle(JToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setBackground(new Color(76, 175, 80)); // Green when ON
            toggle.setForeground(Color.WHITE);
            toggle.setBorder(new LineBorder(new Color(56, 142, 60), 2, true));
        } else {
            toggle.setBackground(new Color(158, 158, 158)); // Gray when OFF
            toggle.setForeground(Color.WHITE);
            toggle.setBorder(new LineBorder(new Color(117, 117, 117), 2, true));
        }
        toggle.setOpaque(true);
        toggle.setContentAreaFilled(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
