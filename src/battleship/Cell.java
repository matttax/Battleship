package battleship;

import java.util.Objects;

public class Cell {
    final private int X;
    final private int Y;
    public ShipCellStatus shipCellStatus;

    /**
     * Cell is a unit of the ship.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Cell(int x, int y) {
        X = x;
        Y = y;
        shipCellStatus = ShipCellStatus.Unknown;
    }

    // Coordinates.
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return X == cell.X && Y == cell.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    /**
     * @return Cell in a user-friendly way.
     */
    public static String cellType(ShipCellStatus cell) {
        return switch (cell) {
            case Unknown -> " \t";
            case Hit -> "⊠\t";
            case Sunk -> "▇\t";
            case Water -> "≋\t";
        };
    }
}
