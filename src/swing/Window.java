package swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JPanel {

    // pixels
    public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final double PERIODIC_FRAME = 0.02;

    private static final long SECONDS_TO_NANOSECONDS = 1000000000;

    private static final String TITLE = "Flappy Bird";
    private static final boolean IS_DOUBLE_BUFFERED = true;
    private static final double INTERVAL_BETWEEN_PIPES = 2.5;

    private final ArrayList<PipeSet> pipes;
    private double currentTimeSeconds;
    private double targetTimeSeconds;

    private static Window instance;

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window(WIDTH, HEIGHT);
        }
        return instance;
    }

    private Window(int width, int height) {
        super(IS_DOUBLE_BUFFERED);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setVisible(true);
        pipes = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pipes.forEach(pipe -> pipe.draw(g2));
    }

    public void run() {
        JFrame jFrame = new JFrame(TITLE);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.add(this);
        jFrame.pack();
        while (true) {
            this.update();
        }
    }

    private void delay(double seconds) {
        long currentTime = System.nanoTime();
        long targetTime = (long) (System.nanoTime() + seconds * SECONDS_TO_NANOSECONDS);
        while (targetTime >= currentTime) {
            currentTime = System.nanoTime();
        }
    }

    private void update() {
        delay(PERIODIC_FRAME);
        pipes.forEach(PipeSet::advance);
        pipes.forEach(this::repaintPipeSet);
        updatePipes();
        repaint();
    }

    private void repaintPipeSet(PipeSet pipeSet) {
        repaint(pipeSet.getTopPipe());
        repaint(pipeSet.getBottomPipe());
    }

    private void updatePipes() {
        currentTimeSeconds = System.nanoTime() / SECONDS_TO_NANOSECONDS;
        if (currentTimeSeconds >= targetTimeSeconds) {
            pipes.add(new PipeSet((int) (Math.random() * (HEIGHT - PipeSet.GAP_HEIGHT) + PipeSet.GAP_HEIGHT)));
            targetTimeSeconds = currentTimeSeconds + INTERVAL_BETWEEN_PIPES;
        }
        if (pipes.get(0).offScreen()) pipes.remove(0);
    }
}
