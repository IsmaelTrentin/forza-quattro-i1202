public class Giocatore {

    String name;

    String icon;

    Giocatore(String name, String icon) {
        this.name = name;
        this.icon = icon == null ? "?" : icon;
    }

    String getIcon() {
        return this.icon;
    }

    String getName() {
        return this.name;
    }
}
