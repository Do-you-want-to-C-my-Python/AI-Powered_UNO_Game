package upei.project;

/**
 * Represents a Reverse card in UNO.
 * When played, this card reverses the direction of play.
 * In a two-player game, it acts like a Skip card, giving the
 * current player another turn.
 */
public class ReverseCard extends ActionCard {
    /**
     * Creates a new Reverse card with the specified color.
     * @param color The color of the Reverse card
     */
    public ReverseCard(String color) {
        super(color, "Reverse");
    }

    /**
     * Checks if this Reverse card can be played on top of another card.
     * A Reverse card can be played on top of a card with the same color,
     * another Reverse card, or a Wild card.
     * @param other The card to check
     * @return True if this Reverse card can be played on top of the other card
     */
    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof ReverseCard || color.equals("Wild");
    }

    /**
     * Returns a string representation of this Reverse card.
     * The string representation includes the color and the type of the card.
     * @return A string representation of this Reverse card
     */
    @Override
    public String toString() {
        return color + " Reverse";
    }
}