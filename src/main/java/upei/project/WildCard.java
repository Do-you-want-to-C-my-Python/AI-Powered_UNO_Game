package upei.project;

/**
 * Represents a Wild card in UNO.
 * This card can be played on any other card, regardless of color.
 * When played, the player chooses a new color for the next player
 * to match.
 */
public class WildCard extends ActionCard {
    /**
     * Creates a new Wild card.
     * Wild cards initially have "Wild" as their color until played.
     */
    public WildCard() {
        super("Wild", "Wild");
    }

    /**
     * Determines if this card can be played on another card.
     * Wild cards can be played on any card at any time.
     * @param other The card to play on
     * @return true, as Wild cards can always be played
     */
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