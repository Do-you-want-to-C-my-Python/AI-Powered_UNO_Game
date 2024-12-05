package upei.project;

public class ReverseCard extends ActionCard {
    public ReverseCard(String color) {
        super(color);
    }

    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof ReverseCard || color.equals("Wild");
    }

    @Override
    public String toString() {
        return color + " Reverse";
    }
}