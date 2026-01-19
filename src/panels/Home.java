package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    private ImageIcon icon;
    private JLabel iconLabel;
    private JButton gotoUserInfo;
    private JButton goToScoreboardButton;

    public Home(MainFrame frame) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        icon = new ImageIcon(new ImageIcon("assets/flappybird.png").getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));
        iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gotoUserInfo = new JButton("Start");
        gotoUserInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        gotoUserInfo.addActionListener(e -> frame.showScreen(PanelIndex.UserInfo));

        goToScoreboardButton = new JButton("Go To Scoreboard");
        goToScoreboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goToScoreboardButton.addActionListener(e -> frame.showScreen(PanelIndex.Scoreboard));

        add(iconLabel);
        add(gotoUserInfo);
        add(goToScoreboardButton);
    }
}
