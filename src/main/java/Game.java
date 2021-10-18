public class Game {
    static int hexWidth = 40; //27
    static int hexHeight = 33; //22
    static int hexRadius = 20; // 13
    static int COLS = 8;
    static int ROWS = 8;
    static int totalBombs = 10;
    private Field field;
    private boolean youWin;
    private boolean bombed;

    public void start() {
        bombed = false;
        youWin = false;
        field = new Field(COLS, ROWS);
        field.initField(totalBombs);
    }

    public Cell[][] getField() {
        return field.getField();
    }

    public void pressLeftButton(int x, int y) {
        Coord coord = findCoord(x, y);
        if (coordNotInside(coord)) return;
        if (bombed || youWin) {
            start();
            return;
        }
        openCell(coord);
        openBombsIfBombed();
    }

    public void pressRightButton(int x, int y) {
        Coord coord = findCoord(x, y);
        if (coordNotInside(coord)) return;
        if (!field.getField()[coord.x][coord.y].isOpened() && !youWin && !bombed)
            field.getField()[coord.x][coord.y].inverseFlag();
    }

    private boolean coordNotInside(Coord coord) {
        return coord.x >= COLS || coord.y >= ROWS;
    }

    private void openCell(Coord coord) {
        if (!field.getField()[coord.x][coord.y].isOpened() &&
                !field.getField()[coord.x][coord.y].isFlagged() && !bombed && !youWin) {
            if (!field.getField()[coord.x][coord.y].isMined()) {
                field.getField()[coord.x][coord.y].open();
                youWin = countOpenedCells() == (COLS * ROWS - totalBombs);
                if (field.getField()[coord.x][coord.y].getIndex() == 0)
                    for (Coord around: field.getCoordsAround(coord))
                        openCell(around);
            }
            else bombed = true;
        }
    }

    private void openBombsIfBombed() {
        if (bombed) {
            for (int x = 0; x < COLS; x++)
                for (int y = 0; y < ROWS; y++) {
                    if (field.getField()[x][y].isMined())
                        field.getField()[x][y].open();
                }
        }
    }

    private int countOpenedCells() {
        int counterOfOpened = 0;
        for (int x = 0; x < COLS; x++)
            for (int y = 0; y < ROWS; y++) {
                if (field.getField()[x][y].isOpened())
                    counterOfOpened++;
            }
        return counterOfOpened;
    }

    private Coord findCoord(int x, int y) {
        int xVal = x / (Game.hexWidth * 3/4);
        int yVal;
        if (xVal % 2 == 0)
            yVal = y / Game.hexHeight;
        else yVal = (y - Game.hexHeight / 2) / Game.hexHeight;
        return new Coord(xVal, yVal);
    }

    public GameState getGameState() {
        if (bombed) return GameState.BOOMED;
        if (youWin) return GameState.WON;
        return GameState.PLAYING;
    }

    public void changeBombCount(String s) {
        totalBombs = Integer.parseInt(s);
    }

    public void changeFieldSize(String cols, String rows) {
        COLS = Integer.parseInt(cols);
        ROWS = Integer.parseInt(rows);
        if (totalBombs > COLS * ROWS / 2)
            totalBombs = COLS * ROWS / 2;
    }
}
