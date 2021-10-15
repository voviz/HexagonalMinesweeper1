import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JFrame {

    private JPanel panel;
    private JLabel label;
    private Game game;


    public static void main(String[] args) {
        new Display().setVisible(true);
    }

    Display() {
        game = new Game();
        game.start();
        initLabel();
        initPanel();
        initFrame();
//        setIconImage(); TODO
    }

    private void initLabel() {
        label = new JLabel();
        label.setText("Good luck!");
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setTitle("Hex sweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(new Dimension(Game.hexWidth * 3 / 4 * Game.COLS + Game.hexWidth * 11 / 16,
                Game.hexHeight * (Game.ROWS + 1) + Game.hexHeight * 14/16 + 14));
        //pack();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.red);
                for (int x = 0; x < Game.COLS; x++)
                    for (int y = 0; y < Game.ROWS; y++)
                        game.getField()[x][y].paint(g, x, y);
            }
        };

        panel.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Coord coord = findCoord(e.getX(), e.getY());
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getText());
                panel.repaint();
            }
        });
        panel.setBackground(Color.lightGray);
        add(panel);
    }

    private String getText() {
        return switch (game.getGameState()) {
            case PLAYING -> "Keep going!";
            case BOOMED -> "OOps.... You lost";
            case WON -> "Congratulations!!!";
        };
    }

    private Coord findCoord(int x, int y) {
        int xVal = x / (Game.hexWidth * 3/4);
        int yVal;
        if (xVal % 2 == 0)
            yVal = y / Game.hexHeight;
        else yVal = (y - Game.hexHeight / 2) / Game.hexHeight;
        return new Coord(xVal, yVal);
    }

}
