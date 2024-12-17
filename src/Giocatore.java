public class Giocatore {

    String name;

    String icon;

    public Giocatore(String name, String icon) {
        this.name = name;
        this.icon = icon != null ? "" + icon.charAt(0) : "X";
    }

    public String getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }
}
