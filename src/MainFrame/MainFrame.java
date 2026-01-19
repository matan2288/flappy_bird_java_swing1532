package MainFrame;
import java.awt.*;
import javax.swing.*;
import panels.*;
import GameLogic.User;


public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;
    User newUser;
    Image backgroundImage;

    public MainFrame() {
        cardLayout = new CardLayout();
        newUser = new User();
        backgroundImage = Toolkit.getDefaultToolkit().getImage("assets/flappybirdbg.png");

        // Create a background panel that paints the image
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Create main panel with CardLayout and make it transparent
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        // Add all screens
        mainPanel.add(new Home(this), PanelIndex.Home.name());
        mainPanel.add(new UserInfo(this, newUser), PanelIndex.UserInfo.name());
        mainPanel.add(new GamePanel(this, newUser), PanelIndex.Game.name());
        mainPanel.add(new YourScore(this, newUser), PanelIndex.YourScore.name());
        mainPanel.add(new Scoreboard(this), PanelIndex.Scoreboard.name());

        // Add mainPanel on top of background
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);

        setTitle("Flappy Bird");
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
