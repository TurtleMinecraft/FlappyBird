package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Window extends JPanel {

    // pixels
    public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int GROUND_HEIGHT = 40;

    public static final double PERIODIC_FRAME = 1 / 60.0;

    private static final long SECONDS_TO_NANOSECONDS = 1000000000;

    private static final String TITLE = "Flappy Bird";
    private static final boolean IS_DOUBLE_BUFFERED = true;
    private static final Color GROUND_COLOR = new Color(0, 122, 0);
    private static final double INTERVAL_BETWEEN_PIPES = 3;

    private final Rectangle ground;
    private final ArrayList<PipeSet> pipes;
    private final Character character;

    private double currentTimeSeconds;
    private double targetTimeSeconds;
    private int score;

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
        setFocusable(true);
        ground = new Rectangle(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);
        pipes = new ArrayList<>();
        character = Character.getInstance();
        KeyBindings keyBindings = new KeyBindings();
        addKeyListener(keyBindings);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(GROUND_COLOR);
        g2.fill(ground);
        pipes.forEach(pipe -> pipe.draw(g2));
        g2.setColor(Color.YELLOW);
        g2.fill(character);
    }

    public void run() {
        JFrame jFrame = new JFrame(TITLE);
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(jFrame);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(this);
        jFrame.pack();
        while (true) {
            this.update();
            if (KeyBindings.escape) jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
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
        character.update();
        repaint(character);
        for (PipeSet pipeSet : pipes) {
            pipeSet.advance();
            repaintPipeSet(pipeSet);
            character.updateLife(pipeSet);
        }
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
            pipes.add(new PipeSet((int) (Math.random() * (HEIGHT - PipeSet.GAP_HEIGHT * 1.5) +
                    PipeSet.GAP_HEIGHT * 1.5)));
            targetTimeSeconds = currentTimeSeconds + INTERVAL_BETWEEN_PIPES;
        }
        if (pipes.get(0).offScreen()) {
            pipes.remove(0);
            score++;
        }
    }
}
