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
    public int jumpDelay;
    public final double jumpSpeed = -6.5;
    private Image image;

    public Bird() {
        x = 100;
        y = 320;
        birdDropSpeed = 0;
        width = 45;
        height = 32;
        gravity = 0.2;
        jumpDelay = 10;
        dead = false;
    }

    public Image getBirdImage() {
        image = Toolkit.getDefaultToolkit().getImage("assets/flappybird.png");
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

    public boolean isBirdDead(Pipes p) {
        if (y < 0 || y > 820) {
            dead = true;
            return dead;
        }
        if (x + width > p.getCurrentPipePositionX() && x < p.getCurrentPipePositionX() + p.pipesWidth) {
            if (y < p.getTopPipeBoundry() || y + height > p.getBottomPipeBoundry()) {
                dead = true;
                return dead;
            }
        }
        return false;
    }

}