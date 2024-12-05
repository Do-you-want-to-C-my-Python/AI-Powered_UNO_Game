package upei.project;

public class DrawTwoCard extends ActionCard {
    public DrawTwoCard(String color) {
        super(color);
    }

    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || other instanceof DrawTwoCard || color.equals("Wild");
    }

    @Override
    public String toString() {
        return color + " Draw Two";
    }
}