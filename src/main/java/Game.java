public class Game {
    static int hexWidth = 40; //27
    static int hexHeight = 33; //22
    static int hexRadius = 20; // 13
    static int COLS = 8;
    static int ROWS = 8;
    private final int totalBombs = 10;
    private Field field;
    private boolean youWin;
    private boolean bombed;
    private GameState gameState;

    public void start() {
        bombed = false;
        youWin = false;
        field = new Field(COLS, ROWS);
        //field.setFieldSize(COLS, ROWS);
        field.initField(totalBombs);
    }

    public Cell[][] getField() {
        return field.getField();
    }

    public void pressLeftButton(Coord coord) {
        if (bombed || youWin) {
            start();
            return;
        }
        openCell(coord);
        checkWinOrLose();
    }

    public void pressRightButton(Coord coord) {
        if (!field.getField()[coord.x][coord.y].isOpened() && !youWin && !bombed)
            field.getField()[coord.x][coord.y].inverseFlag();
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

    private void checkWinOrLose() {
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

    public GameState getGameState() {
        if (bombed) return GameState.BOOMED;
        if (youWin) return GameState.WON;
        return GameState.PLAYING;
    }
}
