/**
 * 
 * @author Ivan Paljevic
 * @version 2025.01.06
 */
public class StartMenu {

  /**
   * Prints the start menu and waits for input.
   * 
   * @param input input handler
   */
  void show(GestioneInput input) {
    Ansi.clearScreen();

    String[] headerLines = {
        "     /$$$$$$$$/$$$$$$  /$$$$$$$  /$$$$$$$$  /$$$$$$         /$$$$$$  /$$   /$$  /$$$$$$  /$$$$$$$$/$$$$$$$$/$$$$$$$   /$$$$$$ ",
        "    | $$_____/$$__  $$| $$__  $$|_____ $$  /$$__  $$       /$$__  $$| $$  | $$ /$$__  $$|__  $$__/__  $$__/ $$__  $$ /$$__  $$",
        "    | $$    | $$  \\ $$| $$  \\ $$     /$$/ | $$  \\ $$      | $$  \\ $$| $$  | $$| $$  \\ $$   | $$     | $$  | $$  \\ $$| $$  \\ $$",
        "    | $$$$$ | $$  | $$| $$$$$$$/    /$$/  | $$$$$$$$      | $$  | $$| $$  | $$| $$$$$$$$   | $$     | $$  | $$$$$$$/| $$  | $$",
        "    | $$__/ | $$  | $$| $$__  $$   /$$/   | $$__  $$      | $$  | $$| $$  | $$| $$__  $$   | $$     | $$  | $$__  $$| $$  | $$",
        "    | $$    | $$  | $$| $$  \\ $$  /$$/    | $$  | $$      | $$/$$ $$| $$  | $$| $$  | $$   | $$     | $$  | $$  \\ $$| $$  | $$",
        "    | $$    |  $$$$$$/| $$  | $$ /$$$$$$$$| $$  | $$      |  $$$$$$/|  $$$$$$/| $$  | $$   | $$     | $$  | $$  | $$|  $$$$$$/",
        "    |__/     \\______/ |__/  |__/|________/|__/  |__/       \\____ $$$ \\______/ |__/  |__/   |__/     |__/  |__/  |__/ \\______/ ",
        "                                                                \\__/                                                          "
    };

    System.out.println();
    System.out.println();
    for (String line : headerLines) {
      System.out.println(Ansi.red(line));
    }

    System.out.println();
    System.out.println();
    System.out.print(Ansi.cyan("    -> "));
    System.out.print(Ansi.yellow(Ansi.bold("premi un tasto per giocare")));
    System.out.print(Ansi.cyan(" <-"));
    System.out.println();
    System.out.println();
    System.out.print(Ansi.fg(Ansi.italic("    //    Ivan Paljevic & Ismael Trentin    //"), 90, 92, 90));
    System.out.println();
    System.out.println();

    input.read();
  }
}
