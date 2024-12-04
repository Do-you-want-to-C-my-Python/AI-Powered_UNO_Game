package upei.project;


import java.util.*;

public class Game {
    protected List<upei.project.Player> players;
    protected upei.project.Deck deck;
    protected upei.project.Card topCard;
    protected int currentPlayerIndex;
    protected boolean isReversed;
    protected String currentColor;

    public Game(List<upei.project.Player> players) {
        this.players = players;
        this.deck = new upei.project.Deck();
        this.currentPlayerIndex = 0;
        this.isReversed = false;
        dealInitialCards();
        this.topCard = deck.drawCard();
        this.currentColor = topCard.getColor();
    }

    protected void dealInitialCards() {
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
                    setCurrentColor(currentColor);
                    if (drawnCard.canPlayOn(topCard)) {
                        System.out.println(currentPlayer + " plays drawn card: " + drawnCard);
                        setCurrentColor(drawnCard.getColor());
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
        String tempColor = currentColor;
        this.currentColor = color;
        if(!currentColor.equals(tempColor)) {
            System.out.println("Color changed to: " + color);
        }
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

    protected void moveToNextPlayer() {
        if (isReversed) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public upei.project.Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    protected upei.project.Player getNextPlayer() {
        int nextPlayerIndex = isReversed ?
                (currentPlayerIndex - 1 + players.size()) % players.size() :
                (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    protected boolean isGameOver() {
        return deck.isEmpty();
    }

    public upei.project.Card getTopCard() {
        return topCard;
    }

    public String getCurrentColor() {
        return currentColor;
    }

    public int getCurrentPlayerIndex()
    {
        return currentPlayerIndex;
    }

    public Deck getDeck()
    {
        return deck;
    }
}

