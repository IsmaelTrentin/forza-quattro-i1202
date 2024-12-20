/**
 * Game manager class.
 *
 * @author Ismael Trentin
 * @version 2024.12.19
 */
public class GameManager {

    GestioneInput input;

    Giocatore[][] grid;

    Giocatore[] matches;

    String[] icons = { "🔴", "🟡", "💎", "🍕" };

    Giocatore[] players;

    public GameManager(GestioneInput input, int maxPlayers) {
        this.input = input;
        this.resetGrid();
        matches = new Giocatore[0];
        players = new Giocatore[maxPlayers];
    }

    public GameManager(GestioneInput input) {
        this(input, 2);
    }

    void resetGrid() {
        this.grid = new Giocatore[6][7];
    }

    String gridToString() {
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

    void filterPickedIcon(String pickedIcon) {
        String[] newIcons = new String[this.icons.length - 1];

        int offset = 0;
        for (int i = 0; i < this.icons.length; i++) {
            if (this.icons[i].equals(pickedIcon)) {
                offset++;
                continue;
            }

            newIcons[i - offset] = this.icons[i];
        }

        this.icons = newIcons;
    }

    Giocatore pickRandomPlayer() {
        for (int i = 0; i < this.players.length; i++) {
            if (Math.random() > 0.5) {
                continue;
            }

            return this.players[i];
        }

        return this.players[0];
    }

    void start() {
        int nMatches = input.askNumberOfMatches("numero di match: ");
        Ansi.clearScreen();
        matches = new Giocatore[nMatches];

        input.clearBuffer();

        // player 1
        String name = input.askString("nome giocatore1: ");
        Ansi.clearScreen();
        String icon = input.askPlayerIcon("icona " + name + ": ", icons);
        Ansi.clearScreen();
        this.players[0] = new Giocatore(name, icon);

        filterPickedIcon(icon);

        // player 2
        name = input.askString("nome giocatore2: ");
        while (name.equals(this.players[0].getName())) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            name = input.askString("questo nome e' gia' in uso, riprova. nome giocatore2: ");
        }
        Ansi.clearScreen();
        icon = input.askPlayerIcon("icona " + name + ": ", icons);
        Ansi.clearScreen();
        this.players[1] = new Giocatore(name, icon);

        for (int i = 0; i < matches.length; i++) {
            Ansi.clearScreen();
            this.resetGrid();

            Giocatore currentPlayer = this.pickRandomPlayer();
            int turns = 0;
            Giocatore lastMatchWinner = null;
            boolean won = false;

            while (!won) {
                Ansi.clearScreen();
                String prompt = String.format(
                        "[match %d turno %d] %s (%s) scegli colonna (1-%d): ",
                        i + 1,
                        turns + 1,
                        currentPlayer.getName(),
                        currentPlayer.getIcon(),
                        this.grid[0].length);

                System.out.println(this.gridToString());
                int col = input.askRangedInt(prompt, 1, this.grid[0].length) - 1;
                int row = getFirstAvailableRow(col);

                while (row == -1) {
                    col = input.askRangedInt("riga piena. cambia riga: ", 1, this.grid[0].length) - 1;
                    row = getFirstAvailableRow(col);
                }

                this.grid[row][col] = currentPlayer;

                currentPlayer = currentPlayer == this.players[0] ? this.players[1] : this.players[0];

                lastMatchWinner = this.getMatchWinner();
                won = lastMatchWinner != null || turns + 1 == this.grid.length * this.grid[0].length;
                turns++;
            }

            this.matches[i] = lastMatchWinner;
            System.out.println(gridToString());
            System.out.printf("match %d vinto da %s!\n", i + 1, lastMatchWinner.getName());
        }

        // TODO: find game winner
        // just cycle and count wins. whoever has most wins is the winner.
    }
}
