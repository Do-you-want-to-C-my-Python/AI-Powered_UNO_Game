package upei.project;


public class WildCard extends upei.project.Card {
    public WildCard() {
        super("Wild");
    }

    @Override
    public boolean canPlayOn(upei.project.Card topCard) {
        return true; // Wild cards can be played on any card
    }

    @Override
    public void play(upei.project.Game game) {
        String newColor = game.getCurrentPlayer().chooseColor();
        game.setTopCard(this);
        game.setCurrentColor(newColor);
    }

    @Override
    public String toString() {
        return "Wild";
    }
}

