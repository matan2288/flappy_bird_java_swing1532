package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;
import GameLogic.ScoreboardFileDataHandler;

public class YourScore extends JPanel {
    private JLabel yourScoreLabel;
    private JButton backHomeButton;
    private JButton scoreboardButton;
    private ScoreboardFileDataHandler scoreboardDataObject = new ScoreboardFileDataHandler();

    public YourScore(MainFrame frame, User currentUser) {
        setOpaque(false);
        yourScoreLabel = new JLabel();
        backHomeButton = new JButton("Go To Home");
        scoreboardButton = new JButton("Scoreboard");

        backHomeButton.addActionListener(e -> {
            currentUser.resetCurrentUserObject();
            frame.showScreen(PanelIndex.Home);
        });

        scoreboardButton.addActionListener(e -> {
            frame.showScreen(PanelIndex.Scoreboard);
        });

        add(yourScoreLabel);
        add(backHomeButton);
        add(scoreboardButton);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                scoreboardDataObject.writeFile(currentUser.getUserName(), currentUser.getUserScore());
                yourScoreLabel.setText("Hi " + currentUser.getUserName() + "! Your score is: " + currentUser.getUserScore());

            }
        });
    }
}
