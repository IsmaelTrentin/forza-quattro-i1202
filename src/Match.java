/**
 * Models a match of 'Forza Quattro'.
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2025.01.06
 */
public class Match {

    /**
     * Input handler.
     */
    GestioneInput input;

    /**
     * Player 1.
     */
    Giocatore player1;

    /**
     * Player 2.
     */
    Giocatore player2;

    /**
     * The player that is playing the current turn.
     */
    Giocatore currentPlayer;

    /**
     * The grid holding the players' tokens.
     */
    Grid grid;

    /**
     * Defines the turn number.
     */
    int turn = 0;

    /**
     * Creates a new instance of Match given two players.
     * 
     * @param input   the input handler
     * @param player1 player 1
     * @param player2 player 2
     */
    public Match(GestioneInput input, Giocatore player1, Giocatore player2) {
        this.input = input;
        this.player1 = player1;
        this.player2 = player2;
        this.grid = new Grid();

        this.currentPlayer = null;
    }

    /**
     * Returns <code>true</code> if the match has ended, <code>false</code>
     * otherwise.
     * The ending condition is reached when the grid/board is full or a winning
     * sequence of token is detected.
     * 
     * @return <code>true</code> if the match has ended, <code>false</code>
     *         otherwise
     */
    boolean hasEnded() {
        return this.getWinner() != null || this.grid.isFull();
    }

    /**
     * Determines the winner for the current state of the grid.
     * It checks for a sequence of 4 tokens owned by the same player in these 4
     * directions:
     * - horizontal
     * - vertical
     * - diagonal (top left to bottom right)
     * - diagonal (top right to bottom left)
     * 
     * @return the player object that holds the winning sequence or
     *         <code>null</code> if no winner is found
     */
    Giocatore getWinner() {
        Giocatore winner = null;

        // horiz
        for (int y = 0; y < this.grid.getHeight(); y++) {
            for (int x = 0; x < this.grid.getWidth() - 3; x++) {
                winner = this.grid.getCellAt(x, y);

                if (winner != null
                        && winner == this.grid.getCellAt(x + 1, y)
                        && winner == this.grid.getCellAt(x + 2, y)
                        && winner == this.grid.getCellAt(x + 3, y)) {
                    return winner;
                }
            }
        }

        // vertical
        for (int y = this.grid.getHeight() - 1; y >= 3; y--) {
            for (int x = 0; x < this.grid.getWidth(); x++) {
                winner = this.grid.getCellAt(x, y);

                if (winner != null
                        && winner == this.grid.getCellAt(x, y - 1)
                        && winner == this.grid.getCellAt(x, y - 2)
                        && winner == this.grid.getCellAt(x, y - 3)) {
                    return winner;
                }
            }
        }

        // diag. top left to bot right
        for (int y = 0; y < this.grid.getHeight() - 3; y++) {
            for (int x = 0; x < this.grid.getWidth() - 3; x++) {
                winner = this.grid.getCellAt(x, y);

                if (winner != null
                        && winner == this.grid.getCellAt(x + 1, y + 1)
                        && winner == this.grid.getCellAt(x + 2, y + 2)
                        && winner == this.grid.getCellAt(x + 3, y + 3)) {
                    return winner;
                }
            }
        }

        // diag. top right to bot left
        for (int y = 0; y < this.grid.getHeight() - 3; y++) {
            for (int x = this.grid.getWidth() - 1; x >= 3; x--) {
                winner = this.grid.getCellAt(x, y);

                if (winner != null
                        && winner == this.grid.getCellAt(x - 1, y + 1)
                        && winner == this.grid.getCellAt(x - 2, y + 2)
                        && winner == this.grid.getCellAt(x - 3, y + 3)) {
                    return winner;
                }
            }
        }

        // no winner found
        return null;
    }

    /**
     * Picks the starting player randomly.
     * 
     * @return the starting player object
     */
    Giocatore pickRandomPlayer() {
        this.currentPlayer = Math.random() > 0.5 ? this.player1 : this.player2;

        return this.currentPlayer;
    }

    /**
     * Handles input in raw mode and returns the column index in which to insert the
     * player's token.
     * This function blocks code execution.
     * 
     * @param lastUsedCol the last used column index. Used for choice persistance
     * @return the column index in which to insert the player's token
     */
    int pickColRawMode(int lastUsedCol) {
        int col = lastUsedCol;

        boolean reading = true;
        this.input.setUnixRawMode();
        while (reading) {
            Ansi.clearScreen();

            String prompt = String.format(
                    "[turno %d] %s (%s) scegli colonna muovendoti con A e D o <- e ->:\r\n",
                    this.turn + 1,
                    this.currentPlayer.getName(),
                    this.currentPlayer.getIcon(),
                    this.grid.getWidth());
            System.out.print(prompt);
            System.out.println(this.grid.toStr(col));

            int keyCode;
            try {
                keyCode = System.in.read();
            } catch (Exception e) {
                System.err.println("IO error: failed to read stdin");
                // continue;
                return -1;
            }

            if (keyCode == 'q') {
                this.input.restoreUnixTerminal();
                System.exit(0);
            } else if (keyCode == 68 || keyCode == 'a') {
                col = ((col - 1) % this.grid.getWidth() + this.grid.getWidth()) % this.grid.getWidth();
            } else if (keyCode == 67 || keyCode == 'd') {
                col = (col + 1) % this.grid.getWidth();
            } else if (keyCode == ' ') {
                if (this.grid.getFirstAvailableRow(col) == -1) {
                    reading = true;

                    System.out.println("riga piena!");
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        System.out.println("error: failed to interrupt");
                    }
                    continue;
                }

                this.input.restoreUnixTerminal();
                reading = false;
                return col;
            }
        }

        this.input.restoreUnixTerminal();
        return -1;
    }

    /**
     * Asks for the column number in which to place the player's token.
     * This function blocks code execution until input is received.
     * 
     * @return the column index in which to place the player's token
     */
    int pickColNormalMode() {
        String prompt = String.format(
                "[turno %d] %s (%s) scegli colonna (1-%d): ",
                this.turn + 1,
                this.currentPlayer.getName(),
                this.currentPlayer.getIcon(),
                this.grid.getWidth());

        System.out.println(this.grid.toStr());
        int col = this.input.askRangedInt(prompt, 1, this.grid.getWidth()) - 1;

        while (this.grid.getFirstAvailableRow(col) == -1) {
            Ansi.clearLine();
            col = this.input.askRangedInt("riga piena. cambia riga: ", 1, this.grid.getWidth()) - 1;
        }

        return col;
    }

    /**
     * Starts the match. It picks a random starting player and then lets him pick a
     * column in which to insert his token, alternating between the players.
     * Depeding on the os, a different UI will be shown. For linux/unix, the
     * terminal will switch to raw mode and let the player pick the column by
     * pressing A, D or the arrow keys. For windows, it will simply ask for the
     * column number.
     * 
     * @param input input handler
     */
    void start() {
        this.grid.resetGrid();

        this.currentPlayer = this.pickRandomPlayer();

        int col = 0;
        int row = 0;
        while (!this.hasEnded()) {
            Ansi.clearScreen();

            col = input.isRawModeSupported() ? this.pickColRawMode(col) : this.pickColNormalMode();
            row = this.grid.getFirstAvailableRow(col);

            this.grid.setCell(col, row, this.currentPlayer);
            this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;
            this.turn++;
        }
    }
}
