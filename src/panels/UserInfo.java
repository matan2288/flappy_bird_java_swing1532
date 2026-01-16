package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;

public class UserInfo extends JPanel {
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton startGame;
    private JButton backHomeButton;

    public UserInfo(MainFrame frame, User currentUser) {
        nameLabel = new JLabel("Enter your name:");
        nameField = new JTextField(15);
        startGame = new JButton("Start Game");
        backHomeButton = new JButton("Back");

        startGame.addActionListener(e -> {
            currentUser.setUserName(nameField.getText());
            frame.showScreen(PanelIndex.Game);
        });

        backHomeButton.addActionListener(e -> frame.showScreen(PanelIndex.Home));

        add(nameLabel);
        add(nameField);
        add(startGame);
        add(backHomeButton);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                nameField.setText("");
            }
        });
    }
}
