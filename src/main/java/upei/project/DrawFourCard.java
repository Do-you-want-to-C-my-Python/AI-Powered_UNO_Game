package upei.project;

/**
 * Represents a Wild Draw Four card in UNO.
 * This is the most powerful card in the game. When played:
 * - The next player must draw 4 cards and lose their turn,
 *   unless they have another Draw Four card
 * - The current player chooses the next color
 * According to official rules, this card can only be played when
 * the player has no cards matching the current color, though this
 * rule is often ignored in casual play.
 */
public class DrawFourCard extends WildCard {
    /**
     * Creates a new Wild Draw Four card.
     * Like regular Wild cards, it starts with "Wild" as its color.
     */
    public DrawFourCard() {
        super();
    }

    /**
     * Returns a string representation of this card.
     * If the color is "Wild", returns "Wild Draw Four".
     * Otherwise, returns "Wild Draw Four (color)".
     * @return a string representation of this card
     */
    @Override
    public String toString() {
        if (color.equals("Wild")) {
            return "Wild Draw Four";
        }
        return "Wild Draw Four (" + color + ")";
    }
}