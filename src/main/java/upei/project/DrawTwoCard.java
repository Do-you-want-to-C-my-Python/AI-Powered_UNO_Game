package upei.project;

/**
 * Represents a Draw Two card in UNO.
 * When played, this card forces the next player to draw two cards
 * and lose their turn, unless they can play another Draw Two card
 * to make the next player draw four cards, and so on.
 */
public class DrawTwoCard extends ActionCard {
    /**
     * Creates a new Draw Two card with the specified color.
     * @param color The color of the Draw Two card
     */
    public DrawTwoCard(String color) {
        super(color, "Draw Two");
    }

    /**
     * Determines if this card can be played on another card.
     * A Draw Two card can be played if:
     * - It matches the color of the other card
     * - The other card is also a Draw Two card (stacking)
     * - The color of the card is Wild
     * @param other The card to play on
     * @return true if this card can be played, false otherwise
     */
    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof DrawTwoCard || color.equals("Wild");
    }

    /**
     * Returns a string representation of this card.
     * @return A string in the format "color Draw Two"
     */
    @Override
    public String toString() {
        return color + " Draw Two";
    }
}