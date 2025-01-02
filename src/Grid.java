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
        return this.toStr(-1, null);
    }

    String toStr(int column, Giocatore currentPlayer) {
        int rows = this.grid.length;
        int cols = this.grid[0].length;
        int row;
        int col;
        String out = "";

        // top border
        out += column == 0 ? Ansi.cyan("\u250C") : "\u250C";
        for (col = 0; col < cols; col++) {
            out += col == column ? Ansi.cyan("\u2500\u2500\u2500") : "\u2500\u2500\u2500";
            if (col < cols - 1) {
                out += col == column || col == column - 1 ? Ansi.cyan("\u252C") : "\u252C";
            }
        }
        out += column == cols - 1 ? Ansi.cyan("\u2510\r\n") : "\u2510\r\n";

        // rows and cols
        for (row = 0; row < rows; row++) {
            out += column == 0 ? Ansi.cyan("\u2502") : "\u2502";
            for (col = 0; col < cols; col++) {
                // cell content
                out += " x".replace("x",
                        this.grid[row][col] == null ? "  " : this.grid[row][col].getIcon());
                out += col == column || col == column - 1 ? Ansi.cyan("\u2502") : "\u2502";
            }
            out += "\r\n";

            // separator
            if (row < rows - 1) {
                out += column == 0 ? Ansi.cyan("\u251C") : "\u251C";
                for (col = 0; col < cols; col++) {
                    out += col == column ? Ansi.cyan("\u2500\u2500\u2500") : "\u2500\u2500\u2500";
                    if (col < cols - 1) {
                        out += col == column || col == column - 1 ? Ansi.cyan("\u253C") : "\u253C";
                    }
                }
                out += column == cols - 1 ? Ansi.cyan("\u2524\r\n") : "\u2524\r\n";
            }
        }

        // bottom border
        out += column == 0 ? Ansi.cyan("\u2514") : "\u2514";
        for (col = 0; col < cols; col++) {
            out += col == column ? Ansi.cyan("\u2500\u2500\u2500") : "\u2500\u2500\u2500";
            if (col < cols - 1) {
                out += col == column || col == column - 1 ? Ansi.cyan("\u2534") : "\u2534";
            }
        }
        out += column == cols - 1 ? Ansi.cyan("\u2518\r\n") : "\u2518\r\n";

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
