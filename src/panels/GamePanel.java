package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Bird bird;
    private java.util.List<Pipes> pipes;
    private Keyboard keyboard;
    private JLabel birdcoordsLabel = new JLabel();
    private JLabel pipescoordslabel = new JLabel();
    private final int PIPE_SPAWN_INTERVAL = 100; // Spawn new pipe every ~2 seconds at 60fps
    private int pipeSpawnTimer = 0;

    public GamePanel(MainFrame frame) {
        bird = new Bird();
        pipes = new java.util.ArrayList<>();
        pipes.add(new Pipes());

        keyboard = new Keyboard();

        // Listen to keyboard pressing
        setFocusable(true);
        addKeyListener(keyboard);
        add(birdcoordsLabel);
        add(pipescoordslabel);

        Timer gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            bird.handleBirdMovement(keyboard.isSpaceClicked());
            birdcoordsLabel.setText(bird.getCoords());
            Pipes currentPipesSet = null;
            Pipes pipesBeforeBird = null;

            // Move all pipes and remove off-screen ones
            for (int i = pipes.size() - 1; i >= 0; i--) {
                currentPipesSet = pipes.get(i);
                currentPipesSet.movePipesHorizontally(3);

                if (currentPipesSet.getCurrentPipePositionX() > 100) {
                    pipesBeforeBird = pipes.get(i);
                }
                // Remove pipes that have moved off the left side
                if (currentPipesSet.pipesPositionX + currentPipesSet.pipesWidth < 0) {
                    pipes.remove(i);
                }
            }

            pipescoordslabel.setText(pipesBeforeBird.getCoords());

            // get pipe set closest to the bird

            // get it's coords and boundries

            // Spawn new pipes at intervals
            pipeSpawnTimer++;

            if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
                pipes.add(new Pipes());
                pipeSpawnTimer = 0;
            }

            if (bird.isBirdDead(pipesBeforeBird)) {
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

        g.setColor(new java.awt.Color(80, 200, 120)); // Example pipe color
        // Draw all pipes in the pipes list
        for (Pipes pipe : pipes) {
            // Draw the top pipe
            g.fillRect(pipe.pipesPositionX, pipe.topPipePositionY, pipe.pipesWidth, pipe.topPipeHeight);
            // Draw the bottom pipe
            g.fillRect(pipe.pipesPositionX, pipe.bottomPipePositionY, pipe.pipesWidth, pipe.bottomPipeHeight);
        }

    }
}
