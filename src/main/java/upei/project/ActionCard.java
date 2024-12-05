package upei.project;

public abstract class ActionCard extends Card {
    public ActionCard(String color) {
        super(color);
    }

    @Override
    public boolean canPlayOn(Card other) {
        return color.equals(other.getColor()) || color.equals("Wild");
    }
}