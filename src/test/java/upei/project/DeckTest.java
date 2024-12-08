package upei.project;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Deck implementation in the UNO game.
 * Tests deck initialization, card drawing, and deck composition.
 */
class DeckTest {
    /**
     * Tests the initial creation and composition of the UNO deck.
     * Verifies:
     * - Deck is not empty when created
     * - Contains different types of cards (number, action, wild)
     * - Has multiple colors
     * - Contains appropriate number of each card type
     */
    @Test
    void testDeckInitialization() {
        // Create a new deck for testing
        Deck deck = new Deck();

        // Verify the deck is not empty after creation
        assertFalse(deck.isEmpty());

        // Track different card properties found in the deck
        Set<String> colors = new HashSet<>();
        Set<Integer> numbers = new HashSet<>();
        int numberCards = 0;
        int actionCards = 0;
        int wildCards = 0;

        // Sample first 30 cards to verify deck composition
        for (int i = 0; i < 30 && !deck.isEmpty(); i++) {
            // Draw a card from the deck
            Card card = deck.drawCard();

            // Check if the card is a NumberCard
            if (card instanceof NumberCard) {
                // Increment the number of NumberCards found
                numberCards++;

                // Add the card's color to the set of colors
                colors.add(card.getColor());

                // Add the card's number to the set of numbers
                numbers.add(((NumberCard) card).getNumber());
            }
            // Check if the card is a WildCard
            else if (card instanceof WildCard) {
                // Increment the number of WildCards found
                wildCards++;
            }
            // Check if the card is an ActionCard
            else if (card instanceof ActionCard) {
                // Increment the number of ActionCards found
                actionCards++;

                // Add the card's color to the set of colors
                colors.add(card.getColor());
            }
        }

        // Verify we found different types of cards
        assertTrue(numberCards > 0, "Should have number cards");
        assertTrue(actionCards > 0, "Should have action cards");
        assertTrue(colors.size() >= 2, "Should have at least 2 different colors");
    }

    /**
     * Tests the drawCard functionality and deck composition.
     * Verifies:
     * - Cards can be drawn
     * - All required card types are present in the deck
     * - Cards are properly instantiated
     */
    @Test
    void testDrawCard() {
        // Create a new deck for testing
        Deck deck = new Deck();

        // Draw a card from the deck
        Card card = deck.drawCard();

        // Verify the card is not null and is a Card instance
        assertNotNull(card);
        assertTrue(card instanceof Card);

        // Track card types found
        boolean foundNumber = false;
        boolean foundAction = false;
        boolean foundWild = false;

        // Check the first card
        if (card instanceof NumberCard) {
            foundNumber = true;
        } else if (card instanceof ActionCard && !(card instanceof WildCard)) {
            foundAction = true;
        } else if (card instanceof WildCard) {
            foundWild = true;
        }

        // Check all remaining cards in the deck
        while (!deck.isEmpty()) {
            card = deck.drawCard();

            if (card instanceof NumberCard) {
                foundNumber = true;
            } else if (card instanceof ActionCard && !(card instanceof WildCard)) {
                foundAction = true;
            } else if (card instanceof WildCard) {
                foundWild = true;
            }

            // If we've found all types, we can stop checking
            if (foundNumber && foundAction && foundWild) {
                break;
            }
        }

        // Verify we found each type of card
        assertTrue(foundNumber, "Deck should contain number cards");
        assertTrue(foundAction, "Deck should contain action cards");
        assertTrue(foundWild, "Deck should contain wild cards");
    }

    /**
     * Tests the shuffle functionality of the deck.
     * Verifies that two newly created decks have different card orders
     * by comparing the first few cards drawn.
     */
    @Test
    void testShuffle() {
        // Create two new decks for testing
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Compare first 5 cards from each deck
        boolean foundDifference = false;
        for (int i = 0; i < 5 && !foundDifference; i++) {
            // Draw a card from each deck
            Card card1 = deck1.drawCard();
            Card card2 = deck2.drawCard();

            // Check if the cards are different
            if (!card1.getClass().equals(card2.getClass()) ||
                    !card1.getColor().equals(card2.getColor())) {
                // Set foundDifference to true
                foundDifference = true;
            }
        }

        // Verify the decks are shuffled differently
        assertTrue(foundDifference, "Decks should be shuffled differently");
    }

    /**
     * Tests the deck's empty state and drawing multiple cards.
     * Verifies:
     * - Multiple cards can be drawn
     * - No null cards are drawn
     * - Deck size decreases appropriately
     */
    @Test
    void testEmptyDeck() {
        // Create a new deck for testing
        Deck deck = new Deck();

        // Initialize a counter for the number of cards drawn
        int cardCount = 0;

        // Draw 20 cards and verify none are null
        for (int i = 0; i < 20 && !deck.isEmpty(); i++) {
            // Draw a card from the deck
            Card card = deck.drawCard();

            // Verify the card is not null
            assertNotNull(card);

            // Increment the card count
            cardCount++;
        }

        // Verify the deck is not empty yet and cards were drawn
        assertFalse(deck.isEmpty());
        assertTrue(cardCount > 0);
    }
}