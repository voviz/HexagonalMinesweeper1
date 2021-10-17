import java.awt.*;

public class Cell {
    private boolean isOpened;
    private boolean isMined;
    private boolean isFlagged;
    private Integer index;

    public Cell() {
        isOpened = false;
        isMined = false;
        isFlagged = false;
        index = 0;
    }

    void open() {
        if (isFlagged) return;
        isOpened = true;
    }

    int getIndex() {
        return index;
    }

    void incIndex() {
        index++;
    }

    void mine() {
        isMined = true;
    }

    boolean isMined() {
        return isMined;
    }

    void inverseFlag() {
        if (isOpened) return;
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
        if (x % 2 == 0)
            yCentre = Game.hexHeight / 2 + Game.hexHeight * y + 2;
        else yCentre = Game.hexHeight + Game.hexHeight * y + 2;
        if (!isOpened) {
            paintTile(xCentre, yCentre, Color.cyan, g);
            if (isFlagged)
                paintFlag(xCentre, yCentre, g);
        }
        else {
            paintTile(xCentre, yCentre, Color.white, g);
            if (isMined()) paintBomb(xCentre, yCentre, g);
            else if (index > 0)
                paintString(index.toString(), xCentre, yCentre, g);

        }
    }

    private void paintTile(int xCentre, int yCentre, Color color, Graphics g) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint(xCentre + (int)Math.floor(Game.hexRadius * Math.cos(i * 2 * Math.PI / 6)),
                    yCentre + (int)Math.floor(Game.hexRadius * Math.sin(i * 2 * Math.PI / 6)));
        }
        g.setColor(color);
        g.fillPolygon(hexagon);
        g.setColor(Color.black);
        g.drawPolygon(hexagon);
    }

    private void paintFlag(int xCentre, int yCentre, Graphics g) {
        g.setColor(Color.black);
        g.fillRect(xCentre - Game.hexWidth / 10, yCentre - Game.hexHeight / 3,
                Game.hexWidth/ 20, Game.hexHeight * 3 / 5);
        g.setColor(Color.red);
        g.fillRect(xCentre - Game.hexWidth / 18, yCentre - Game.hexHeight / 3,
                Game.hexWidth / 5, Game.hexHeight / 3);
    }

    private void paintBomb(int xCentre, int yCentre, Graphics g) {
        g.setColor(Color.black);
        g.fillRect(xCentre - Game.hexWidth * 15 / 128, yCentre - Game.hexHeight * 5 / 16,
                Game.hexWidth / 5, Game.hexHeight * 25 / 40);
        g.fillRect(xCentre - Game.hexWidth * 12 / 64, yCentre - Game.hexHeight * 9 / 40,
                Game.hexWidth * 17 / 48, Game.hexHeight * 14 / 32);
        g.fillRect(xCentre - Game.hexWidth / 4, yCentre - Game.hexHeight / 7,
                Game.hexWidth / 2, Game.hexHeight * 3 / 12);
        g.setColor(Color.white);
        g.fillRect(xCentre - Game.hexWidth * 6 / 64, yCentre - Game.hexHeight * 2 / 16,
                Game.hexWidth / 13, Game.hexHeight / 12);
    }

    private void paintString(String string, int xCentre, int yCentre, Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("", Font.BOLD, Game.hexHeight));
        g.drawString(string, xCentre - Game.hexWidth / 4, yCentre + Game.hexHeight / 3);
    }
}
