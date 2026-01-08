package GameLogic;

public class Pipes {
    public int x;
    public int topY;
    public int height;
    public int width;
    public int bottomY;
    public int bottomWidth;
    public int bottomHeight;

    public Pipes() {
        // Top pipe coordinates and dimensions
        x = 300; // Horizontal center
        topY = 0;
        width = 80;
        height = 300; // height for top pipe

        // Bottom pipe coordinates and dimensions
        x = 300;
        bottomHeight = 300; // height for bottom pipe
        bottomY = 900 - bottomHeight; // frame height assumed 820
        bottomWidth = width;
    }

    public void setPipePosition(int a) {
        x -= a;
    }
}
