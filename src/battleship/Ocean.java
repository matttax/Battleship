package battleship;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Ocean {
    private CellStatus[][] field;
    public ArrayList<Ship> ships;
    public HashMap<Cell, Ship> shipCells;
    public final int N, M, initialCellsCount;

    /**
     * Ocean is the battlefield. Class for locating ships.
     * @param n Length
     * @param m Width
     * @param submarines Number of submarines
     * @param destroyers Number of destroyers
     * @param cruisers Number of cruisers
     * @param battleships Number of battleships
     * @param carriers Number of carriers
     */
    public Ocean(int n, int m, int submarines, int destroyers, int cruisers, int battleships, int carriers) {
        N = n;
        M = m;
        ships = new ArrayList<>();
        field = new CellStatus[n][m];
        shipCells = new HashMap<>();
        for (CellStatus[] row : field) {
            Arrays.fill(row, CellStatus.Water);
        }
        placeShips(carriers, 5);
        placeShips(battleships, 4);
        placeShips(cruisers, 3);
        placeShips(destroyers, 2);
        placeShips(submarines, 1);
        initialCellsCount = shipCells.size();
    }

    /**
     * Place N ships of some length.
     * @param n Number of ships.
     * @param len Length of one ship.
     */
    private void placeShips(int n, int len) {
        for (int i = 0; i < n; i++) {
            placeShip(len);
        }
    }

    /**
     * Find appropriate head cells for a ship of distinct length in a row.
     * @param row Row for search.
     * @param len Length of the ship.
     * @return ArrayList of appropriate cells.
     */
    private ArrayList<Integer> cellsInRow(int row, int len) {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            boolean cellIsOk = false;
            if (field[row][i] == CellStatus.Water) {
                cellIsOk = true;
                if (i + len > M)
                    cellIsOk = false;
                else for (int j = i; j < i + len; j++) {
                    cellIsOk &= field[row][j] == CellStatus.Water;
                }
            }
            if (cellIsOk) {
                cells.add(i);
            }
        }
        return cells;
    }

    /**
     * Find appropriate head cells for a ship of distinct length in a column.
     * @param col Column for search.
     * @param len Length of the ship.
     * @return ArrayList of appropriate cells.
     */
    private ArrayList<Integer> cellsInColumn(int col, int len) {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            boolean cellIsOk = false;
            if (field[i][col] == CellStatus.Water) {
                cellIsOk = true;
                if (i + len > N)
                    cellIsOk = false;
                else for (int j = i; j < i + len; j++) {
                    cellIsOk &= field[j][col] == CellStatus.Water;
                }
            }
            if (cellIsOk) {
                cells.add(i);
            }
        }
        return cells;
    }

    /**
     * Locate a ship in the ocean.
     * @param length Length of the ship.
     */
    private void placeShip(int length) {
        int row = -1, col = -1;
        ArrayList<Integer> possibleCells = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            possibleCells = cellsInRow(i, length);
            if (possibleCells.size() > 0) {
                row = i;
                break;
            }
            if (i < M)
                possibleCells = cellsInColumn(i, length);
            if (possibleCells.size() > 0) {
                col = i;
                break;
            }
        }
        if (row == -1 & col == -1) {
            throw new InvalidParameterException("Unfortunately, unable to locate the ships.\n" +
                    "Please, change the parameters.");
        }
        int cell = (int)(Math.random() * 10) % possibleCells.size();
        int selectedCell = possibleCells.get(cell);
        if (row != -1) {
            placeHorizontally(length, row, selectedCell);
        }
        else if (col != -1) {
            placeVertically(length, col, selectedCell);
        }
    }

    private void placeHorizontally(int length, int row, int selectedCell) {
        Ship currentShip = new Ship(length);
        if (selectedCell != 0) {
            if (row != 0) {
                field[row - 1][selectedCell - 1] = CellStatus.NoShips;
            }
            if (row != N - 1) {
                field[row + 1][selectedCell - 1] = CellStatus.NoShips;
            }
            field[row][selectedCell - 1] = CellStatus.NoShips;
        }
        if (selectedCell + length != M) {
            if (row != 0) {
                field[row - 1][selectedCell + length] = CellStatus.NoShips;
            }
            if (row != N - 1) {
                field[row + 1][selectedCell + length] = CellStatus.NoShips;
            }
            field[row][selectedCell + length] = CellStatus.NoShips;
        }
        for (int i = selectedCell; i < selectedCell + length; i++) {
            field[row][i] = CellStatus.Ship;
            Cell currentCell = new Cell(row, i);
            currentShip.cells.add(currentCell);
            shipCells.put(currentCell, currentShip);
            if (row != 0) {
                if (field[row - 1][i] != CellStatus.Ship) {
                    field[row - 1][i] = CellStatus.NoShips;
                }
            }
            if (row != N - 1) {
                field[row + 1][i] = CellStatus.NoShips;
            }
        }
        ships.add(currentShip);
    }

    private void placeVertically(int length, int col, int selectedCell) {
        Ship currentShip = new Ship(length);
        if (selectedCell != 0) {
            if (col != 0) {
                field[selectedCell - 1][col - 1] = CellStatus.NoShips;
            }
            if (col != M - 1) {
                field[selectedCell - 1][col + 1] = CellStatus.NoShips;
            }
            field[selectedCell - 1][col] = CellStatus.NoShips;
        }
        if (selectedCell + length != N) {
            if (col != 0) {
                field[selectedCell + length][col - 1] = CellStatus.NoShips;
            }
            if (col != M - 1) {
                field[selectedCell + length][col + 1] = CellStatus.NoShips;
            }
            field[selectedCell + length][col] = CellStatus.NoShips;
        }
        for (int i = selectedCell; i < selectedCell + length; i++) {
            field[i][col] = CellStatus.Ship;
            Cell currentCell = new Cell(i, col);
            currentShip.cells.add(currentCell);
            shipCells.put(currentCell, currentShip);
            if (col != 0) {
                if (field[i][col - 1] != CellStatus.Ship) {
                    field[i][col - 1] = CellStatus.NoShips;
                }
            }
            if (col != M - 1) {
                field[i][col + 1] = CellStatus.NoShips;
            }
        }
        ships.add(currentShip);
    }

    public void printField() {
        System.out.print(" \t");
        for (int i = 1; i < M + 1; i++){
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int i = 0; i < N; i++) {
            System.out.print((char)('A' + i) + "\t");
            for (int j = 0; j < M; j++) {
                System.out.print(field[i][j] == CellStatus.Ship ? "+\t" : "-\t");
            }
            System.out.println();
        }
    }

    /**
     * Cell status when ships are being placed.
     */
    public enum CellStatus {
        Ship,
        Water,
        NoShips
    }
}
