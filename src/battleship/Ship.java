package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    public List<Cell> cells;
    private int length;

    /**
     * Ship is the main essence in the game.
     * @param length Length of the ship.
     */
    public Ship(int length) {
        this.length = length;
        cells = new ArrayList<>();
    }

    /**
     * @return Ship type name.
     */
    @Override
    public String toString() {
        return switch (length) {
            case 1 -> "submarine.";
            case 2 -> "destroyer.";
            case 3 -> "cruiser.";
            case 4 -> "battleship.";
            default -> "carrier.";
        };
    }

    /**
     * Change ship cells' status.
     * @param cell Cell to be hit.
     * @return If the ship is sunk.
     */
    public boolean hit(Cell cell) {
        for (int i = 0; i < length; i++) {
            if (cells.get(i).getX() == cell.getX() & cells.get(i).getY() == cell.getY()) {
                if (lastHit()) {
                    sink();
                    return true;
                }
                else {
                    cells.get(i).shipCellStatus = ShipCellStatus.Hit;
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @return If the ship is sunk.
     */
    private boolean lastHit() {
        int cnt = 0;
        for (Cell c : cells) {
            if (c.shipCellStatus == ShipCellStatus.Hit) {
                cnt++;
            }
        }
        return cnt == cells.size() - 1;
    }

    /**
     * Make all ship's cells sunk.
     */
    private void sink() {
        for (Cell cell : cells) {
            cell.shipCellStatus = ShipCellStatus.Sunk;
        }
    }
}
