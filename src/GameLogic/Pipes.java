package GameLogic;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Pipes {
    // Pipe images (static so they're loaded once and shared)
    private static Image topPipeImage = Toolkit.getDefaultToolkit().getImage("assets/toppipe.png");
    private static Image bottomPipeImage = Toolkit.getDefaultToolkit().getImage("assets/bottompipe.png");

    // These measurements based on the panel height which is 820px
    final public int pipesWidth = 80;
    public int pipesPositionX = 700;
    public int topPipeHeight = 300;
    public int bottomPipeHeight = 300;
    public int topPipePositionY = 0;
    public int bottomPipePositionY = 600;
    public int verticalGap = 250;
    Random rand = new Random();


    public Pipes() {
        int randomHeight = (int)(Math.random() * 201) - 100; //between -100 and 100
        int randomGap = (int)(Math.random() * (300 - 150 + 1)) + 150; //between 150-250

        verticalGap = randomGap;

        topPipePositionY = 0;
        topPipeHeight += randomHeight;

        bottomPipePositionY = topPipeHeight + verticalGap;
        bottomPipeHeight = 900 - bottomPipePositionY;
    }

    public void movePipesHorizontally(int screenMovementSpeed) {
        pipesPositionX -= screenMovementSpeed;
    }

    public int getTopPipeBoundry() {
        return topPipeHeight;
    }

    public int getBottomPipeBoundry() {
        return bottomPipePositionY;
    }

    // if current pipe position = 100 + pipe width, calculate borders
    public int getCurrentPipePositionX() {
        return pipesPositionX;
    }

    public String getCoords() {
        return "Current pipes position x: " + pipesPositionX +
               ", Top pipe boundry: " + getTopPipeBoundry() +
               ", Bottom pipe boundry: " + getBottomPipeBoundry();
    }

    public Image getTopPipeImage() {
        return topPipeImage;
    }

    public Image getBottomPipeImage() {
        return bottomPipeImage;
    }
}
