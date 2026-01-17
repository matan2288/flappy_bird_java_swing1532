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
        add(scoreLabel);
        add(userNameLabel);

        gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            // Handle bird movement
            bird.handleBirdMovement(keyboard.isSpaceClicked());
            birdcoordsLabel.setText(bird.getCoords());

            Pipes pipesBeforeBird = null;
            int pipesPassed = 0;

            // Move pipes, detect scoring and cleanup
            for (int i = pipes.size() - 1; i >= 0; i--) {
                Pipes currentPipe = pipes.get(i);
                currentPipe.movePipesHorizontally(3);

                // Check if the pipe is before the bird (for collision check)
                if (currentPipe.getCurrentPipePositionX() > 100) {
                    // The latest pipe before the bird (closest OBSTACLE)
                    pipesBeforeBird = currentPipe;
                } else {
                    // Pipe just passed the bird, increase pipesPassed
                    pipesPassed++;
                }

                // Remove pipes that have moved off the left side
                if (currentPipe.pipesPositionX + currentPipe.pipesWidth < 0) {
                    pipes.remove(i);
                }
            }

            // Update score only once for each pipe set passed
            score += pipesPassed;

            // Handle pipe spawning
            pipeSpawnTimer++;
            if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
                pipes.add(new Pipes());
                pipeSpawnTimer = 0;
            }

            // Safe check for pipesBeforeBird to avoid NPE
            if (pipesBeforeBird != null) {
                pipesCoordslabel.setText(pipesBeforeBird.getCoords());
            } else {
                pipesCoordslabel.setText("No Pipe");
            }

            // Update user data and labels
            currentUser.setUserScore(score);
            scoreLabel.setText("Score: " + score);
            userNameLabel.setText("User: " + currentUser.getUserName());

            // Check bird state
            if (bird.isBirdDead(pipesBeforeBird)) {
                ((Timer) e.getSource()).stop();
            }

            // Redraw
            repaint();
        });

        JButton stopGameButton = new JButton("Stop game");
        JButton restartGameButton = new JButton("Restart game");
        JButton nxt = new JButton("nxt");

        stopGameButton.addActionListener(e -> {
            // frame.showScreen(PanelIndex.Home);
            gameLoop.stop();
        });

        restartGameButton.addActionListener(e -> {
            initializeGame();
            startGame();
        });

        nxt.addActionListener(e -> {
            frame.showScreen(PanelIndex.YourScore);
        });

        add(stopGameButton);

        add(restartGameButton);
        add(nxt);

        // Listen for when panel becomes visible/hidden
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                // Intialize and Start the game when panel becomes visible
                initializeGame();
                startGame();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                initializeGame();
            }
        });
    }

    public void initializeGame() {
        // Reset bird and keyboard
        bird = new Bird();
        keyboard = new Keyboard();

        // Reset score
        score = 0;

        // Reset pipes
        pipes.clear();
        Pipes initialPipe = new Pipes();
        pipes.add(initialPipe);

        // Reset pipe spawn timer
        pipeSpawnTimer = 0;

        // Re-add keyboard listener
        for (java.awt.event.KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
        addKeyListener(keyboard);
        requestFocusInWindow();
    }

    public void startGame() {
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
