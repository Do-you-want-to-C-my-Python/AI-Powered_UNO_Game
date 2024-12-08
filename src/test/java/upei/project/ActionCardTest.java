package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for action cards in the UNO game.
 * Tests various action card types (Skip, Reverse, Draw Two, Wild, Draw Four)
 * and their interactions with other cards.
 */
public class ActionCardTest {
    /**
     * Tests Skip card creation and basic properties.
     * Verifies color assignment and action type.
     */
    @Test
    void testSkipCard() {
        SkipCard card = new SkipCard("Blue");
        assertEquals("Blue", card.getColor());
        assertEquals("Blue Skip", card.toString());

        // Test playing rules
        assertTrue(card.canPlayOn(new SkipCard("Blue")));
        assertTrue(card.canPlayOn(new NumberCard("Blue", 5)));
        assertFalse(card.canPlayOn(new NumberCard("Red", 5)));
    }

    /**
     * Tests Reverse card creation and basic properties.
     * Verifies color assignment and action type.
     */
    @Test
    void testReverseCard() {
        ReverseCard card = new ReverseCard("Yellow");
        assertEquals("Yellow", card.getColor());
        assertEquals("Yellow Reverse", card.toString());

        // Test playing rules
        assertTrue(card.canPlayOn(new ReverseCard("Yellow")));
        assertTrue(card.canPlayOn(new NumberCard("Yellow", 7)));
        assertFalse(card.canPlayOn(new NumberCard("Green", 7)));
    }

    /**
     * Tests Draw Two card creation and basic properties.
     * Verifies color assignment and action type.
     */
    @Test
    void testDrawTwoCard() {
        DrawTwoCard card = new DrawTwoCard("Red");
        assertEquals("Red", card.getColor());
        assertEquals("Red Draw Two", card.toString());

        // Test playing rules
        assertTrue(card.canPlayOn(new DrawTwoCard("Red")));
        assertTrue(card.canPlayOn(new NumberCard("Red", 3)));
        assertFalse(card.canPlayOn(new NumberCard("Blue", 3)));
    }

    /**
     * Tests action card inheritance.
     * Verifies that all action cards are subclasses of ActionCard.
     */
    @Test
    void testActionCardInheritance() {
        // Test that all action cards are subclasses of ActionCard
        assertInstanceOf(ActionCard.class, new SkipCard("Green"));
        assertInstanceOf(ActionCard.class, new ReverseCard("Red"));
        assertInstanceOf(ActionCard.class, new DrawTwoCard("Blue"));

        // Test that they're also Card instances
        assertInstanceOf(Card.class, new SkipCard("Green"));
        assertInstanceOf(Card.class, new ReverseCard("Red"));
        assertInstanceOf(Card.class, new DrawTwoCard("Blue"));
    }

    /**
     * Tests action cards can be played on other action cards of same color.
     */
    @Test
    void testActionCardPlayingOnActionCards() {
        // Test action cards can be played on other action cards of same color
        ActionCard skipCard = new SkipCard("Blue");
        ActionCard reverseCard = new ReverseCard("Blue");
        ActionCard drawTwoCard = new DrawTwoCard("Blue");

        assertTrue(skipCard.canPlayOn(reverseCard));
        assertTrue(reverseCard.canPlayOn(drawTwoCard));
        assertTrue(drawTwoCard.canPlayOn(skipCard));
    }
}