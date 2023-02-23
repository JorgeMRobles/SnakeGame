import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
    private static final int CELL_SIZE = 10;

    private ArrayList<Point> snake = new ArrayList<Point>();
    private Point food;

    private int direction = KeyEvent.VK_RIGHT;
    private boolean running = true;
    private Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new java.awt.Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        snake.add(new Point(10, 10));
        snake.add(new Point(10, 11));
        snake.add(new Point(10, 12));

        food = createFood();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.BLACK);
        for (Point point : snake) {
            g.fillRect(point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public void move() {
        Point head = snake.get(0);
        Point newHead = (Point) head.clone();

        if (direction == KeyEvent.VK_UP) {
            newHead.y--;
        } else if (direction == KeyEvent.VK_DOWN) {
            newHead.y++;
        } else if (direction == KeyEvent.VK_LEFT) {
            newHead.x--;
        } else if (direction == KeyEvent.VK_RIGHT) {
            newHead.x++;
        }

        if (newHead.equals(food)) {
            snake.add(0, newHead);
            food = createFood();
        } else {
            snake.remove(snake.size() - 1);
            if (snake.contains(newHead)) {
                running = false;
            }
            snake.add(0, newHead);
        }
    }

    private Point createFood() {
        int x = random.nextInt(FRAME_WIDTH / CELL_SIZE);
        int y = random.nextInt(FRAME_HEIGHT / CELL_SIZE);
        return new Point(x, y);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
                && Math.abs(direction - key) != 2) {
            direction = key;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.setContentPane(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        while (game.running) {
            game.move();
            game.repaint();
            Thread.sleep(100);
        }
    }
}
