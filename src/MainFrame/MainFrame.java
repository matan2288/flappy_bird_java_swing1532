package MainFrame;
import java.awt.*;
import javax.swing.*;
import panels.*;


public class MainFrame extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new Game(this), PanelIndex.Game.name());
        mainPanel.add(new Home(this), PanelIndex.Home.name());
        mainPanel.add(new Scoreboard(this), PanelIndex.Scoreboard.name());
        mainPanel.add(new UserInfo(this), PanelIndex.UserInfo.name());

        mainPanel.add(new YourScore(this), PanelIndex.YourScore.name());

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
