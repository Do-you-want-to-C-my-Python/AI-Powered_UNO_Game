package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testNumberCard() {
        NumberCard card = new NumberCard("RED", 5);
        assertEquals(5, card.getNumber());
        assertEquals("RED", card.getColor());
    }

    @Test
    void testNumberCardCanPlayOn() {
        NumberCard card1 = new NumberCard("RED", 5);
        NumberCard card2 = new NumberCard("RED", 7); // Same color
        NumberCard card3 = new NumberCard("BLUE", 5); // Same number
        NumberCard card4 = new NumberCard("BLUE", 7); // Different color and number

        assertTrue(card1.canPlayOn(card2)); // Can play on same color
        assertTrue(card1.canPlayOn(card3)); // Can play on same number
        assertFalse(card1.canPlayOn(card4)); // Cannot play on different color and number
    }

    @Test
    void testWildCard() {
        WildCard card = new WildCard();
        assertEquals("Wild", card.getColor());
        assertTrue(card.canPlayOn(new NumberCard("RED", 5))); // Wild can be played on any card
    }

    @Test
    void testDrawFourCard() {
        DrawFourCard card = new DrawFourCard();
        assertEquals("Wild", card.getColor());
        assertTrue(card.canPlayOn(new NumberCard("RED", 5))); // Draw Four can be played on any card
    }
}