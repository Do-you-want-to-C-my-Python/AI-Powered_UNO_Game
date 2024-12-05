package upei.project;

import java.util.*;

public class Game {
    private List<Player> players;
    private Deck deck;
    private Card topCard;
    private String currentColor;
    private int currentPlayerIndex;
    private boolean isReversed;
    private int drawTwoCount = 0;
    private int drawFourCount = 0;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.currentPlayerIndex = 0;
        this.isReversed = false;

        // Deal initial cards
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }

        // Set initial top card (keep drawing until we get a number card)
        do {
            topCard = deck.drawCard();
        } while (!(topCard instanceof NumberCard));

        currentColor = topCard.getColor();
    }

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

    public void reverseDirection() {
        isReversed = !isReversed;
        if (players.size() == 2) {
            moveToNextPlayer(); // In 2-player game, reverse acts like skip
        }
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHandSize() == 0) {
                return true;
            }
        }
        return deck.isEmpty();
    }

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

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card card) {
        this.topCard = card;
        if (card instanceof WildCard) {
            String chosenColor = getCurrentPlayer().chooseColor();
            ((WildCard) card).setCurrentColor(chosenColor);
            this.currentColor = chosenColor;
            System.out.println(getCurrentPlayer() + " chose " + chosenColor);

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

        moveToNextPlayer(); // Move to next player
    }

    public boolean canPlayCard(Card card) {
        if (drawTwoCount > 0 && !(card instanceof DrawTwoCard)) {
            return false; // Player must play a Draw Two or draw cards
        }
        if (drawFourCount > 0 && !(card instanceof DrawFourCard)) {
            return false; // Player must play a Draw Four or draw cards
        }

        if (card instanceof WildCard) {
            return true;
        }

        return card.getColor().equals(currentColor) ||
                (topCard instanceof NumberCard && card instanceof NumberCard &&
                        ((NumberCard) topCard).getNumber() == ((NumberCard) card).getNumber());
    }

    public void drawCards(int count) {
        Player player = getCurrentPlayer();
        for (int i = 0; i < count && !deck.isEmpty(); i++) {
            player.addCard(deck.drawCard());
        }
        if (drawTwoCount > 0 || drawFourCount > 0) {
            drawTwoCount = 0;
            drawFourCount = 0;
            moveToNextPlayer(); // Skip turn after drawing for Draw Two or Draw Four
        }
    }

    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String color) {
        this.currentColor = color;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getDrawTwoCount() {
        return drawTwoCount;
    }

    public int getDrawFourCount() {
        return drawFourCount;
    }

    public void checkForMissedUnoCall(Player player) {
        if (player.getHandSize() == 1 && !player.hasCalledUno()) {
            System.out.println(player + " forgot to call UNO! Drawing 2 cards as penalty.");
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
        }
    }
}

