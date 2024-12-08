package upei.project;

/**
 * Abstract base class representing a card in the UNO game.
 * Defines common properties and behaviors for all card types.
 * Subclasses include NumberCard, ActionCard, and their variants.
 */
public abstract class Card {
    /** The color of the card (Red, Blue, Green, Yellow, or Wild) */
    protected String color;

    /**
     * Creates a new card with the specified color.
     * @param color The color of the card
     */
    public Card(String color) {
        this.color = color;
    }

    /**
     * Gets the color of the card.
     * @return The card's color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the card.
     * Primarily used for Wild cards after color is chosen.
     * @param color The new color for the card
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Determines if this card can be played on top of another card.
     * Implementation varies by card type (number matching, color matching, etc.).
     * @param other The card to play on top of
     * @return true if this card can be played, false otherwise
     */
    public abstract boolean canPlayOn(Card other);

    /**
     * Returns a string representation of the card.
     * Format varies by card type but generally includes color and value/action.
     * @return String representation of the card
     */
    @Override
    public abstract String toString();
}