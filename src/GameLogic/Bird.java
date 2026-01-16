package GameLogic;

import java.awt.Image;
import java.awt.Toolkit;

public class Bird {

    public int x;
    public int y;
    public int width;
    public int height;

    public boolean dead;

    public double birdDropSpeed;
    public double gravity;
    public final double jumpSpeed = -4.5;
    public int jumpDelay;

    private Image image = Toolkit.getDefaultToolkit().getImage("assets/flappybird.png");

    public Bird() {
        x = 100;
        y = 320;
        birdDropSpeed = 0;
        width = 45;
        height = 32;
        gravity = 0.1;
        jumpDelay = 10;
        dead = false;
    }

    public Image getBirdImage() {
        return image;
    }

    public String getCoords() {
        return "x: " + x + " y: " + y;
    }

    public void handleBirdMovement(boolean isJumping) {
        if (isJumping) {
            birdDropSpeed = jumpSpeed;
        } else {
            birdDropSpeed += gravity;
            y += (int) birdDropSpeed;
        }
    }

    // add frame boundries
    public boolean isBirdDead(Pipes pipesBeforeBird) {
        // pipe boundries
        if (pipesBeforeBird.getCurrentPipePositionX() >= 100 && pipesBeforeBird.getCurrentPipePositionX() <= 160) {
            if (y < pipesBeforeBird.getTopPipeBoundry() || y >= pipesBeforeBird.getBottomPipeBoundry()) {
                return dead = true;
            }
        }

        // frame boundries
        if (y < 0 || y > 820) {
            return dead = true;
        }
        return false;
    }

}