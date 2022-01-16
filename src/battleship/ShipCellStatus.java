package battleship;

/**
 * Status of the cell at game.
 */
public enum ShipCellStatus {
    Unknown, // Not fired.
    Hit, // Fired and hit.
    Sunk, // Fired and sunk.
    Water // Fired and missed.
}
