import java.util.ArrayList;
import java.util.Random;

public class Field {

    private int fieldLength;
    private int fieldHeight;
    private Cell[][] field;
    private Random random = new Random();

    public Field(int fieldLength, int fieldHeight) {
        this.fieldLength = fieldLength;
        this.fieldHeight = fieldHeight;
        field = new Cell[fieldLength][fieldHeight];
        for (int x = 0; x < fieldLength; x++)
            for (int y = 0; y < fieldHeight; y++)
                field[x][y] = new Cell();
    }

//    public void  setFieldSize(int length, int height) {
//        fieldLength = length;
//        fieldHeight = height;
//    }

    public void initField(int totalBombs) {
        if (totalBombs > fieldHeight * fieldLength / 2)
            totalBombs = fieldHeight * fieldLength / 2;
        for (int i = 0; i < totalBombs; i++)
            createBomb();
        createIndexes();
    }


    private void createIndexes() {
       for (int x = 0; x < fieldLength; x++)
           for (int y = 0; y < fieldHeight; y++)
               if (!field[x][y].isMined())
                   for (Coord around : getCoordsAround(new Coord(x, y)))
                        if (field[around.x][around.y].isMined())
                            field[x][y].incIndex();
    }

    private void createBomb() {
        int x;
        int y;
        while (true) {
            x = random.nextInt(fieldLength);
            y = random.nextInt(fieldHeight);
            if (!field[x][y].isMined()) {
                field[x][y].mine();
                break;
            }
        }
    }

    private boolean inField(Coord coord) {
        return coord.x >= 0 && coord.x < fieldLength &&
                coord.y >= 0 && coord.y < fieldHeight;
    }

     ArrayList<Coord> getCoordsAround(Coord coord) {
        ArrayList<Coord> listOfAroundCoords = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                Coord coordAround = new Coord(x, y);
                if (inField(coordAround)) {
                    if (coord.x % 2 != 0) {
                        if (x % 2 == 0) {
                            if (coordAround.y == coord.y - 1)
                                continue;
                            listOfAroundCoords.add(coordAround);
                        }
                        else if (!coordAround.equals(coord))
                            listOfAroundCoords.add(coordAround);
                    }
                    else {
                        if (x % 2 != 0) {
                            if (coordAround.y == coord.y + 1)
                                continue;
                            listOfAroundCoords.add(coordAround);
                        }
                        else if (!coordAround.equals(coord))
                            listOfAroundCoords.add(coordAround);
                    }
                }
            }
        return listOfAroundCoords;
    }

    public Cell[][] getField() {
        return field;
    }
}
