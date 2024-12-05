package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionCardTest {
    @Test
    void testSkipCard() {
        SkipCard card = new SkipCard("BLUE");
        assertEquals("BLUE", card.getColor());
    }

    @Test
    void testReverseCard() {
        ReverseCard card = new ReverseCard("YELLOW");
        assertEquals("YELLOW", card.getColor());
    }

    @Test
    void testDrawTwoCard() {
        DrawTwoCard card = new DrawTwoCard("RED");
        assertEquals("RED", card.getColor());
    }
}