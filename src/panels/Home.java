package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    public Home(MainFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ImageIcon icon = new ImageIcon(
                new ImageIcon("assets/flappybird.png").getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(iconLabel);

        JButton start = new JButton("Start");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        start.addActionListener(e -> frame.showScreen(PanelIndex.UserInfo));

        JButton scoreboard = new JButton("Go To Scoreboard");
        scoreboard.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreboard.addActionListener(e -> frame.showScreen(PanelIndex.Scoreboard));

        add(start);
        add(scoreboard);
    }
}
