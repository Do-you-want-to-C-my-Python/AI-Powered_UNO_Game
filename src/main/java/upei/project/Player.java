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

    public upei.project.Card playCard(upei.project.Card topCard) {
        for (upei.project.Card card : hand) {
            if (card.canPlayOn(topCard)) {
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


    public String chooseColor() {
        // Randomly select a color
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        return colors[new Random().nextInt(colors.length)];
    }
}
