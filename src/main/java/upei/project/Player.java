package upei.project;

import java.util.*;

/**
 * Represents a player in the UNO game.
 * Manages the player's hand and implements different playing strategies.
 */
public class Player {
    /** The player's name */
    private final String name;

    /** The player's current hand of cards */
    private final List<Card> hand;

    /** Tracks whether the player has called UNO */
    private boolean hasCalledUno;

    /**
     * Creates a new player with the specified name.
     * @param name The player's name
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hasCalledUno = false;
    }

    /**
     * First strategy: Play the first available matching card.
     * Simple strategy that plays the first card that matches color or number.
     * @param topCard The current top card
     * @return The card to play, or null if no playable card
     */
    public Card playCard(Card topCard) {
        // First check if we need to respond to a Draw card
        if (topCard instanceof DrawTwoCard) {
            for (Card card : hand) {
                if (card instanceof DrawTwoCard) {
                    hand.remove(card);
                    return card;
                }
            }
        } else if (topCard instanceof DrawFourCard) {
            for (Card card : hand) {
                if (card instanceof DrawFourCard) {
                    hand.remove(card);
                    return card;
                }
            }
        }

        // If no Draw card to respond with, play first valid card
        for (Card card : hand) {
            if (card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    /**
     * Second strategy: Play number cards first.
     * Prioritizes playing number cards before action cards.
     * @param topCard The current top card
     * @return The card to play, or null if no playable card
     */
    public Card playNumberCardFirst(Card topCard) {
        // First check if we need to respond to a Draw card
        if (topCard instanceof DrawTwoCard) {
            for (Card card : hand) {
                if (card instanceof DrawTwoCard) {
                    hand.remove(card);
                    return card;
                }
            }
        } else if (topCard instanceof DrawFourCard) {
            for (Card card : hand) {
                if (card instanceof DrawFourCard) {
                    hand.remove(card);
                    return card;
                }
            }
        }

        // First try to play number cards
        for (Card card : hand) {
            if (card instanceof NumberCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // Then try action cards
        for (Card card : hand) {
            if (card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    /**
     * Third strategy: Play action cards first.
     * Prioritizes playing action and special cards before number cards.
     * @param topCard The current top card
     * @return The card to play, or null if no playable card
     */
    public Card playActionCardFirst(Card topCard) {
        // First check if we need to respond to a Draw card
        if (topCard instanceof DrawTwoCard) {
            for (Card card : hand) {
                if (card instanceof DrawTwoCard) {
                    hand.remove(card);
                    return card;
                }
            }
        } else if (topCard instanceof DrawFourCard) {
            for (Card card : hand) {
                if (card instanceof DrawFourCard) {
                    hand.remove(card);
                    return card;
                }
            }
        }

        // Try cards in order of priority:
        // 1. Draw Four cards (can always be played)
        for (Card card : hand) {
            if (card instanceof DrawFourCard) {
                hand.remove(card);
                return card;
            }
        }

        // 2. Wild cards
        for (Card card : hand) {
            if (card instanceof WildCard && !(card instanceof DrawFourCard)) {
                hand.remove(card);
                return card;
            }
        }

        // 3. Draw Two cards
        for (Card card : hand) {
            if (card instanceof DrawTwoCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // 4. Skip cards
        for (Card card : hand) {
            if (card instanceof SkipCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // 5. Reverse cards
        for (Card card : hand) {
            if (card instanceof ReverseCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // 6. Other action cards
        for (Card card : hand) {
            if (card instanceof ActionCard && !(card instanceof WildCard) && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // 7. Finally, number cards
        for (Card card : hand) {
            if (card instanceof NumberCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        return null;
    }

    /**
     * Attempts to play a Draw Two card in response to another Draw Two.
     * @param topCard The current top card
     * @return A Draw Two card if available, null otherwise
     */
    public Card playDrawTwo(Card topCard) {
        for (Card card : hand) {
            if (card instanceof DrawTwoCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    /**
     * Attempts to play a Draw Four card in response to another Draw Four.
     * @return A Draw Four card if available, null otherwise
     */
    public Card playDrawFour() {
        for (Card card : hand) {
            if (card instanceof DrawFourCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    /**
     * Chooses a color when playing a Wild card.
     * Currently implements a simple strategy of choosing the most common color in hand.
     * @return The chosen color
     */
    public String chooseColor() {
        Map<String, Integer> colorCounts = new HashMap<>();
        for (Card card : hand) {
            if (!card.getColor().equals("Wild")) {
                colorCounts.merge(card.getColor(), 1, Integer::sum);
            }
        }

        // Default colors if hand is empty or only has wild cards
        String[] defaultColors = {"Red", "Blue", "Green", "Yellow"};
        if (colorCounts.isEmpty()) {
            return defaultColors[new Random().nextInt(defaultColors.length)];
        }

        // Return color with highest count
        return Collections.max(colorCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Marks that the player has called UNO.
     */
    public void callUno() {
        this.hasCalledUno = true;
    }

    /**
     * Resets the UNO call status.
     */
    public void resetUnoCall() {
        this.hasCalledUno = false;
    }

    /**
     * Adds a card to the player's hand.
     * @param card The card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    // Getters
    public List<Card> getHand() { return hand; }
    public int getHandSize() { return hand.size(); }
    public boolean hasCalledUno() { return hasCalledUno; }

    @Override
    public String toString() {
        return name;
    }
}
