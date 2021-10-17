import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    Cell cell = new Cell();
    int cols = 5;
    int rows = 5;
    Field field = new Field(cols, rows);

    @Test
    void cellTest() {
        assertFalse(cell.isMined());
        assertFalse(cell.isOpened());
        assertFalse(cell.isFlagged());
        assertEquals(0, cell.getIndex());
    }

    @Test
    void openCellTest() {
        assertFalse(cell.isOpened());
        cell.open();
        assertTrue(cell.isOpened());
    }

    @Test
    void mineCellTest() {
        cell.mine();
        assertTrue(cell.isMined());
        assertFalse(cell.isOpened());
        cell.open();
        assertTrue(cell.isOpened());
    }

    @Test
    void flagCellTest() {
        assertFalse(cell.isFlagged());
        cell.inverseFlag();
        assertTrue(cell.isFlagged());
        cell.inverseFlag();
        assertFalse(cell.isFlagged());
        cell.inverseFlag();
        cell.open();
        assertFalse(cell.isOpened());
        cell.inverseFlag();
        cell.open();
        assertTrue(cell.isOpened());
        cell.inverseFlag();
        assertFalse(cell.isFlagged());
    }

    @Test
    void indexCellTest() {
        assertEquals(0, cell.getIndex());
        cell.incIndex();
        assertEquals(1, cell.getIndex());
        cell.incIndex();
        assertEquals(2, cell.getIndex());
    }

    @Test
    void fieldTest() {
        for (int x = 0; x < cols; x++)
            for (int y = 0; y < rows; y++) {
                Cell cell = field.getField()[x][y];
                assertFalse(cell.isOpened());
                assertFalse(cell.isFlagged());
                assertFalse(cell.isMined());
                assertEquals(0, cell.getIndex());
            }
    }

    @Test
    void getCoordsAroundTest() {
        Coord coord1 = new Coord(1, 1);
        ArrayList<Coord> listOfCoordsAround1 = field.getCoordsAround(coord1);
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0 -> assertEquals(new Coord(0, 1), listOfCoordsAround1.get(i));
                case 1 -> assertEquals(new Coord(0, 2), listOfCoordsAround1.get(i));
                case 2 -> assertEquals(new Coord(1, 0), listOfCoordsAround1.get(i));
                case 3 -> assertEquals(new Coord(1, 2), listOfCoordsAround1.get(i));
                case 4 -> assertEquals(new Coord(2, 1), listOfCoordsAround1.get(i));
                case 5 -> assertEquals(new Coord(2, 2), listOfCoordsAround1.get(i));
            }
        }

        Coord coord2 = new Coord(2, 1);
        ArrayList<Coord> listOfCoordsAround2 = field.getCoordsAround(coord2);
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0 -> assertEquals(new Coord(1, 0), listOfCoordsAround2.get(i));
                case 1 -> assertEquals(new Coord(1, 1), listOfCoordsAround2.get(i));
                case 2 -> assertEquals(new Coord(2, 0), listOfCoordsAround2.get(i));
                case 3 -> assertEquals(new Coord(2, 2), listOfCoordsAround2.get(i));
                case 4 -> assertEquals(new Coord(3, 0), listOfCoordsAround2.get(i));
                case 5 -> assertEquals(new Coord(3, 1), listOfCoordsAround2.get(i));
            }
        }
    }

    @Test
    void initFieldTest() {
        int counter = 0;
        int totalBombs = 5;
        field.initField(totalBombs);
        for (int x = 0; x < cols; x++)
            for (int y = 0; y < rows; y++) {
                Cell cell = field.getField()[x][y];
                if (cell.isMined()) counter++;
            }
        assertEquals(totalBombs, counter);
    }

}
