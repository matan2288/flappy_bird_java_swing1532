package MainFrame;
import java.awt.*;
import javax.swing.*;
import panels.*;
import GameLogic.User;


public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;
    User newUser;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        newUser = new User();

        mainPanel.add(new Home(this), PanelIndex.Home.name());
        mainPanel.add(new UserInfo(this, newUser), PanelIndex.UserInfo.name());
        mainPanel.add(new GamePanel(this, newUser), PanelIndex.Game.name());
        mainPanel.add(new YourScore(this), PanelIndex.YourScore.name());
        mainPanel.add(new Scoreboard(this), PanelIndex.Scoreboard.name());

        add(mainPanel);

        setTitle("Swing Navigation");
        setSize(600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showScreen(PanelIndex panel) {
        cardLayout.show(mainPanel, panel.name());
    }

    public static void runApp() {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
