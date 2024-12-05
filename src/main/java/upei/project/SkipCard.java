package upei.project;

public class SkipCard extends ActionCard {
    public SkipCard(String color) {
        super(color);
    }

    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof SkipCard || color.equals("Wild");
    }

    @Override
    public String toString() {
        return color + " Skip";
    }
}