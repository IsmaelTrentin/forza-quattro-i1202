/**
 * Game manager class.
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2025.01.06
 */
public class Partita {

    /**
     * Input handler.
     */
    GestioneInput input;

    /**
     * The matches (rounds) in a game of 'Forza Quattro'.
     */
    Match[] matches;

    /**
     * Player 1.
     */
    Giocatore player1;

    /**
     * Player 2.
     */
    Giocatore player2;

    /**
     * Holds the total match wins for each player.
     * Index 0 is player1 and index 1 is player2.
     * 
     * @see Partita#player1
     * @see Partita#player2
     */
    int[] playerWins = new int[2];

    /**
     * Available icons that players can choose from.
     */
    String[] icons = { "🔴", "🟡", "💎", "🍕" };

    /**
     * Creates a new instance of Partita.
     * 
     * @param input the input handler
     */
    Partita(GestioneInput input) {
        this.input = input;
        this.matches = new Match[0];

        this.player1 = null;
        this.player2 = null;
    }

    /**
     * Creates a new instance of Partita with custom icons.
     * 
     * @param input the input handler
     * @param icons available icons that players can choose from
     */
    Partita(GestioneInput input, String[] icons) {
        this(input);
        this.icons = icons;
    }

    /**
     * Removes the icon <code>pickedIcon</code> from <code>this.icons</code>.
     * 
     * @param pickedIcon the icon that needs to be filtered
     * @see Partita#icons
     */
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

    /**
     * Returns the game winner.
     * 
     * @return the game winner.
     */
    Giocatore getWinner() {
        return this.playerWins[0] > this.playerWins[1] ? this.player1 : this.player2;
    }

    /**
     * Initializes the players by asking for their names and icons.
     */
    void initPlayers() {
        // player 1
        String name = this.input.askString("nome " + Ansi.cyan("giocatore1") + ": ");
        Ansi.clearScreen();
        String icon = this.input.askPlayerIcon("icona " + Ansi.cyan(name) + ": ", icons);
        Ansi.clearScreen();
        this.player1 = new Giocatore(name, icon);

        this.filterPickedIcon(icon);

        // player 2
        name = this.input.askString("nome " + Ansi.green("giocatore2") + ": ");
        while (name.equals(this.player1.getName())) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            name = this.input.askString("questo nome e' gia' in uso, riprova. nome giocatore2: ");
        }
        Ansi.clearScreen();
        icon = this.input.askPlayerIcon("icona " + name + ": ", icons);
        this.player2 = new Giocatore(name, icon);
    }

    /**
     * Initializes the matches array by asking how many matches need to be played.
     */
    void initMatches() {
        int nMatches = this.input.askNumberOfMatches("numero di match: ");
        this.matches = new Match[nMatches];
    }

    /**
     * Prints out the total wins for each player.
     */
    void printWins() {
        System.out.printf("Vittorie %s: %d\r\n", this.player1.getName(), this.playerWins[0]);
        System.out.printf("Vittorie %s: %d\r\n", this.player2.getName(), this.playerWins[1]);
    }

    /**
     * Prints out the winner message.
     */
    void printWinner() {
        Giocatore gameWinner = this.getWinner();
        System.out.printf(" ⭐️ Il vincitore della partita e': %s! ⭐️\n", gameWinner.getName());
    }

    /**
     * Starts the game by asking for the players' name and icon and the number of
     * matches. It then plays each match and determines the winner.
     */
    void start() {
        Ansi.clearScreen();

        this.initPlayers();
        Ansi.clearScreen();
        this.initMatches();
        Ansi.clearScreen();

        for (int i = 0; i < matches.length; i++) {
            Ansi.clearScreen();

            Match match = new Match(this.player1, this.player2);
            this.matches[i] = match;

            match.start(input); // blocking
            Giocatore matchWinner = match.getWinner();

            if (match.hasEnded() && matchWinner == null) {
                Ansi.clearScreen();

                System.out.println("pareggio!");
                System.out.println(match.grid.toStr());
                System.out.print("premi un qualsiasi tasto per rigiocare o q per uscire ");
                int key = input.read(); // blocking
                if (key == 'q') {
                    // Ansi.clearScreen();
                    System.out.println("partita terminata");
                    return;
                }

                // step back one iteration to repeat
                // the last game since it was a draw.
                i -= 1;
                continue;
            }

            if (matchWinner == this.player1) {
                this.playerWins[0]++;
            } else if (matchWinner == this.player2) {
                this.playerWins[1]++;
            }

            Ansi.clearScreen();
            System.out.printf("match %d vinto da %s!\n", i + 1, matchWinner.getName());
            System.out.println(match.grid.toStr());
            if (i + 1 != matches.length) {
                System.out.println("premi un qualsiasi tasto per continuare ");
                input.read();
            }
        }

        this.printWins();
        this.printWinner();
    }
}
