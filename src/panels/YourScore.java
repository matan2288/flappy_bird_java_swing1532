package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;
import GameLogic.ScoreboardFileDataHandler;

public class YourScore extends JPanel {
    private JLabel yourScoreLabel;
    private JButton backHomeButton;
    private ScoreboardFileDataHandler scoreboardDataObject = new ScoreboardFileDataHandler();

    public YourScore(MainFrame frame, User currentUser) {
        yourScoreLabel = new JLabel();
        backHomeButton = new JButton("Go To Home");

        backHomeButton.addActionListener(e -> {
            scoreboardDataObject.writeFile(currentUser.getUserName(), currentUser.getUserScore());
            currentUser.resetCurrentUserObject();
            frame.showScreen(PanelIndex.Home);
        });

        add(yourScoreLabel);
        add(backHomeButton);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                yourScoreLabel.setText("Hi " + currentUser.getUserName() + "! Your score is: " + currentUser.getUserScore());

            }
        });
    }
}
