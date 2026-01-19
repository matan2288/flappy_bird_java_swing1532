package panels;

import MainFrame.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Home extends JPanel {
    private ImageIcon icon;
    private JLabel iconLabel;
    private JLabel welcomeLabel;
    private JButton gotoUserInfo;
    private JButton goToScoreboardButton;

    public Home(MainFrame frame) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Welcome title
        welcomeLabel = new JLabel("Welcome");
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
