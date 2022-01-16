package battleship;

import java.security.InvalidParameterException;
import java.util.*;

public class GameLauncher {
    private Scanner scanner = new Scanner(System.in);
    private int turns = 0;
    public static Set<Cell> shotCells = new HashSet<>();

    /**
     * Launches game with user's input.
     */
    public void launch() {
        Ocean battlefield = null;
        do {
            battlefield = setOcean();
        } while (battlefield == null);
        System.out.print("Number of torpedoes: ");
        int torpedoes = readInt(64);
        Game game = new Game(battlefield, torpedoes);
        play(game);
        System.out.println(turns + " turn" + (turns % 10 == 1 ? "." : "s."));
        System.out.println("Accuracy: " + (100 * battlefield.initialCellsCount / shotCells.size()) + "%");
    }

    /**
     * Launches game that is already created.
     * @param craftGame
     */
    public void launch(Game craftGame) {
        play(craftGame);
    }

    /**
     * User makes shots until no ships left.
     * @param game Game to be played.
     */
    private void play(Game game) {
        scanner.nextLine();
        while (game.shipsLeft()) {
            System.out.println("Torpedoes: " + game.currentTorpedoesCount() + "/" + game.torpedoesCount);
            System.out.println(game);
            System.out.print("Make turn:\n" +
                    (game.currentTorpedoesCount() > 0 ? "Torpedo (1 - off, 2 - on): " : ""));
            boolean torpedo = game.currentTorpedoesCount() > 0 && readInt(2) == 2;
            if (torpedo) {
                scanner.nextLine();
            }
            Cell turn = readTurn(game.getN(), game.getM());
            game.makeTurn(torpedo, turn);
            turns++;
        }
        System.out.println(game);
    }

    /**
     * Locate ships defined by user.
     * @return Matrix of cells. Some of them are ships.
     */
    private Ocean setOcean() {
        int n, m, submarines, destroyers, cruisers, battleships, carriers;
        System.out.print("Enter length: ");
        n = readInt(15);
        System.out.print("Enter width: ");
        m = readInt(15);
        System.out.print("Enter number of submarines: ");
        submarines = readInt(64);
        System.out.print("Enter number of destroyers: ");
        destroyers = readInt(40);
        System.out.print("Enter number of cruisers: ");
        cruisers = readInt(32);
        System.out.print("Enter number of battleships: ");
        battleships = readInt(24);
        System.out.print("Enter number of carriers: ");
        carriers = readInt(16);
        Ocean ocean;
        try {
            ocean = new Ocean(n, m, submarines, destroyers, cruisers, battleships, carriers);
        }
        catch (InvalidParameterException parameterException) {
            System.out.println(parameterException.getMessage());
            System.out.println("Try again!");
            return null;
        }
        return ocean;
    }

    private Cell readTurn(int xLim, int yLim) {
        System.out.print("Enter cell (\"A 4\", \"C 8\", etc.): ");
        Cell hitCell = new Cell(-1, -1);
        Cell invalidCell = new Cell(-1, -1);
        do {
            String line = scanner.nextLine();
            String[] input = line.toLowerCase().split(" ");
            int x = -1, y = -1;
            try {
                NumberFormatException numex = new NumberFormatException("Incorrect format! " +
                        "Should be \"A 4\", \"C 8\", etc.");
                if (input.length == 2 & input[0].length() == 1) {
                    char xSym = input[0].toCharArray()[0];
                    if (xSym - '0' < 10) {
                        throw numex;
                    }
                    x = input[0].toCharArray()[0] - 'a';
                    y = Integer.parseInt(input[1]) - 1;
                    if (x >= xLim || x < 0 || y >= yLim || y < 0) {
                        throw new IllegalArgumentException("Out of the borders!");
                    }
                    if (shotCells.contains(new Cell(x, y))) {
                        throw new IllegalArgumentException("Cell is already shot!");
                    }
                    hitCell = new Cell(x, y);
                } else throw numex;
            }
            catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }
            catch (IllegalArgumentException illEx) {
                System.out.println(illEx.getMessage());
            }
        } while (hitCell.equals(invalidCell));
        shotCells.add(hitCell);
        return hitCell;
    }

    private int readInt(int limit) {
        int value = -1;
        do {
            try {
                value = scanner.nextInt();
                if (value < 0 || value > limit) {
                    value = -1;
                    throw new NumberFormatException("Incorrect value!\nShould be from 1 to " + limit);
                }
            } catch (InputMismatchException mismatchException) {
                value = -1;
                scanner.nextLine();
                System.out.println("Not an integer");
            } catch (NumberFormatException numberFormatException) {
                System.out.println(numberFormatException.getMessage());
            }
        } while (value == -1);
        return value;
    }

}
