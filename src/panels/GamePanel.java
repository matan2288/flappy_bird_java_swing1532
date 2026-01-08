package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Bird bird;
    private Pipes pipes;
    private Keyboard keyboard;
    private JLabel birdcoordsLabel = new JLabel();

    public GamePanel(MainFrame frame) {
        bird = new Bird();
        pipes = new Pipes();
        keyboard = new Keyboard();

        // Listen to keyboard pressing
        setFocusable(true);
        addKeyListener(keyboard);
        add(birdcoordsLabel);

        Timer gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            bird.handleBirdMovement(keyboard.isSpaceClicked());

            birdcoordsLabel.setText(bird.getCoords());

            pipes.setPipePosition(1);

            

            if (bird.isBirdDead()) {
                ((Timer) e.getSource()).stop();
            }
            repaint();
        });

        gameLoop.start();

        JButton btn = new JButton("Stop game");

        btn.addActionListener(e -> {
            // frame.showScreen(PanelIndex.Home);
            gameLoop.stop();
        });

        JButton btn2 = new JButton("Restart game");

        btn2.addActionListener(e -> {
            bird = new Bird();
            keyboard = new Keyboard();
            for (java.awt.event.KeyListener kl : getKeyListeners()) {
                removeKeyListener(kl);
            }
            addKeyListener(keyboard);
            requestFocusInWindow();
            gameLoop.start();
        });

        add(btn);

        add(btn2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the bird
        g.drawImage(bird.getBirdImage(), bird.x, bird.y, bird.width, bird.height, null);
        // If Pipes class doesn't have getPipeImage(), you could draw a simple pipe
        // shape instead:
        g.setColor(new java.awt.Color(80, 200, 120)); // Example pipe color
        g.fillRect(pipes.x, pipes.topY, pipes.width, pipes.height); // Top pipe
        g.fillRect(pipes.x, pipes.bottomY, pipes.bottomWidth, pipes.bottomHeight); // Bottom pipe

    }
}
