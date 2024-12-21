/**
 *
 * @author Ismael Trentin
 * @version 2024.12.20
 */
public class Grid {

    public static final int DEFAULT_W = 7;

    public static final int DEFAULT_H = 6;

    // we store Giocatore so that we inheritly store
    // the player's information.
    // null -> no token
    // Giocatore -> the player's token described by
    // the player's attributes.
    Giocatore[][] grid;

    Grid(int h, int w) {
        this.grid = new Giocatore[h][w];
    }

    Grid() {
        this(DEFAULT_H, DEFAULT_W);
    }

    int getWidth() {
        return this.grid[0].length;
    }

    int getHeight() {
        return this.grid.length;
    }

    void resetGrid() {
        int h = this.getHeight();
        int w = this.getWidth();

        this.grid = new Giocatore[h][w];
    }

    String toStr() {
        String out = "";

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                Giocatore cell = this.grid[i][j];

                out += cell != null ? cell.getIcon() : "-";
            }
            out += "\n";
        }

        return out;
    }

    int getFirstAvailableRow(int col) {
        for (int i = 0; i < this.grid.length; i++) {
            if (this.grid[i][col] != null) {
                return i - 1;
            }
        }

        return grid.length - 1;
    };

    Giocatore getMatchWinner() {
        Giocatore winner = null;

        for (int i = this.grid.length - 1; i > 2; i--) {
            for (int j = 0; j < this.grid[i].length - 3; j++) {
                if (this.grid[i][j] == null) {
                    continue;
                }

                winner = this.grid[i][j];

                if (winner == this.grid[i - 1][j]
                        && winner == this.grid[i - 2][j]
                        && winner == this.grid[i - 3][j]) {
                    return winner;
                }
                if (winner == this.grid[i][j + 1]
                        && winner == this.grid[i][j + 2]
                        && winner == this.grid[i][j + 3]) {
                    return winner;
                }
                if (winner == this.grid[i - 1][j + 1]
                        && winner == this.grid[i - 2][j + 2]
                        && winner == this.grid[i - 3][j + 3]) {
                    return winner;
                }
                if (j >= 3 && winner == this.grid[i - 1][j - 1]
                        && winner == this.grid[i - 2][j - 2]
                        && winner == this.grid[i - 3][j - 3]) {
                    return winner;
                }
            }

        }

        return null;
    }

    Giocatore getCellAt(int x, int y) {
        return this.grid[y][x];
    }

    void setCell(int x, int y, Giocatore player) {
        this.grid[y][x] = player;
    }

    // void emptyCellAt(int x, int y) {
    // this.setCell(x, y, null);
    // }

    boolean isCellEmpty(int x, int y) {
        return this.getCellAt(x, y) == null;
    }

    // boolean isCellOwnedBy(int x, int y, Giocatore player) {
    // // == comparasion because we want only one instance for each player
    // return getCellAt(x, y) == player;
    // }

    boolean isFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (isCellEmpty(j, i)) {
                    return false;
                }
            }

        }

        return true;
    }
}
