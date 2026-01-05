package panels;
import MainFrame.*;
import javax.swing.*;

public class Game extends JPanel {
    public Game(MainFrame frame) {
        // Set a background image for the panel
        setLayout(null);
        frame.setSize(600, 900);
        // Use panel size or a fixed size for demonstration; can adjust as needed
        ImageIcon bgIcon = new ImageIcon("assets/flappybirdbg.png");
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 600, 900); // set to match MainFrame size
        add(background);
        // set background
        // set bird
        // set pipes
        // set bird movement






        JButton btn = new JButton("Go To Home");

        btn.addActionListener(e ->
            frame.showScreen(PanelIndex.Home)
        );

        add(btn);
    }
}
