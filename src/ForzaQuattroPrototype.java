import java.util.Scanner;

/**
 * ForzaQuattroTest
 */
public class ForzaQuattroTest {

    static String gridToString(int[][] grid, String p1Icon, String p2Icon) {
        String out = "";

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int cell = grid[i][j];

                if (cell == 1) {
                    out += p1Icon;
                } else if (cell == 2) {
                    out += p2Icon;
                } else {
                    out += "-";
                }
            }
            out += "\n";
        }

        return out;
    }

    static int getFirstAvailableRow(int[][] grid, int col) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][col] != 0) {
                return i - 1;
            }
        }

        return grid.length - 1;
    };

    static int getWinner(int[][] grid) {
        int winner = 0;

        for (int i = grid.length - 1; i > 2; i--) {
            for (int j = 0; j < grid[i].length - 3; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }

                winner = grid[i][j];

                if (winner == grid[i - 1][j]
                        && winner == grid[i - 2][j]
                        && winner == grid[i - 3][j]) {
                    return winner;
                }
                if (winner == grid[i][j + 1]
                        && winner == grid[i][j + 2]
                        && winner == grid[i][j + 3]) {
                    return winner;
                }
                if (winner == grid[i - 1][j + 1]
                        && winner == grid[i - 2][j + 2]
                        && winner == grid[i - 3][j + 3]) {
                    return winner;
                }
                if (j >= 3 && winner == grid[i - 1][j - 1]
                        && winner == grid[i - 2][j - 2]
                        && winner == grid[i - 3][j - 3]) {
                    return winner;
                }
            }

        }

        return 0;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[][] grid = new int[6][7];

        System.out.println("numero di match: ");
        int nMatches;
        while (!input.hasNextInt() || (nMatches = input.nextInt()) > 15 || nMatches < 1 || nMatches % 2 == 0) {
            System.out.println("numero di match deve essere un numero dispari tra 1 e 15");
            input.nextLine();
        }

        int[] matches = new int[nMatches];

        input.nextLine();

        System.out.println("player name:");
        String p1Name = input.nextLine();
        System.out.println("player icon:");
        String p1Icon = input.nextLine();
        System.out.println("player name:");
        String p2Name = input.nextLine();
        System.out.println("player icon:");
        String p2Icon = input.nextLine();

        int currentPlayer;
        int turns;
        int lastWinner;
        boolean won;

        for (int i = 0; i < matches.length; i++) {
            grid = new int[6][7];

            // false p1 true p2
            currentPlayer = Math.random() > 0.5 ? 1 : 2;
            turns = 0;
            lastWinner = 0;
            won = false;

            while (!won) {
                System.out.printf(
                        "[ match %d turno %d] %s (%s) scegli colonna (1-%d): \n",
                        i + 1,
                        turns + 1,
                        currentPlayer == 1 ? p1Name : p2Name, currentPlayer == 1 ? p1Icon : p2Icon,
                        grid[0].length);
                System.out.println(gridToString(grid, p1Icon, p2Icon));

                // TODO: input checks
                int col = input.nextInt() - 1;
                int row = getFirstAvailableRow(grid, col);

                while (row == -1) {
                    System.out.println("riga piena. cambia riga");
                    col = input.nextInt();
                    row = getFirstAvailableRow(grid, col);
                }

                grid[row][col] = currentPlayer;

                currentPlayer = currentPlayer == 1 ? 2 : 1;

                lastWinner = getWinner(grid);
                won = lastWinner != 0 || turns + 1 == grid.length * grid[0].length;
                turns++;
            }

            matches[i] = lastWinner;
            System.out.printf("match %d vinto da %s!\n", i + 1, lastWinner == 1 ? p1Name : p2Name);
            System.out.println(gridToString(grid, p1Icon, p2Icon));
        }

        // TODO: find game winner
        // just cycle and count wins. whoever has most wins is the winner.

        input.close();
    }
}
