package GameLogic;

public class Pipes {
    public int pipesPositionX;
    public int pipesWidth;

    public int topPipeHeight;
    public int bottomPipeHeight;

    public int topPipePositionY;
    public int bottomPipePositionY;

    public Pipes() {
        // ! panel height 820
        pipesWidth = 60; // Standard pipe width

        pipesPositionX = 700; // Spawn just off-screen to the right

        topPipePositionY = 0;
        topPipeHeight = 300;

        bottomPipePositionY = 600;
        bottomPipeHeight = 300;
    }

    public void movePipesHorizontally(int speed) {
        pipesPositionX -= speed;
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

}
