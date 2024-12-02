package upei.project;


public class DrawFourCard extends upei.project.Card {
    public DrawFourCard() {
        super("Wild");
    }

    @Override
    public boolean canPlayOn(upei.project.Card topCard) {
        return true; // Draw Four cards can be played on any card
    }

    @Override
    public void play(upei.project.Game game) {
        String newColor = game.getCurrentPlayer().chooseColor();
        game.setTopCard(this);
        game.setCurrentColor(newColor);
        game.drawCards(4);
        game.skipNextPlayer();
    }

    @Override
    public String toString() {
        return "Wild Draw Four";
    }
}

