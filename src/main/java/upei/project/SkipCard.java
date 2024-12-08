package upei.project;

/**
 * Represents a Skip card in UNO.
 * When played, this card causes the next player to lose their turn.
 * In a two-player game, it effectively gives the current player another turn.
 */
public class SkipCard extends ActionCard {
    /**
     * Creates a new Skip card with the specified color.
     * @param color The color of the Skip card
     */
    public SkipCard(String color) {
        super(color, "Skip");
    }

    /**
     * Checks if this Skip card can be played on top of another card.
     * A Skip card can be played on top of another card if they share the same color or if the other card is also a Skip card.
     * Additionally, a Skip card can be played on top of a Wild card.
     * @param other The card to check
     * @return True if this Skip card can be played on top of the other card, false otherwise
     */
    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof SkipCard || color.equals("Wild");
    }

    /**
     * Returns a string representation of this Skip card.
     * The string representation includes the color of the card and the type ("Skip").
     * @return A string representation of this Skip card
     */
    @Override
    public String toString() {
        return color + " Skip";
    }
}