
/**
 * Match
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2024.12.20
 */
public class Match {

    Giocatore player1;

    Giocatore player2;

    Giocatore currentPlayer;

    Giocatore winner;

    Grid grid;

    int turn = 0;

    public Match(Giocatore player1, Giocatore player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.grid = new Grid();

        this.currentPlayer = null;
        this.winner = null;
    }

    boolean hasEnded() {
        return this.grid.isFull() || this.winner != null;
    }

    Giocatore getWinner() {
        return this.winner;
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

    Giocatore calculateWinner() {
        Giocatore winner = null;

        for (int i = this.grid.getHeight() - 1; i > 2; i--) {
            for (int j = 0; j < this.grid.getHeight() - 3; j++) {
                winner = this.grid.getCellAt(j, i);

                if (winner == null) {
                    continue;
                }

                if (winner == this.grid.getCellAt(j, i - 1)
                        && winner == this.grid.getCellAt(j, i - 2)
                        && winner == this.grid.getCellAt(j, i - 3)) {
                    return winner;
                }
                if (winner == this.grid.getCellAt(j + 1, i)
                        && winner == this.grid.getCellAt(j + 2, i)
                        && winner == this.grid.getCellAt(j + 3, i)) {
                    return winner;
                }
                if (winner == this.grid.getCellAt(j + 1, i - 1)
                        && winner == this.grid.getCellAt(j + 2, i - 2)
                        && winner == this.grid.getCellAt(j + 3, i - 3)) {
                    return winner;
                }
                if (j >= 3 && winner == this.grid.getCellAt(j - 1, i - 1)
                        && winner == this.grid.getCellAt(j - 2, i - 2)
                        && winner == this.grid.getCellAt(j - 3, i - 3)) {
                    return winner;
                }
            }

        }

        return null;
    }

    void start(GestioneInput input) {
        this.grid.resetGrid();

        this.currentPlayer = this.pickRandomPlayer();

        while (!this.hasEnded()) {
            Ansi.clearScreen();

            int col;
            int row;

            if (input.isRawModeSupported()) {
                col = 0;
                row = 0;

                boolean reading = true;
                input.setUnixRawMode();

                while (reading) {
                    Ansi.clearScreen();
                    String prompt = String.format(
                            "[turno %d] %s (%s) scegli colonna muovendoti con A e D:\r\n",
                            this.turn + 1,
                            this.currentPlayer.getName(),
                            this.currentPlayer.getIcon(),
                            this.grid.getWidth());
                    System.out.print(prompt);
                    System.out.println(this.gridToString(col));

                    int keyCode;
                    try {
                        keyCode = System.in.read();
                    } catch (Exception e) {
                        System.err.println("IO error: failed to read stdin");
                        continue;
                    }

                    if (keyCode == 'w') {
                        input.restoreUnixTerminal();
                        System.exit(0);
                    } else if (keyCode == 'a') {
                        col = ((col - 1) % this.grid.getWidth() + this.grid.getWidth()) % this.grid.getWidth();
                    } else if (keyCode == 'd') {
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

                System.out.println(this.gridToString());
                col = input.askRangedInt(prompt, 1, this.grid.getWidth()) - 1;
                row = this.getFirstAvailableRow(col);

                while (row == -1) {
                    col = input.askRangedInt("riga piena. cambia riga: ", 1, this.grid.getWidth()) - 1;
                    row = this.getFirstAvailableRow(col);
                }
            }

            this.grid.setCell(col, row, this.currentPlayer);

            this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;

            this.winner = this.calculateWinner();
            this.turn++;
        }
    }

    String gridToString() {
        return this.grid.toStr();
    }

    String gridToString(int column) {
        return this.grid.toStr(column, this.currentPlayer);
    }

}
