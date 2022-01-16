package battleship;

import java.util.Arrays;

public class Game {

    private ShipCellStatus[][] gameField;
    private Ocean ocean;

    public final int torpedoesCount;
    private int torpedoesLeft;


    /**
     * Game class performs user's turns.
     * @param ocean Battlefield.
     * @param torpedoes Number of torpedoes owned by user.
     */
    public Game(Ocean ocean, int torpedoes) {
        this.ocean = ocean;
        gameField = new ShipCellStatus[ocean.N][ocean.M];
        for (ShipCellStatus[] row : gameField) {
            Arrays.fill(row, ShipCellStatus.Unknown);
        }
        torpedoesCount = torpedoes;
        torpedoesLeft = torpedoes;
    }

    /**
     * @return If any ships left.
     */
    public boolean shipsLeft() {
        return ocean.ships.size() > 0;
    }

    /**
     * @return Length of the ocean.
     */
    public int getN() {
        return ocean.N;
    }

    /**
     * @return Width of the ocean.
     */
    public int getM() {
        return ocean.M;
    }

    /**
     * @return Number of torpedoes used.
     */
    public int currentTorpedoesCount() {
        return torpedoesLeft;
    }

    /**
     * To print battlefield in a user-friendly way.
     * @return Ocean
     */
    @Override
    public String toString() {
        String field = "";
        field += " \t";
        for (int i = 1; i < ocean.M + 1; i++){
            field += i + "\t";
        }
        field += "\n";
        for (int i = 0; i < ocean.N; i++) {
            field += (char)('A' + i) + "\t";
            for (int j = 0; j < ocean.M; j++) {
                field += (Cell.cellType(gameField[i][j]));
            }
            field += "\n";
        }
        return field;
    }

    /**
     * Check if cell belongs to any ship. If it does hit it or mark sunk. If it does not, it is water.
     * @param torpedo Hit by torpedo or a usual one.
     * @param hitCell Cell to hit.
     */
    public void makeTurn(boolean torpedo, Cell hitCell) {
        for (Cell cell : ocean.shipCells.keySet()) {
            if (cell.equals(hitCell)) {
                if (torpedo) {
                    torpedoesLeft--;
                    for (Cell c : ocean.shipCells.get(cell).cells) {
                        if (c != cell) {
                            GameLauncher.shotCells.add(c);
                            ocean.shipCells.remove(c);
                        }
                    }
                    removeShip(ocean.shipCells.get(cell));
                }
                else if (ocean.shipCells.get(cell).hit(cell)) {
                    removeShip(ocean.shipCells.get(cell));
                }
                else {
                    gameField[cell.getX()][cell.getY()] = ShipCellStatus.Hit;
                }
                ocean.shipCells.remove(cell);
                return;
            }
        }
        gameField[hitCell.getX()][hitCell.getY()] = ShipCellStatus.Water;
    }

    /**
     * Destroy any memory about the ship.
     * @param ship Ship to be removed.
     */
    private void removeShip(Ship ship) {
        for (int i = 0; i < ocean.ships.size(); i++) {
            if (ocean.ships.get(i) == ship) {
                for (Cell cell : ship.cells) {
                    gameField[cell.getX()][cell.getY()] = ShipCellStatus.Sunk;
                }
                ocean.ships.remove(ship);
                break;
            }
        }
        System.out.println("You have sunk a " + ship);
    }
}
