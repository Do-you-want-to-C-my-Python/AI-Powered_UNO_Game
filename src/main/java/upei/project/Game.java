package upei.project;

import java.util.*;

/**
 * Represents an UNO card game.
 * This class manages the game state, player turns, and game rules.
 */
public class Game {
    /** List of players in the game */
    private final List<Player> players;

    /** The deck of cards used in the game */
    private final Deck deck;

    /** Index of the current player */
    private int currentPlayerIndex;

    /** The card currently on top of the discard pile */
    private Card topCard;

    /** The current color in play (important for Wild cards) */
    private String currentColor;

    /** Whether the play direction is reversed */
    private boolean isReversed;

    /** Counter for accumulated Draw Two cards */
    private int drawTwoCount;

    /** Counter for accumulated Draw Four cards */
    private int drawFourCount;

    /**
     * Initializes a new UNO game with the specified players.
     * Sets up the deck, deals initial hands, and establishes the first card.
     *
     * @param players List of players participating in the game
     */
    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.currentPlayerIndex = 0;
        this.isReversed = false;
        this.drawTwoCount = 0;
        this.drawFourCount = 0;

        // Deal 7 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }

        // Draw first card, redraw if it's a Wild Draw Four
        do {
            topCard = deck.drawCard();
        } while (topCard instanceof DrawFourCard);

        // Set initial color
        currentColor = topCard.getColor();

        // Handle if first card is an action card
        if (topCard instanceof ActionCard) {
            handleInitialActionCard();
        }
    }

    /**
     * Handles special cases when the first card is an action card.
     * Applies effects like Skip, Reverse, or Draw Two.
     */
    private void handleInitialActionCard() {
        if (topCard instanceof SkipCard) {
            currentPlayerIndex = 1; // Skip first player
        } else if (topCard instanceof ReverseCard) {
            isReversed = true;
            currentPlayerIndex = players.size() - 1; // Start with last player
        } else if (topCard instanceof DrawTwoCard) {
            drawTwoCount = 1; // First player must draw two or play Draw Two
        }
    }

    /**
     * Checks if a card can be legally played on the current top card.
     * @param card The card to check
     * @return true if the card can be played, false otherwise
     */
    public boolean canPlayCard(Card card) {
        return card.canPlayOn(topCard);
    }

    /**
     * Makes a player draw the specified number of cards.
     * Also handles special cases for Draw Two and Draw Four effects.
     * @param count Number of cards to draw
     */
    public void drawCards(int count) {
        Player player = getCurrentPlayer();
        int cardsDrawn = 0;

        // Draw as many cards as possible up to count
        while (cardsDrawn < count && !deck.isEmpty()) {
            player.addCard(deck.drawCard());
            cardsDrawn++;
        }

        if (cardsDrawn < count) {
            System.out.println("Warning: Could only draw " + cardsDrawn + " cards instead of " + count + " (deck depleted)");
        }

        // Reset draw counts and skip turn after drawing
        if (drawTwoCount > 0 || drawFourCount > 0) {
            drawTwoCount = 0;
            drawFourCount = 0;
            moveToNextPlayer();
        }
    }

    /**
     * Moves to the next player based on current direction.
     * Handles both clockwise and counter-clockwise play.
     */
    public void moveToNextPlayer() {
        if (isReversed) {
            currentPlayerIndex--;
            if (currentPlayerIndex < 0) {
                currentPlayerIndex = players.size() - 1;
            }
        } else {
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        }
    }

    /**
     * Gets the next player in sequence without changing turns.
     * @return The next player
     */
    public Player getNextPlayer() {
        int nextIndex;
        if (isReversed) {
            nextIndex = currentPlayerIndex - 1;
            if (nextIndex < 0) {
                nextIndex = players.size() - 1;
            }
        } else {
            nextIndex = currentPlayerIndex + 1;
            if (nextIndex >= players.size()) {
                nextIndex = 0;
            }
        }
        return players.get(nextIndex);
    }

    /**
     * Reverses the direction of play.
     */
    public void reverseDirection() {
        isReversed = !isReversed;
    }

    /**
     * Sets a new top card and handles its effects.
     * Manages color changes, special card effects, and turn progression.
     * @param card The new top card
     */
    public void setTopCard(Card card) {
        this.topCard = card;
        if (card instanceof WildCard) {
            String chosenColor = getCurrentPlayer().chooseColor();
            ((WildCard) card).setCurrentColor(chosenColor);
            this.currentColor = chosenColor;

            if (card instanceof DrawFourCard) {
                drawFourCount++;
            }
        } else {
            this.currentColor = card.getColor();
        }

        // Handle special cards
        if (card instanceof SkipCard) {
            moveToNextPlayer(); // Skip next player
        } else if (card instanceof ReverseCard) {
            reverseDirection();
        } else if (card instanceof DrawTwoCard) {
            drawTwoCount++;
        }

        moveToNextPlayer();
    }

    /**
     * Checks if a player forgot to call UNO with one card.
     * @param player The player to check
     */
    public void checkForMissedUnoCall(Player player) {
        if (player.getHandSize() == 1 && !player.hasCalledUno()) {
            System.out.println(player + " forgot to call UNO! Drawing 2 penalty cards.");
            drawCards(2);
        }
    }

    // Getters
    public Card getTopCard() { return topCard; }
    public String getCurrentColor() { return currentColor; }
    public void setCurrentColor(String color) { this.currentColor = color; }
    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public Deck getDeck() { return deck; }
    public int getDrawTwoCount() { return drawTwoCount; }
    public int getDrawFourCount() { return drawFourCount; }

    /**
     * Checks if the game is over.
     * Game ends when a player has no cards or deck is empty.
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHandSize() == 0) {
                return true;
            }
        }
        return deck.isEmpty();
    }

    /**
     * Gets the winner based on smallest hand size.
     * @return The winning player
     */
    public Player getWinner() {
        Player winner = players.get(0);
        int minCards = winner.getHandSize();

        for (Player player : players) {
            if (player.getHandSize() < minCards) {
                winner = player;
                minCards = player.getHandSize();
            }
        }

        return winner;
    }
}
