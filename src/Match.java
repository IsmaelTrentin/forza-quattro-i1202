/**
 * Match
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2025.01.06
 */
public class Match {

    Giocatore player1;

    Giocatore player2;

    Giocatore currentPlayer;

    Grid grid;

    int turn = 0;

    public Match(Giocatore player1, Giocatore player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.grid = new Grid();

        this.currentPlayer = null;
    }

    boolean hasEnded() {
        System.out.println(this.getWinner());
        return this.getWinner() != null || this.grid.isFull();
    }

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

        return null;
    }

    Giocatore pickRandomPlayer() {
        this.currentPlayer = Math.random() > 0.5 ? this.player1 : this.player2;

        return this.currentPlayer;
    }

    int getFirstAvailableRow(int col) {
        for (int i = 0; i < this.grid.getHeight(); i++) {
            if (this.grid.getCellAt(col, i) != null) {
                return i - 1;
            }
        }

        return grid.getHeight() - 1;
    };

    void start(GestioneInput input) {
        this.grid.resetGrid();

        this.currentPlayer = this.pickRandomPlayer();

        int col = 0;
        int row;
        while (!this.hasEnded()) {
            Ansi.clearScreen();

            if (input.isRawModeSupported()) {
                row = 0;

                boolean reading = true;
                input.setUnixRawMode();

                while (reading) {
                    Ansi.clearScreen();

                    String prompt = String.format(
                            "[turno %d] %s (%s) scegli colonna muovendoti con A e D o <- e ->:\r\n",
                            this.turn + 1,
                            this.currentPlayer.getName(),
                            this.currentPlayer.getIcon(),
                            this.grid.getWidth());
                    System.out.print(prompt);
                    System.out.println(this.grid.toStr(col, this.currentPlayer));

                    int keyCode;
                    try {
                        keyCode = System.in.read();
                    } catch (Exception e) {
                        System.err.println("IO error: failed to read stdin");
                        continue;
                    }

                    if (keyCode == 'q') {
                        input.restoreUnixTerminal();
                        System.exit(0);
                    } else if (keyCode == 68 || keyCode == 'a') {
                        col = ((col - 1) % this.grid.getWidth() + this.grid.getWidth()) % this.grid.getWidth();
                    } else if (keyCode == 67 || keyCode == 'd') {
                        col = (col + 1) % this.grid.getWidth();
                    } else if (keyCode == ' ') {
                        row = this.getFirstAvailableRow(col);

                        if (row == -1) {
                            reading = true;
                            System.out.println("riga piena!");
                            try {
                                Thread.sleep(300);
                            } catch (Exception e) {
                                System.out.println("error: failed to interrupt");
                            }
                            continue;
                        }

                        reading = false;
                    }
                }

                input.restoreUnixTerminal();
            } else {
                String prompt = String.format(
                        "[turno %d] %s (%s) scegli colonna (1-%d): ",
                        this.turn + 1,
                        this.currentPlayer.getName(),
                        this.currentPlayer.getIcon(),
                        this.grid.getWidth());

                System.out.println(this.grid.toStr());
                col = input.askRangedInt(prompt, 1, this.grid.getWidth()) - 1;
                row = this.getFirstAvailableRow(col);

                while (row == -1) {
                    col = input.askRangedInt("riga piena. cambia riga: ", 1, this.grid.getWidth()) - 1;
                    row = this.getFirstAvailableRow(col);
                }
            }

            this.grid.setCell(col, row, this.currentPlayer);

            this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;

            this.turn++;
        }
    }
}
