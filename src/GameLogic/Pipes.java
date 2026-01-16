package GameLogic;

public class Pipes {
    public int pipesPositionX;
    public int pipesWidth;

    public int topPipeHeight;
    public int bottomPipeHeight;

    public int topPipePositionY;
    public int bottomPipePositionY;


    public Pipes() {
        //! panel height 820
        pipesWidth = 60; // Standard pipe width

        pipesPositionX = 700; // Spawn just off-screen to the right

        topPipePositionY = 0;
        topPipeHeight = 300;


        bottomPipePositionY = 600;
        bottomPipeHeight = 300;



        // Set gap between top and bottom pipe
        int pipeGap = 205;

        // Ensure there is no spacing at the bottom (bottom pipe reaches the full edge)
        int totalHeight = 820;

    }



    public void movePipesHorizontally(int speed) {
        pipesPositionX -= speed;
    }
}
