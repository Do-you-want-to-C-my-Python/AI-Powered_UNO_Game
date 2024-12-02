package upei.project;


public class NumberCard extends upei.project.Card {
    private final int number;

    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean canPlayOn(upei.project.Card topCard) {
        return topCard.getColor().equals(this.color) ||
                (topCard instanceof NumberCard && ((NumberCard) topCard).getNumber() == this.number);
    }

    @Override
    public void play(upei.project.Game game) {
        game.setTopCard(this);
    }

    @Override
    public String toString() {
        return color + " " + number;
    }
}

