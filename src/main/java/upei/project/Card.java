package upei.project;

public abstract class Card {
    protected String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public abstract boolean canPlayOn(Card other);
}