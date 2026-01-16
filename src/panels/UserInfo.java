package panels;

import MainFrame.*;
import javax.swing.*;
import GameLogic.User;

public class UserInfo extends JPanel {
    public UserInfo(MainFrame frame, User currentUser) {

        JLabel nameLabel = new JLabel("Enter your name:");
        JTextField nameField = new JTextField(15);

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
    }
}
