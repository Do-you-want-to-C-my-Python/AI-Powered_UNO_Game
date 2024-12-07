package upei.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Card topCard;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        topCard = new NumberCard("Red", 5);
    }

    @Test
    void testInitialState() {
        assertEquals("TestPlayer", player.toString());
        assertEquals(0, player.getHandSize());
    }

    @Test
    void testAddCard() {
        Card card = new NumberCard("Blue", 7);
        player.addCard(card);
        assertEquals(1, player.getHandSize());
    }

    @Test
    void testPlayCardFirstStrategy() {
        // Add playable card
        Card playableCard = new NumberCard("Red", 7);
        player.addCard(playableCard);
        Card played = player.playCard(topCard);
        assertNotNull(played);
        assertEquals(0, player.getHandSize());

        // Test with unplayable card
        Card unplayableCard = new NumberCard("Blue", 2);
        player.addCard(unplayableCard);
        played = player.playCard(topCard);
        assertNull(played);
        assertEquals(1, player.getHandSize());
    }

    @Test
    void testPlayNumberCardFirstStrategy() {
        // Add both number and action cards
        Card numberCard = new NumberCard("Red", 7);
        Card actionCard = new DrawTwoCard("Red");
        player.addCard(actionCard);
        player.addCard(numberCard);

        // Should play number card first
        Card played = player.playNumberCardFirst(topCard);
        assertNotNull(played);
        assertInstanceOf(NumberCard.class, played);

        // Then should play action card
        played = player.playNumberCardFirst(topCard);
        assertNotNull(played);
        assertInstanceOf(ActionCard.class, played);
    }

    @Test
    void testPlayActionCardFirstStrategy() {
        // Add both number and action cards
        Card numberCard = new NumberCard("Red", 7);
        Card actionCard = new DrawTwoCard("Red");
        player.addCard(numberCard);
        player.addCard(actionCard);

        // Should play action card first
        Card played = player.playActionCardFirst(topCard);
        assertNotNull(played);
        assertInstanceOf(ActionCard.class, played);

        // Then should play number card
        played = player.playActionCardFirst(topCard);
        assertNotNull(played);
        assertInstanceOf(NumberCard.class, played);
    }

    @Test
    void testChooseColor() {
        String color = player.chooseColor();
        assertTrue(Arrays.asList("Red", "Blue", "Green", "Yellow").contains(color));
    }

    @Test
    void testUnoCallAndReset() {
        assertFalse(player.hasCalledUno());
        player.callUno();
        assertTrue(player.hasCalledUno());
        player.resetUnoCall();
        assertFalse(player.hasCalledUno());
    }
}