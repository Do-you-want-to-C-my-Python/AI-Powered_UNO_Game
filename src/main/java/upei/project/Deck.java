package upei.project;


import java.util.*;

public class Deck {
    private List<upei.project.Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};

        for (String color : colors) {
            // Add number cards
            cards.add(new upei.project.NumberCard(color, 0));
            for (int i = 1; i <= 9; i++) {
                cards.add(new upei.project.NumberCard(color, i));
                cards.add(new upei.project.NumberCard(color, i));
            }

            // Add action cards
            cards.add(new upei.project.SkipCard(color));
            cards.add(new upei.project.SkipCard(color));
            cards.add(new upei.project.ReverseCard(color));
            cards.add(new upei.project.ReverseCard(color));
            cards.add(new upei.project.DrawTwoCard(color));
            cards.add(new upei.project.DrawTwoCard(color));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public upei.project.Card drawCard() {
        if (isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

