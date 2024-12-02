package uno;

public class SkipCard extends ActionCard {
    public SkipCard(String color) {
        super(color, "Skip");
    }

    @Override
    public void play(Game game) {
        game.setTopCard(this);
        game.setCurrentColor(this.color);
        game.skipNextPlayer();
    }
}

