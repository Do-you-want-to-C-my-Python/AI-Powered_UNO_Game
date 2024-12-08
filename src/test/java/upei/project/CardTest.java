package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Card implementations in the UNO game.
 * Tests various card types, their behaviors, and interactions.
 */
public class CardTest {
    /**
     * Tests NumberCard creation and basic functionality.
     * Verifies:
     * - Color assignment
     * - Number assignment
     * - String representation
     * - Card matching rules
     */
    @Test
    void testNumberCard() {
        // Create a new NumberCard with color "Red" and number 5
        NumberCard card = new NumberCard("Red", 5);

        // Verify color assignment
        assertEquals(5, card.getNumber());
        assertEquals("Red", card.getColor());

        // Verify string representation
        assertEquals("Red 5", card.toString());

        // Test playing on other cards
        assertTrue(card.canPlayOn(new NumberCard("Red", 7))); // Same color
        assertTrue(card.canPlayOn(new NumberCard("Blue", 5))); // Same number
        assertFalse(card.canPlayOn(new NumberCard("Blue", 7))); // Different color and number
    }

    /**
     * Tests NumberCard canPlayOn functionality.
     * Verifies:
     * - Card matching rules with various card types
     */
    @Test
    void testNumberCardCanPlayOn() {
        // Create NumberCards for testing
        NumberCard card1 = new NumberCard("Red", 5);
        NumberCard card2 = new NumberCard("Red", 7); // Same color
        NumberCard card3 = new NumberCard("Blue", 5); // Same number
        NumberCard card4 = new NumberCard("Blue", 7); // Different color and number

        // Verify card matching rules
        assertTrue(card1.canPlayOn(card2), "Should play on same color");
        assertTrue(card1.canPlayOn(card3), "Should play on same number");
        assertFalse(card1.canPlayOn(card4), "Should not play on different color and number");
    }

    /**
     * Tests WildCard creation and functionality.
     * Verifies:
     * - Initial wild color
     * - Color changing capability
     * - String representation before and after color change
     * - Card matching rules
     */
    @Test
    void testWildCard() {
        // Create a new WildCard
        WildCard card = new WildCard();

        // Verify initial wild color
        assertEquals("Wild", card.getColor());
        assertEquals("Wild", card.toString());

        // Test playing on any card
        assertTrue(card.canPlayOn(new NumberCard("Red", 5)));
        assertTrue(card.canPlayOn(new DrawTwoCard("Blue")));

        // Test color change
        card.setCurrentColor("Blue");
        assertEquals("Blue", card.getColor());
        assertEquals("Wild (Blue)", card.toString());
    }

    /**
     * Tests DrawFourCard creation and functionality.
     * Verifies:
     * - Initial wild color
     * - Color changing capability
     * - String representation before and after color change
     * - Card matching rules
     */
    @Test
    void testDrawFourCard() {
        // Create a new DrawFourCard
        DrawFourCard card = new DrawFourCard();

        // Verify initial wild color
        assertEquals("Wild", card.getColor());
        assertEquals("Wild Draw Four", card.toString());

        // Test playing on any card
        assertTrue(card.canPlayOn(new NumberCard("Red", 5)));
        assertTrue(card.canPlayOn(new DrawTwoCard("Blue")));

        // Test color change
        card.setCurrentColor("Green");
        assertEquals("Green", card.getColor());
        assertEquals("Wild Draw Four (Green)", card.toString());
    }

    /**
     * Tests ActionCards creation and functionality.
     * Verifies:
     * - Color assignment
     * - String representation
     * - Card matching rules with various card types
     */
    @Test
    void testActionCards() {
        // Create ActionCards for testing
        SkipCard skipCard = new SkipCard("Blue");
        ReverseCard reverseCard = new ReverseCard("Yellow");
        DrawTwoCard drawTwoCard = new DrawTwoCard("Red");

        // Verify color assignment
        assertEquals("Blue", skipCard.getColor());
        assertEquals("Yellow", reverseCard.getColor());
        assertEquals("Red", drawTwoCard.getColor());

        // Verify string representation
        assertEquals("Blue Skip", skipCard.toString());
        assertEquals("Yellow Reverse", reverseCard.toString());
        assertEquals("Red Draw Two", drawTwoCard.toString());

        // Test playing on same color
        assertTrue(skipCard.canPlayOn(new NumberCard("Blue", 5)));
        assertTrue(reverseCard.canPlayOn(new NumberCard("Yellow", 7)));
        assertTrue(drawTwoCard.canPlayOn(new NumberCard("Red", 3)));

        // Test playing on different color
        assertFalse(skipCard.canPlayOn(new NumberCard("Red", 5)));
        assertFalse(reverseCard.canPlayOn(new NumberCard("Blue", 7)));
        assertFalse(drawTwoCard.canPlayOn(new NumberCard("Green", 3)));
    }
}