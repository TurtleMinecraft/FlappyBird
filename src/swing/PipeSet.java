package swing;

import java.awt.*;

public class PipeSet {

    public static final int WIDTH = 30;
    public static final int GAP_HEIGHT = 250;

    private static final int ADVANCE_PIXELS_PER_FRAME = 2;

    private final Rectangle bottomPipe;
    private final Rectangle topPipe;

    public PipeSet(int bottomGapLocation) {
        bottomPipe = new Rectangle(Window.WIDTH, bottomGapLocation, WIDTH,
                Window.HEIGHT - bottomGapLocation);
        topPipe = new Rectangle(Window.WIDTH, 0, WIDTH, Window.HEIGHT - bottomPipe.height - GAP_HEIGHT);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fill(bottomPipe);
        g2.fill(topPipe);
    }

    public void advance() {
        bottomPipe.translate(-ADVANCE_PIXELS_PER_FRAME, 0);
        topPipe.translate(-ADVANCE_PIXELS_PER_FRAME, 0);
    }

    public Rectangle getTopPipe() {
        return topPipe;
    }

    public Rectangle getBottomPipe() {
        return bottomPipe;
    }

    public boolean offScreen() {
        return topPipe.x < -WIDTH;
    }
}
