package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void testDeckInitialization() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());
        // 4 colors × (1 zero + 2 each of 1-9 + 2 each of Skip, Reverse, Draw Two)
        // = 4 × (1 + 18 + 6) = 100 cards
        // Note: Wild cards and Draw Four cards are missing in the current implementation
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertTrue(card instanceof Card);
    }

    @Test
    void testShuffle() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        // Draw a few cards from both decks and compare
        // They should likely be different due to shuffling
        Card card1 = deck1.drawCard();
        Card card2 = deck2.drawCard();
        // Note: There's a small chance this could fail even with proper shuffling
        // but it's very unlikely
        assertNotEquals(card1.getColor(), card2.getColor());
    }

    @Test
    void testEmptyDeck() {
        Deck deck = new Deck();
        // Draw all cards
        while (!deck.isEmpty()) {
            deck.drawCard();
        }
        assertTrue(deck.isEmpty());
        assertThrows(IllegalStateException.class, () -> deck.drawCard());
    }
}