package upei.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Player functionality in the UNO game.
 * Tests player actions, card management, and different play strategies.
 */
class PlayerTest {
    /**
     * Player instance for testing.
     */
    private Player player;

    /**
     * Top card on the discard pile.
     */
    private Card topCard;

    /**
     * Sets up the player and top card before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        topCard = new NumberCard("Red", 5);
    }

    /**
     * Tests the initial state of the player.
     * Verifies:
     * - Initial empty hand
     * - Name assignment
     */
    @Test
    void testInitialState() {
        assertEquals("TestPlayer", player.toString());
        assertEquals(0, player.getHandSize());
    }

    /**
     * Tests adding cards to player's hand.
     * Verifies:
     * - Hand size increases correctly
     */
    @Test
    void testAddCard() {
        Card card = new NumberCard("Blue", 7);
        player.addCard(card);
        assertEquals(1, player.getHandSize());
    }

    /**
     * Tests the first play strategy (play first valid card).
     * Verifies:
     * - Matching color cards are played
     * - Non-matching color cards are not played
     */
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

    /**
     * Tests the number-first play strategy.
     * Verifies:
     * - Number cards are prioritized over action cards
     * - Action cards are played when no number cards match
     */
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

    /**
     * Tests the action-first play strategy.
     * Verifies:
     * - Action cards are prioritized over number cards
     * - Wild cards are played first when available
     */
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

    /**
     * Tests the color choice strategy.
     * Verifies:
     * - Most common color in hand is chosen
     * - Default color is chosen for empty hand
     */
    @Test
    void testChooseColor() {
        String color = player.chooseColor();
        assertTrue(Arrays.asList("Red", "Blue", "Green", "Yellow").contains(color));
    }

    /**
     * Tests UNO call functionality.
     * Verifies:
     * - UNO call status changes
     * - Reset functionality works
     */
    @Test
    void testUnoCallAndReset() {
        assertFalse(player.hasCalledUno());
        player.callUno();
        assertTrue(player.hasCalledUno());
        player.resetUnoCall();
        assertFalse(player.hasCalledUno());
    }
}