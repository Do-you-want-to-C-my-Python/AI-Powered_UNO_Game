package upei.project;

public abstract class ActionCard extends upei.project.Card {
    protected final String action;

    public ActionCard(String color, String action) {
        super(color);
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public boolean canPlayOn(upei.project.Card topCard) {
        return topCard.getColor().equals(this.color) ||
                (topCard instanceof ActionCard && ((ActionCard) topCard).getAction().equals(this.action));
    }

    @Override
    public String toString() {
        return color + " " + action;
    }
}

