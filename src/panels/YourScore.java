package panels;
import MainFrame.*;
import javax.swing.*;

public class YourScore extends JPanel {
    public YourScore(MainFrame frame) {
        JButton btn = new JButton("Go To Home");

        btn.addActionListener(e ->
            frame.showScreen(PanelIndex.Home)
        );

        add(btn);
    }
}
