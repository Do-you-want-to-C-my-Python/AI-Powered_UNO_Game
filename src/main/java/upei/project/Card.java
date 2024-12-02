package upei.project;


public abstract class Card {
    protected final String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean canPlayOn(Card topCard);
    public abstract void play(upei.project.Game game);
}

