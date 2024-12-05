package upei.project;

public class WildCard extends Card {
    public WildCard() {
        super("Wild");
    }

    @Override
    public boolean canPlayOn(Card other) {
        // Wild cards can be played on any card
        return true;
    }

    @Override
    public String toString() {
        if (color.equals("Wild")) {
            return "Wild";
        }
        return "Wild (" + color + ")";
    }

    public void setCurrentColor(String newColor) {
        this.color = newColor;
    }
}