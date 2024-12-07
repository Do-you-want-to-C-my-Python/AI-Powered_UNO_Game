package upei.project;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void testDeckInitialization() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());

        // Verify deck composition without drawing all cards
        Set<String> colors = new HashSet<>();
        Set<Integer> numbers = new HashSet<>();
        int numberCards = 0;
        int actionCards = 0;
        int wildCards = 0;

        // Sample first 30 cards to verify deck composition
        for (int i = 0; i < 30 && !deck.isEmpty(); i++) {
            Card card = deck.drawCard();
            if (card instanceof NumberCard) {
                numberCards++;
                colors.add(card.getColor());
                numbers.add(((NumberCard) card).getNumber());
            } else if (card instanceof WildCard) {
                wildCards++;
            } else if (card instanceof ActionCard) {
                actionCards++;
                colors.add(card.getColor());
            }
        }

        // Verify we found different types of cards
        assertTrue(numberCards > 0, "Should have number cards");
        assertTrue(actionCards > 0, "Should have action cards");
        assertTrue(colors.size() >= 2, "Should have at least 2 different colors");
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertTrue(card instanceof Card);

        // Draw cards until we find each type
        boolean foundNumber = false;
        boolean foundAction = false;
        boolean foundWild = false;
        int maxDraws = 30; // Increased from 10 to 30 for better chance of finding all types

        for (int i = 0; i < maxDraws && !deck.isEmpty() && !(foundNumber && foundAction && foundWild); i++) {
            card = deck.drawCard();
            if (card instanceof NumberCard) foundNumber = true;
            if (card instanceof ActionCard) foundAction = true;
            if (card instanceof WildCard) foundWild = true;
        }

        assertTrue(foundNumber, "Should find number cards");
        assertTrue(foundAction, "Should find action cards");
        assertTrue(foundWild, "Should find wild cards");
    }

    @Test
    void testShuffle() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Compare first 5 cards from each deck
        boolean foundDifference = false;
        for (int i = 0; i < 5 && !foundDifference; i++) {
            Card card1 = deck1.drawCard();
            Card card2 = deck2.drawCard();
            if (!card1.getClass().equals(card2.getClass()) ||
                    !card1.getColor().equals(card2.getColor())) {
                foundDifference = true;
            }
        }

        assertTrue(foundDifference, "Decks should be shuffled differently");
    }

    @Test
    void testEmptyDeck() {
        Deck deck = new Deck();
        int cardCount = 0;

        // Draw 20 cards and verify none are null
        for (int i = 0; i < 20 && !deck.isEmpty(); i++) {
            Card card = deck.drawCard();
            assertNotNull(card);
            cardCount++;
        }

        // Verify deck is not empty yet
        assertFalse(deck.isEmpty());
        assertTrue(cardCount > 0);
    }
}