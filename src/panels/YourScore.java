package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;

public class YourScore extends JPanel {
    private JLabel yourScoreLabel;

    public YourScore(MainFrame frame, User currentUser) {
        yourScoreLabel = new JLabel();

        JButton btn = new JButton("Go To Home");

        btn.addActionListener(e -> frame.showScreen(PanelIndex.Home));

        add(btn);
        add(yourScoreLabel);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                yourScoreLabel
                        .setText("Hi " + currentUser.getUserName() + "! Your score is: " + currentUser.getUserScore());

            }
        });
    }
}
