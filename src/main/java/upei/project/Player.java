package upei.project;

import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;
    private boolean hasCalledUno;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hasCalledUno = false;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    //First strategy. Player plays the first available card.
    public Card playCard(Card topCard) {
        // First try to play matching cards (non-wild)
        for (Card card : hand) {
            if (!(card instanceof WildCard) && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }
        // Then try wild cards if no matching cards found
        for (Card card : hand) {
            if (card instanceof WildCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    //Second strategy. Play number cards first and save the best for last :)
    public Card playNumberCardFirst(Card topCard) {
        // First try to play matching cards
        if (topCard instanceof ActionCard && !(topCard instanceof DrawTwoCard) && !(topCard instanceof DrawFourCard)) {
            for (Card card : hand) {
                if (card.getClass().equals(topCard.getClass())) {
                    hand.remove(card);
                    return card;
                }
            }
        }

        // Then try number cards
        for (Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof NumberCard) {
                hand.remove(card);
                return card;
            }
        }
        // Then try action cards
        for (Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof ActionCard) {
                hand.remove(card);
                return card;
            }
        }
        // Finally try wild cards
        for (Card card : hand) {
            if (card instanceof WildCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    //Third strategy. Play action cards first then numbers. Not saving the best for last :(
    public Card playActionCardFirst(Card topCard) {
        // First try to play matching action cards (except Draw cards)
        if (topCard instanceof ActionCard && !(topCard instanceof DrawTwoCard) && !(topCard instanceof DrawFourCard)) {
            for (Card card : hand) {
                if (card.getClass().equals(topCard.getClass())) {
                    hand.remove(card);
                    return card;
                }
            }
        }

        // Then try wild cards
        for (Card card : hand) {
            if (card instanceof WildCard) {
                hand.remove(card);
                return card;
            }
        }
        // Then try action cards
        for (Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof ActionCard) {
                hand.remove(card);
                return card;
            }
        }
        // Finally try number cards
        for (Card card : hand) {
            if (card.canPlayOn(topCard) && card instanceof NumberCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public Card playDrawTwo(Card topCard) {
        for (Card card : hand) {
            if (card instanceof DrawTwoCard && card.canPlayOn(topCard)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public Card playDrawFour() {
        for (Card card : hand) {
            if (card instanceof DrawFourCard) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public int getHandSize() {
        return hand.size();
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    @Override
    public String toString() {
        return name;
    }

    // Choose a color based on the cards in hand
    public String chooseColor() {
        Map<String, Integer> colorCounts = new HashMap<>();
        colorCounts.put("Red", 0);
        colorCounts.put("Blue", 0);
        colorCounts.put("Green", 0);
        colorCounts.put("Yellow", 0);

        // Count the frequency of each color in hand
        for (Card card : hand) {
            String cardColor = card.getColor();
            if (!cardColor.equals("Wild")) {
                colorCounts.put(cardColor, colorCounts.get(cardColor) + 1);
            }
        }

        // Find the color with the most cards
        String bestColor = "Blue"; // Default color if no other cards in hand
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : colorCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                bestColor = entry.getKey();
            }
        }

        return bestColor;
    }

    // New methods for UNO call functionality
    public void callUno() {
        this.hasCalledUno = true;
    }

    public void resetUnoCall() {
        this.hasCalledUno = false;
    }

    public boolean hasCalledUno() {
        return this.hasCalledUno;
    }
}

