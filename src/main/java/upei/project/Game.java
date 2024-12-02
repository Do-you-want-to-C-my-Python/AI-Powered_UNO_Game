package upei.project;


import java.util.*;

public class Game {
    private List<upei.project.Player> players;
    private upei.project.Deck deck;
    private upei.project.Card topCard;
    private int currentPlayerIndex;
    private boolean isReversed;
    private String currentColor;

    public Game(List<upei.project.Player> players) {
        this.players = players;
        this.deck = new upei.project.Deck();
        this.currentPlayerIndex = 0;
        this.isReversed = false;
        dealInitialCards();
        this.topCard = deck.drawCard();
        this.currentColor = topCard.getColor();
    }

    private void dealInitialCards() {
        for (upei.project.Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public void play() {
        while (!isGameOver()) {
            upei.project.Player currentPlayer = getCurrentPlayer();
            System.out.println("\nCurrent player: " + currentPlayer);
            System.out.println("Top card: " + topCard + ", Current color: " + currentColor);

            // Updated to use the correct method signature
            upei.project.Card playedCard = currentPlayer.playCard(topCard);
            if (playedCard != null) {
                System.out.println(currentPlayer + " plays " + playedCard);
                playedCard.play(this);
                setCurrentColor(playedCard.getColor());
                if (currentPlayer.getHandSize() == 0) {
                    System.out.println(currentPlayer + " wins!");
                    return;
                }
            } else {
                if (!deck.isEmpty()) {
                    upei.project.Card drawnCard = deck.drawCard();
                    System.out.println(currentPlayer + " draws a card");
                    currentPlayer.addCard(drawnCard);
                    if (drawnCard.canPlayOn(topCard)) {
                        System.out.println(currentPlayer + " plays drawn card: " + drawnCard);
                        drawnCard.play(this);
                    }
                }
            }

            moveToNextPlayer();
        }
        System.out.println("Game over. Deck is empty.");
    }

    public void setTopCard(upei.project.Card card) {
        this.topCard = card;
    }

    public void setCurrentColor(String color) {
        this.currentColor = color;
        System.out.println("Color changed to: " + color);
    }

    public void skipNextPlayer() {
        moveToNextPlayer();
    }

    public void reverseDirection() {
        isReversed = !isReversed;
    }

    public void drawCards(int numCards) {
        upei.project.Player nextPlayer = getNextPlayer();
        for (int i = 0; i < numCards; i++) {
            if (!deck.isEmpty()) {
                nextPlayer.addCard(deck.drawCard());
            }
        }
    }

    private void moveToNextPlayer() {
        if (isReversed) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public upei.project.Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private upei.project.Player getNextPlayer() {
        int nextPlayerIndex = isReversed ?
                (currentPlayerIndex - 1 + players.size()) % players.size() :
                (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    private boolean isGameOver() {
        return deck.isEmpty();
    }

    public upei.project.Card getTopCard() {
        return topCard;
    }

    public String getCurrentColor() {
        return currentColor;
    }
}

