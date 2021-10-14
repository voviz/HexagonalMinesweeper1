import java.awt.*;

public class Cell {
    private boolean isOpened = false;
    private boolean isMined = false;
    private boolean isFlagged = false;
    private boolean boomed = false;
    int countOpenedCells;

    void open() {
        isOpened = true;
        if (!isMined) countOpenedCells++;
        boomed = isMined;
    }

    void mine() {
        isMined = true;
    }

    boolean isMined() {
        return isMined;
    }

    void inverseFlag() {
        isFlagged = !isFlagged;
    }

    boolean isOpened() {
        return isOpened;
    }

    boolean isFlagged() {
        return isFlagged;
    }

    void paint(Graphics g, int x, int y) {
        int xCentre = Game.hexWidth / 2 + Game.hexWidth * 3 / 4 * x;
        int yCentre;
//        if (x % 2 == 0)
//            yCentre = Game.hexHeight / 2 + Game.hexHeight * y - y * 3;
//        else yCentre = Game.hexHeight + Game.hexHeight * y - y * 3;
        if (x % 2 == 0)
            yCentre = Game.hexHeight / 2 + Game.hexHeight * y;
        else yCentre = Game.hexHeight + Game.hexHeight * y;
        if (!isOpened) {
            paintTile(xCentre, yCentre, Color.cyan, g);
        }
        else
            paintTile(xCentre, yCentre, Color.white, g);
    }

    private void paintTile(int xCentre, int yCentre, Color color, Graphics g) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint(xCentre + (int)Math.round(Game.hexRadius * Math.cos(i * 2 * Math.PI / 6)),
                    yCentre + (int)Math.round(Game.hexRadius * Math.sin(i * 2 * Math.PI / 6)));
        }
        g.setColor(color);
        g.fillPolygon(hexagon);
        g.setColor(Color.black);
        g.drawPolygon(hexagon);
    }
}
