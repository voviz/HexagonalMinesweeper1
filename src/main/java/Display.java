import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JFrame {

    private JPanel panel;
    private Game game;


    public static void main(String[] args) {
        new Display().setVisible(true);
    }

    Display() {
        game = new Game();
        game.start();
        initPanel();
        initFrame();
//        setIconImage(); TODO
    }

    private void initFrame() {
        setTitle("Hex sweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(new Dimension(Game.hexWidth + Game.hexWidth * 3 / 4 * Game.COLS,
                Game.hexHeight * (Game.ROWS + 2) - Game.ROWS * 3));
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
//                if (tapOnTile(x, y)) {
                    Coord coord = findCoord(e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON1)
                        game.pressLeftButton(coord);
                    if (e.getButton() == MouseEvent.BUTTON2)
                        game.pressRightButton(coord);
                //}
                panel.repaint();
            }
        });
        panel.setBackground(Color.lightGray);
        add(panel);
    }

    private boolean tapOnTile(int x, int y) {
        int xVal = x / (Game.hexWidth * 3/4);
        int yVal = y / Game.hexHeight;
        int dX = x % (Game.hexWidth * 3/4);
        int dY = y % Game.hexHeight;
        if (xVal == 0 && dX < Game.hexWidth / 4) {
            if (dY < Game.hexHeight / 2)
                return isInsideTriangle(dX, dY, Game.hexWidth / 4, 0, 0, Game.hexHeight / 2, Game.hexWidth / 2, Game.hexHeight / 2 );
            else return isInsideTriangle(dX, dY, 0, Game.hexHeight / 2, Game.hexWidth / 4, Game.hexHeight / 2, Game.hexWidth / 4, Game.hexHeight);
        }
        return false;
    }

    private Coord findCoord(int x, int y) {
        //Coord coord = new Coord(1,1);
        int xVal = x / (Game.hexWidth * 3/4);
        int yVal;
        if (xVal % 2 == 0)
            yVal = (y) / Game.hexHeight;
        else yVal = (y - Game.hexHeight / 2 - y * 3) / Game.hexHeight;
        int dX = x % (Game.hexWidth * 3/4);
        int dY = y % Game.hexHeight;
        int slope = (Game.hexHeight / 2) / (Game.hexWidth / 4);
        int caldX = dY / slope;
        int delta = Game.hexWidth / 4 - caldX;
        return new Coord(xVal, yVal);
    }

    private boolean isInsideTriangle(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
        int a = (x1-x0)*(y2-y1)-(x2-x1)*(y1-y0);
        int b = (x2-x0)*(y3-y2)-(x3-x2)*(y2-y0);
        int c = (x3-x0)*(y1-y3)-(x1-x3)*(y3-y0);
        return a > 0 && b > 0 && c > 0 || (a < 0 && b < 0 && c < 0);
    }
}
