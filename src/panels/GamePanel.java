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

    private JLabel userNameLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JLabel difficultyLevelLabel = new JLabel();

    private Pipes currentPipeSet = null;
    private Pipes upcomingPipeSet = null;
    private int PIPE_SPAWN_INTERVAL = 100; // Spawn new pipe every 2 second
    private int pipeSpawnTimer = 0;
    private int score = 0;
    private int speedByScore = 5;

    public GamePanel(MainFrame frame, User currentUser) {
        setOpaque(false);
        bird = new Bird();
        pipes = new java.util.ArrayList<>();
        pipes.add(new Pipes());

        keyboard = new Keyboard();

        // Listen to keyboard pressing
        setFocusable(true);
        addKeyListener(keyboard);
        add(scoreLabel);
        add(userNameLabel);
        add(difficultyLevelLabel);

        // Start game loop
        gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            // Handle bird movement
            bird.handleBirdMovement(keyboard.isSpaceClicked());

            // Handle pipe spawning
            pipeSpawnTimer++;

            if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
                pipes.add(new Pipes());
                pipeSpawnTimer = 0;
            }

            // Move pipes, detect scoring and cleanup
            for (int i = pipes.size() - 1; i >= 0; i--) {
                currentPipeSet = pipes.get(i);
                currentPipeSet.movePipesHorizontally(setDifficulityByScore(score));

                // Check if the pipe is before the bird (for collision check)
                if (currentPipeSet.getCurrentPipePositionX() > 100) {
                    // The latest pipe before the bird (closest OBSTACLE)
                    upcomingPipeSet = currentPipeSet;
                } else {
                    // Pipe just passed the bird, increase pipesPassed
                    score++;
                }
            }

            // Remove pipes that have moved off the left side
            if (currentPipeSet.pipesPositionX < -currentPipeSet.pipesWidth) {
                pipes.remove(0);
            }

            // Update user data and labels
            currentUser.setUserScore(score);
            scoreLabel.setText("Score: " + score);
            userNameLabel.setText("User: " + currentUser.getUserName());
            difficultyLevelLabel.setText("Difficulty: " + speedByScore);

            // Check bird state
            if (bird.isBirdDead(upcomingPipeSet)) {
                ((Timer) e.getSource()).stop();
            }

            // Redraw
            repaint();
        });

        JButton stopGameButton = new JButton("Stop game");
        JButton restartGameButton = new JButton("Restart game");
        JButton continueButton = new JButton("Continue");

        stopGameButton.addActionListener(e -> {
            gameLoop.stop();
        });

        restartGameButton.addActionListener(e -> {
            initializeGame();
            startGame();
        });

        continueButton.addActionListener(e -> {
            frame.showScreen(PanelIndex.YourScore);
        });

        add(stopGameButton);
        add(restartGameButton);
        add(continueButton);

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
        speedByScore = 5;
        PIPE_SPAWN_INTERVAL = 100;

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

    public int setDifficulityByScore(int score) {

        switch (score) {
            case 500:
                speedByScore = 6;
                PIPE_SPAWN_INTERVAL = 90;
                break;
            case 1000:
                speedByScore = 8;
                PIPE_SPAWN_INTERVAL = 80;
                break;
            case 1600:
                speedByScore = 9;
                PIPE_SPAWN_INTERVAL = 70;
                break;
            case 2100:
                speedByScore = 10;
                PIPE_SPAWN_INTERVAL = 70;
                break;
            case 2800:
                speedByScore = 11;
                PIPE_SPAWN_INTERVAL = 70;
                break;
            default:
                break;
        }
        return speedByScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the bird based on the image and the dimensions
        g.drawImage(bird.getBirdImage(), bird.x, bird.y, bird.width, bird.height, null);
        g.setColor(new java.awt.Color(80, 200, 120)); /** pipe color **/

        // Draw pipes from the pipes array
        for (Pipes pipe : pipes) {
            g.fillRect(pipe.pipesPositionX, pipe.topPipePositionY, pipe.pipesWidth, pipe.topPipeHeight);
            g.fillRect(pipe.pipesPositionX, pipe.bottomPipePositionY, pipe.pipesWidth, pipe.bottomPipeHeight);
        }
    }
}
