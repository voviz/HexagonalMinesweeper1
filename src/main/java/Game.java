public class Game {
    static int hexWidth = 30;
    static int hexHeight = 21;//hexWidth * (int)Math.round(Math.sqrt(3)) / 2;
    static int hexRadius = hexWidth / 2;
    static int COLS = 10;
    static int ROWS = 10;
    int totalBombs = 10;
    Field field;
    boolean youWin;
    boolean bombed;

    public void start() {
        field = new Field(COLS, ROWS);
        //field.setFieldSize(COLS, ROWS);
        field.initField();
    }

    public Cell[][] getField() {
        return field.getField();
    }

    public void pressLeftButton(Coord coord) {
        if (!field.getField()[coord.x][coord.y].isOpened() &&
        !field.getField()[coord.x][coord.y].isFlagged()) {
            if (!field.getField()[coord.x][coord.y].isMined()) {
                field.getField()[coord.x][coord.y].open();
                youWin = countOpenedCells() == (COLS * ROWS - totalBombs);
            }
            else bombed = true;
        }
    }

    public void pressRightButton(Coord coord) {
        if (!field.getField()[coord.x][coord.y].isOpened())
            field.getField()[coord.x][coord.y].inverseFlag();
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
}
