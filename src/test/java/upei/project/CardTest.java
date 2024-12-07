package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testNumberCard() {
        NumberCard card = new NumberCard("Red", 5);
        assertEquals(5, card.getNumber());
        assertEquals("Red", card.getColor());
        assertEquals("Red 5", card.toString());
    }

    @Test
    void testNumberCardCanPlayOn() {
        NumberCard card1 = new NumberCard("Red", 5);
        NumberCard card2 = new NumberCard("Red", 7); // Same color
        NumberCard card3 = new NumberCard("Blue", 5); // Same number
        NumberCard card4 = new NumberCard("Blue", 7); // Different color and number

        assertTrue(card1.canPlayOn(card2), "Should play on same color");
        assertTrue(card1.canPlayOn(card3), "Should play on same number");
        assertFalse(card1.canPlayOn(card4), "Should not play on different color and number");
    }

    @Test
    void testWildCard() {
        WildCard card = new WildCard();
        assertEquals("Wild", card.getColor());
        assertEquals("Wild", card.toString());

        // Test playing on other cards
        assertTrue(card.canPlayOn(new NumberCard("Red", 5)));
        assertTrue(card.canPlayOn(new DrawTwoCard("Blue")));

        // Test color change
        card.setCurrentColor("Blue");
        assertEquals("Blue", card.getColor());
        assertEquals("Wild (Blue)", card.toString());
    }

    @Test
    void testDrawFourCard() {
        DrawFourCard card = new DrawFourCard();
        assertEquals("Wild", card.getColor());
        assertEquals("Wild Draw Four", card.toString());

        // Test playing on other cards
        assertTrue(card.canPlayOn(new NumberCard("Red", 5)));
        assertTrue(card.canPlayOn(new DrawTwoCard("Blue")));

        // Test color change
        card.setCurrentColor("Green");
        assertEquals("Green", card.getColor());
        assertEquals("Wild Draw Four (Green)", card.toString());
    }

    @Test
    void testActionCards() {
        SkipCard skipCard = new SkipCard("Blue");
        ReverseCard reverseCard = new ReverseCard("Yellow");
        DrawTwoCard drawTwoCard = new DrawTwoCard("Red");

        // Test colors
        assertEquals("Blue", skipCard.getColor());
        assertEquals("Yellow", reverseCard.getColor());
        assertEquals("Red", drawTwoCard.getColor());

        // Test toString
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