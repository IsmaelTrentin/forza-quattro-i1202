/**
 * Models a player for the game 'Forza Quattro'.
 * 
 * @author Ivan Paljevic
 * @version 2025.01.06
 */
public class Giocatore {

    /**
     * The player's name.
     */
    String name;

    /**
     * The player's token icon.
     */
    String icon;

    /**
     * Creates a new player.
     * 
     * @param name the player name
     * @param icon the player icon
     */
    Giocatore(String name, String icon) {
        this.name = name;
        this.icon = icon == null ? "?" : icon;
    }

    /**
     * Returns the player name.
     * 
     * @return the player name.
     */
    String getName() {
        return this.name;
    }

    /**
     * Returns the player icon.
     * 
     * @return the player icon.
     */
    String getIcon() {
        return this.icon;
    }
}
