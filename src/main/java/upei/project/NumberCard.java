package upei.project;

public class NumberCard extends Card {
    private int number;

    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) ||
                (other instanceof NumberCard &&
                        number == ((NumberCard) other).getNumber());
    }

    @Override
    public String toString() {
        return color + " " + number;
    }
}