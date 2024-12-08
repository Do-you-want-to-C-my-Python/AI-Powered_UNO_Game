package upei.project;

/**
 * Abstract base class for all action cards in UNO.
 * Action cards include Skip, Reverse, Draw Two, Wild, and Wild Draw Four.
 * Each action card has a specific effect on gameplay when played.
 */
public abstract class ActionCard extends Card {
    /** The type of action (e.g., "Skip", "Reverse", "Draw Two", etc.) */
    protected String action;

    /**
     * Creates a new action card with specified color and action type.
     * @param color The card's color
     * @param action The type of action
     */
    public ActionCard(String color, String action) {
        super(color);
        this.action = action;
    }

    /**
     * Gets the type of action for this card.
     * @return The action type
     */
    public String getAction() {
        return action;
    }

    /**
     * Determines if this card can be played on another card.
     * An action card can be played if it matches:
     * - The color of the other card
     * - The action type of the other card
     * Wild cards have special rules defined in their subclasses.
     * @param other The card to play on
     * @return true if this card can be played, false otherwise
     */
    @Override
    public boolean canPlayOn(Card other) {
        if (other instanceof ActionCard) {
            return color.equals(other.getColor()) ||
                    action.equals(((ActionCard) other).getAction());
        }
        return color.equals(other.getColor());
    }

    /**
     * Returns a string representation of the action card.
     * Format: "[Color] [Action]"
     * @return String representation of the card
     */
    @Override
    public String toString() {
        return color + " " + action;
    }
}