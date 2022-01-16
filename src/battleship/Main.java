package battleship;

import java.security.InvalidParameterException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] intArgs = readArgs(args);
        GameLauncher gameLauncher = new GameLauncher();
        if (intArgs != null) {
            Ocean battlefield = new Ocean(intArgs[1], intArgs[2],
                    intArgs[3], intArgs[4], intArgs[5], intArgs[6], intArgs[7]);
            Game game = new Game(battlefield, intArgs[0]);
            gameLauncher.launch(game);
        }
        else {
            System.out.println("Welcome to Battleship game!\n" +
                    "\tN - ocean length (1 - 15)\n" +
                    "\tM - ocean width (1 - 15)\n" +
                    "\tSubmarines (1 cell) - number of submarines (0 - 64)\n" +
                    "\tDestroyers (2 cell) - number of destroyers (0 - 40)\n" +
                    "\tCruisers (3 cells) - number of cruisers (0 - 32)\n" +
                    "\tBattleships (4 cells) - number of battleships (0 - 24)\n" +
                    "\tCarriers  (5 cells) - number of carriers (0 - 16)\n" +
                    "\tTorpedoes - number of entire-ship-destroying hits (0 - 64)");
            System.out.println("Cell statuses:\n" +
                    "⊠ - Fired and hit.\n" +
                    "▇ - Fired and sunk.\n" +
                    "≋ - Fired and missed.\n");
            gameLauncher.launch();
        }
    }

    /**
     * Turns string arguments into integer ones.
     * @param args
     * @return Int array if conversion is successful, null if not.
     */
    private static int[] readArgs(String[] args) {
        int[] intArgs = new int[8];
        if (args.length != 8) {
            return null;
        }
        else {
            try {
                for (int i = 0; i < 8; i++) {
                    intArgs[i] = Integer.parseInt(args[i]);
                    switch (i) {
                        case 1:
                        case 2:
                            if (intArgs[i] > 0 && intArgs[i] < 16)
                                break;
                            else throw new NumberFormatException();
                        default:
                            if (intArgs[i] >= 0 && intArgs[i] < 65)
                                break;
                            else throw new NumberFormatException();
                    }
                }
            }
            catch (NumberFormatException numEx) {
                return null;
            }
            return intArgs;
        }
    }
}
