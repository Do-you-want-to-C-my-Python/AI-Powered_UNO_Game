package upei.project;

import java.util.*;

/**
 * Represents a deck of UNO cards.
 * A standard UNO deck contains 108 cards:
 * - 76 Number cards (0-9 in four colors)
 * - 24 Action cards (Skip, Reverse, Draw Two in four colors)
 * - 8 Wild cards (4 regular Wild, 4 Wild Draw Four)
 */
public class Deck {
    /** List of cards in the deck */
    private final List<Card> cards;

    /**
     * Creates a new deck with all standard UNO cards.
     * The deck is automatically shuffled upon creation.
     */
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    /**
     * Initializes the deck with all standard UNO cards.
     * Card distribution follows official UNO rules:
     * - Each color (Red, Blue, Green, Yellow) has:
     *   * One zero
     *   * Two of each number 1-9
     *   * Two each of Skip, Reverse, Draw Two
     * - Four Wild cards
     * - Four Wild Draw Four cards
     */
    private void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};

        for (String color : colors) {
            // Add number cards (one 0, two of 1-9)
            cards.add(new NumberCard(color, 0));
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i));
            }

            // Add action cards (two of each type)
            cards.add(new SkipCard(color));
            cards.add(new SkipCard(color));
            cards.add(new ReverseCard(color));
            cards.add(new ReverseCard(color));
            cards.add(new DrawTwoCard(color));
            cards.add(new DrawTwoCard(color));
        }

        // Add Wild cards (4 of each type)
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard());
            cards.add(new DrawFourCard());
        }
    }

    /**
     * Shuffles the deck using Collections.shuffle.
     * This ensures random card distribution.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     * @return The drawn card, or throws exception if deck is empty
     */
    public Card drawCard() {
        if (isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Checks if the deck is empty.
     * @return true if no cards remain, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Gets the number of cards remaining in the deck.
     * @return Number of remaining cards
     */
    public int getSize() {
        return cards.size();
    }
}