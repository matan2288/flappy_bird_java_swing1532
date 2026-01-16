package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Bird bird;
    private java.util.List<Pipes> pipes;
    private Keyboard keyboard;
    private Timer gameLoop;

    private JLabel birdcoordsLabel = new JLabel();
    private JLabel pipesCoordslabel = new JLabel();
    private JLabel userNameLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();

    private final int PIPE_SPAWN_INTERVAL = 100; // Spawn new pipe every ~2 seconds at 60fps
    private int pipeSpawnTimer = 0;
    private int score = 0;

    public GamePanel(MainFrame frame, User currentUser) {
        bird = new Bird();
        pipes = new java.util.ArrayList<>();
        pipes.add(new Pipes());

        keyboard = new Keyboard();

        // Listen to keyboard pressing
        setFocusable(true);
        addKeyListener(keyboard);
        // add(birdcoordsLabel);
        // add(pipesCoordslabel);
        add(scoreLabel);
        add(userNameLabel);

        gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            bird.handleBirdMovement(keyboard.isSpaceClicked());
            birdcoordsLabel.setText(bird.getCoords());
            Pipes currentPipesSet = null;
            Pipes pipesBeforeBird = null;

            // Move all pipes and remove off-screen ones
            for (int i = pipes.size() - 1; i >= 0; i--) {
                currentPipesSet = pipes.get(i);
                currentPipesSet.movePipesHorizontally(3);

                // Get pipes before before
                if (currentPipesSet.getCurrentPipePositionX() > 100) {
                    pipesBeforeBird = pipes.get(i);
                } else {
                    // Pipes passed bird > Add to score
                    score = score + 1;
                }

                // Remove pipes that have moved off the left side
                if (currentPipesSet.pipesPositionX + currentPipesSet.pipesWidth < 0) {
                    pipes.remove(i);
                }
            }

            pipeSpawnTimer++;

            if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
                pipes.add(new Pipes());
                pipeSpawnTimer = 0;
            }

            pipesCoordslabel.setText(pipesBeforeBird.getCoords());

            currentUser.setUserScore(score);
            if (bird.isBirdDead(pipesBeforeBird)) {
                ((Timer) e.getSource()).stop();
            }

            scoreLabel.setText("Score: " + score);
            userNameLabel.setText("User: " + currentUser.getUserName()); // Add this line

            repaint();
        });

        JButton btn = new JButton("Stop game");

        btn.addActionListener(e -> {
            // frame.showScreen(PanelIndex.Home);
            gameLoop.stop();
        });

        JButton btn2 = new JButton("Restart game");

        JButton nxt = new JButton("nxt");

        btn2.addActionListener(e -> {
            startGame();
        });


        nxt.addActionListener(e -> frame.showScreen(PanelIndex.YourScore));


        add(btn);

        add(btn2);
        add(nxt);

        // Listen for when panel becomes visible/hidden
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                // Start the game when panel becomes visible
                startGame();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                // Stop the game when panel is hidden
                gameLoop.stop();
            }
        });
    }

    public void startGame() {
        bird = new Bird();
        keyboard = new Keyboard();
        for (java.awt.event.KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
        addKeyListener(keyboard);
        requestFocusInWindow();
        gameLoop.start();
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
