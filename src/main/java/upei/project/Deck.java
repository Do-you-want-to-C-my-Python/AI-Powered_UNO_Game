package uno;

import java.util.*;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        
        for (String color : colors) {
            // Add number cards
            cards.add(new NumberCard(color, 0));
            for (int i = 1; i <= 9; i++) {
                cards.add(new NumberCard(color, i));
                cards.add(new NumberCard(color, i));
            }
            
            // Add action cards
            cards.add(new SkipCard(color));
            cards.add(new SkipCard(color));
            cards.add(new ReverseCard(color));
            cards.add(new ReverseCard(color));
            cards.add(new DrawTwoCard(color));
            cards.add(new DrawTwoCard(color));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

