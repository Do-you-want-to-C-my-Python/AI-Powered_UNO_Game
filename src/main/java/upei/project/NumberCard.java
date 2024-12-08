package upei.project;

/**
 * Represents a number card in the UNO game.
 * Number cards have a color and a number value from 0 to 9.
 * They can be played on cards of the same color or number.
 */
public class NumberCard extends Card {
    /** The number value on the card (0-9) */
    private final int number;

    /**
     * Creates a new number card with specified color and number.
     * @param color The card's color
     * @param number The card's number (0-9)
     * @throws IllegalArgumentException if number is not between 0 and 9
     */
    public NumberCard(String color, int number) {
        super(color);
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Number must be between 0 and 9");
        }
        this.number = number;
    }

    /**
     * Gets the number value of this card.
     * @return The card's number value
     */
    public int getNumber() {
        return number;
    }

    /**
     * Determines if this card can be played on another card.
     * A number card can be played if it matches either:
     * - The color of the other card
     * - The number of the other card (if it's also a number card)
     * @param other The card to play on
     * @return true if this card can be played, false otherwise
     */
    @Override
    public boolean canPlayOn(Card other) {
        if (other instanceof NumberCard) {
            return color.equals(other.getColor()) || number == ((NumberCard) other).getNumber();
        }
        return color.equals(other.getColor());
    }

    /**
     * Returns a string representation of the card.
     * Format: "[Color] [Number]"
     * @return String representation of the card
     */
    @Override
    public String toString() {
        return color + " " + number;
    }
}