package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;

public class UserInfo extends JPanel {
    JLabel nameLabel;
    JTextField nameField;

    public UserInfo(MainFrame frame, User currentUser) {
        nameLabel = new JLabel("Enter your name:");
        nameField = new JTextField(15);

        add(nameLabel);
        add(nameField);

        JButton play = new JButton("Start Game");

        play.addActionListener(e -> {
            currentUser.setUserName(nameField.getText());
            frame.showScreen(PanelIndex.Game);
        });

        add(play);

        JButton backHome = new JButton("Back");

        backHome.addActionListener(e -> frame.showScreen(PanelIndex.Home));

        add(backHome);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                currentUser.setUserName(nameField.getText());
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                nameField.setText("");
            }
        });
    }
}
