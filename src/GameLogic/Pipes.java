package GameLogic;

public class Pipes {
    // These measurements based on the panel height which is 820px
    final public int pipesWidth = 60;
    public int pipesPositionX;
    public int topPipeHeight;
    public int bottomPipeHeight;
    final public int topPipePositionY = 0;
    final public int bottomPipePositionY = 600;;

    public Pipes() {
        pipesPositionX = 700;
        topPipeHeight = 300;
        bottomPipeHeight = 300;
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

}
