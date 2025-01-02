/**
 * Game manager class.
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2024.12.20
 */
public class Partita {

    Match[] matches;

    String[] icons = { "🔴", "🟡", "💎", "🍕" };

    Giocatore player1;
    Giocatore player2;

    public Partita() {
        this.matches = new Match[0];

        this.player1 = null;
        this.player2 = null;
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

    Giocatore getWinner() {
        int p1Wins = 0;
        int p2Wins = 0;

        for (Match match : this.matches) {
            Giocatore winner = match.getWinner();
            if (winner == this.player1) {
                p1Wins++;
            } else if (winner == this.player2) {
                p2Wins++;
            }
        }

        return p1Wins > p2Wins ? this.player1 : this.player2;
    }

    void start(GestioneInput input) {
        // player 1
        String name = input.askString("nome giocatore1: ");
        Ansi.clearScreen();
        String icon = input.askPlayerIcon("icona " + name + ": ", icons);
        Ansi.clearScreen();
        this.player1 = new Giocatore(name, icon);

        this.filterPickedIcon(icon);

        // player 2
        name = input.askString("nome giocatore2: ");
        while (name.equals(this.player1.getName())) {
            System.out.print(Ansi.CURSOR_UP);
            Ansi.clearLine();
            name = input.askString("questo nome e' gia' in uso, riprova. nome giocatore2: ");
        }
        Ansi.clearScreen();
        icon = input.askPlayerIcon("icona " + name + ": ", icons);
        Ansi.clearScreen();
        this.player2 = new Giocatore(name, icon);

        int nMatches = input.askNumberOfMatches("numero di match: ");
        Ansi.clearScreen();
        matches = new Match[nMatches];

        for (int i = 0; i < matches.length; i++) {
            Ansi.clearScreen();

            Match match = new Match(this.player1, this.player2);
            this.matches[i] = match;

            match.start(input); // blocking
            Giocatore winner = match.getWinner();

            if (match.hasEnded() && winner == null) {
                input.askString("nessun vincitore! premi enter per rigiocare il match");

                // step back one iteration and then
                // restart with the same index.
                i -= 1;
                continue;
            }

            Ansi.clearScreen();
            System.out.println(match.gridToString());
            System.out.printf("match %d vinto da %s!\n", i + 1, winner.getName());
        }

        Giocatore matchWinner = this.getWinner();

        System.out.println();
        System.out.printf(" ⭐️ Il vincitore della partita e': %s %s! ⭐️\n", matchWinner.getIcon(),
                matchWinner.getName());
    }
}
