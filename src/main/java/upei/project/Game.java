package uno;

import java.util.*;

public class Game {
    private List<Player> players;
    private Deck deck;
    private Card topCard;
    private int currentPlayerIndex;
    private boolean isReversed;

    public Game(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.currentPlayerIndex = 0;
        this.isReversed = false;
        dealInitialCards();
        this.topCard = deck.drawCard();
    }

    private void dealInitialCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public void play() {
        while (!isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            Card playedCard = currentPlayer.playCard(topCard);

            if (playedCard != null) {
                playedCard.play(this);
                if (currentPlayer.getHandSize() == 0) {
                    System.out.println(currentPlayer + " wins!");
                    return;
                }
            } else {
                Card drawnCard = deck.drawCard();
                currentPlayer.addCard(drawnCard);
                if (drawnCard.canPlayOn(topCard)) {
                    drawnCard.play(this);
                }
            }

            moveToNextPlayer();
        }
    }

    public void setTopCard(Card card) {
        this.topCard = card;
    }

    public void skipNextPlayer() {
        moveToNextPlayer();
    }

    public void reverseDirection() {
        isReversed = !isReversed;
    }

    public void drawCards(int numCards) {
        Player nextPlayer = getNextPlayer();
        for (int i = 0; i < numCards; i++) {
            nextPlayer.addCard(deck.drawCard());
        }
    }

    private void moveToNextPlayer() {
        if (isReversed) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private Player getNextPlayer() {
        int nextPlayerIndex = isReversed ? 
            (currentPlayerIndex - 1 + players.size()) % players.size() :
            (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    private boolean isGameOver() {
        return deck.isEmpty();
    }
}

