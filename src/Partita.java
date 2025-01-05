/**
 * Game manager class.
 *
 * @author Ivan Paljevic
 * @author Ismael Trentin
 * @version 2025.01.03
 */
public class Partita {

    Match[] matches;

    String[] icons = { "🔴", "🟡", "💎", "🍕" };

    Giocatore player1;
    Giocatore player2;

    int[] playerWins = new int[2];

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
        // int p1Wins = 0;
        // int p2Wins = 0;
        //
        // for (Match match : this.matches) {
        // Giocatore winner = match.getWinner();
        // if (winner == this.player1) {
        // p1Wins++;
        // } else if (winner == this.player2) {
        // p2Wins++;
        // }
        // }
        //
        // return p1Wins > p2Wins ? this.player1 : this.player2;
        return this.playerWins[0] > this.playerWins[1] ? this.player1 : this.player2;
    }

    void start(GestioneInput input) {
        Ansi.clearScreen();

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

        // number of matches
        int nMatches = input.askNumberOfMatches("numero di match: ");
        Ansi.clearScreen();
        matches = new Match[nMatches];

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

        Giocatore gameWinner = this.getWinner();

        System.out.println();
        System.out.printf("Vittorie %s: %d\r\n", this.player1.getName(), this.playerWins[0]);
        System.out.printf("Vittorie %s: %d\r\n", this.player2.getName(), this.playerWins[1]);
        System.out.printf(" ⭐️ Il vincitore della partita e': %s! ⭐️\n", gameWinner.getName());
    }
}
