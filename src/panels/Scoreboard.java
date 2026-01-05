package panels;
import MainFrame.*;
import javax.swing.*;

public class Scoreboard extends JPanel {
    public Scoreboard(MainFrame frame) {
        JButton btn = new JButton("Back to Home");

        btn.addActionListener(e ->
            frame.showScreen(PanelIndex.Home)
        );

        add(btn);
    }
}
