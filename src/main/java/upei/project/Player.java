package uno;

import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    // Updated method signature to match the correct parameter type
    public Card playCard(Card topCard, String currentColor) {
        // Strategy 1: Play a card that matches the current color
        for (Card card : hand) {
            if (card.getColor().equals(currentColor)) {
                hand.remove(card);
                return card;
            }
        }

        // Strategy 2: Play a card that matches the top card's number or action
        for (Card card : hand) {
            if (card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }

        // Strategy 3: Play a Wild or Draw Four card if available
        for (Card card : hand) {
            if (card instanceof WildCard || card instanceof DrawFourCard) {
                hand.remove(card);
                return card;
            }
        }

        return null;
    }

    public String chooseColor() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        Map<String, Integer> colorCounts = new HashMap<>();

        // Initialize color counts
        for (String color : colors) {
            colorCounts.put(color, 0);
        }

        // Count the frequency of each color in hand
        for (Card card : hand) {
            if (!card.getColor().equals("Wild")) {
                colorCounts.put(card.getColor(), colorCounts.get(card.getColor()) + 1);
            }
        }

        // Choose the most frequent color
        String chosenColor = colors[0];
        int maxCount = -1;

        for (Map.Entry<String, Integer> entry : colorCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                chosenColor = entry.getKey();
            }
        }

        System.out.println(name + " chooses color: " + chosenColor);
        return chosenColor;
    }

    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        return name;
    }
}

