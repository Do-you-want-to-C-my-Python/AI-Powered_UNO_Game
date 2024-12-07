package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionCardTest {
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

    @Test
    void testActionCardInheritance() {
        // Test that all action cards are subclasses of ActionCard
        assertTrue(new SkipCard("Green") instanceof ActionCard);
        assertTrue(new ReverseCard("Red") instanceof ActionCard);
        assertTrue(new DrawTwoCard("Blue") instanceof ActionCard);

        // Test that they're also Card instances
        assertTrue(new SkipCard("Green") instanceof Card);
        assertTrue(new ReverseCard("Red") instanceof Card);
        assertTrue(new DrawTwoCard("Blue") instanceof Card);
    }

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