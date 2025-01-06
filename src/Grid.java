/**
 * Models a grid/board for the 'Forza Quattro' game.
 * 
 * @author Ismael Trentin
 * @author Ivan Paljevic
 * @version 2025.01.06
 */
public class Grid {

    /**
     * The default width.
     */
    public static final int DEFAULT_W = 7;

    /**
     * The default height.
     */
    public static final int DEFAULT_H = 6;

    /**
     * The game grid. We store Giocatore so that we can directly access the player's
     * information.
     * If a cell is null, it means there is no token placed into it. Otherwise, the
     * contained player's object defines the token's owner.
     */
    Giocatore[][] grid;

    /**
     * Creates a new Grid instance with height <code>h</code> and width
     * <code>w</code>
     * 
     * @param h the grid's height
     * @param w the grid's width
     */
    Grid(int h, int w) {
        this.grid = new Giocatore[h][w];
    }

    /**
     * Creates a new Grid instance with default dimensions.
     * 
     * @see Grid#DEFAULT_H
     * @see Grid#DEFAULT_W
     */
    Grid() {
        this(DEFAULT_H, DEFAULT_W);
    }

    /**
     * Returns the grid's width.
     * 
     * @return the grid's width.
     */
    int getWidth() {
        return this.grid[0].length;
    }

    /**
     * Returns the grid's height.
     * 
     * @return the grid's height.
     */
    int getHeight() {
        return this.grid.length;
    }

    /**
     * Resets the grid by setting every cell to <code>null</code>.
     */
    void resetGrid() {
        int h = this.getHeight();
        int w = this.getWidth();

        this.grid = new Giocatore[h][w];
    }

    /**
     * Returns the first available row index for the column at index
     * <code>col</code>.
     * 
     * @param col the column to look in
     * @return the first available row index for the column at index
     *         <code>col</code>
     */
    int getFirstAvailableRow(int col) {
        for (int i = 0; i < this.grid.length; i++) {
            if (this.grid[i][col] != null) {
                return i - 1;
            }
        }

        return grid.length - 1;
    };

    /**
     * Returns the contents of the cell at <code>(x, y)</code>.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return the contents of the cell at <code>(x, y)</code>.
     */
    Giocatore getCellAt(int x, int y) {
        return this.grid[y][x];
    }

    /**
     * Sets the contents of the cell at <code>(x, y)</code>.
     * 
     * @param x      x coordinate
     * @param y      y coordinate
     * @param player the new value of the cell
     */
    void setCell(int x, int y, Giocatore player) {
        this.grid[y][x] = player;
    }

    /**
     * Returns true if the cell at <code>(x, y)</code> is <code>null</code> (empty).
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the cell at <code>(x, y)</code> is <code>null</code>, false
     *         otherwise.
     */
    boolean isCellEmpty(int x, int y) {
        return this.getCellAt(x, y) == null;
    }

    /**
     * Returns true if the grid has no empty cells.
     * 
     * @return true if the grid has no empty cells, false otherwise.
     */
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

    /**
     * Returns a string representation of this object.
     * I've created this method since we cannot use access modifiers and overloading
     * without them is not permitted.
     * 
     * @return a string representation of this object.
     */
    String toStr() {
        return this.toStr(-1);
    }

    /**
     * Returns a string representation of this object coloring the column at index
     * <code>column</code>.
     * 
     * @param column the selected column index
     * @return a string representation of this this object.
     */
    String toStr(int column) {
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
}
