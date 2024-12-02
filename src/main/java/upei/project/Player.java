package upei.project;


import java.util.*;

public class Player {
    private String name;
    private List<upei.project.Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(upei.project.Card card) {
        hand.add(card);
    }

    //First strategy. Player plays the first available card.
    public upei.project.Card playCard(upei.project.Card topCard) {
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    //Second strategy. Play number cards first and save the best for last :)
    public upei.project.Card playNumberCardFirst(upei.project.Card topCard) {
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard) && !(card instanceof upei.project.ActionCard)) {
                hand.remove(card);
                return card;
            }
        }
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof upei.project.ActionCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    //Third strategy. Play action cards first then numbers. Not saving the best for last :(
    public upei.project.Card playActionCardFirst(upei.project.Card topCard) {
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof upei.project.ActionCard) {
                hand.remove(card);
                return card;
            }
        }
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard) && !(card instanceof upei.project.ActionCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        return name;
    }

    // Randomly select a color
    public String chooseColor() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        return colors[new Random().nextInt(colors.length)];
    }
}
