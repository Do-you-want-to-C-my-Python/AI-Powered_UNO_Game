package uno;

public class NumberCard extends Card {
    private final int number;

    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return topCard.getColor().equals(this.color) || 
               (topCard instanceof NumberCard && ((NumberCard) topCard).getNumber() == this.number);
    }

    @Override
    public void play(Game game) {
        game.setTopCard(this);
    }

    @Override
    public String toString() {
        return color + " " + number;
    }
}

