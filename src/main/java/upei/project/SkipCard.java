package upei.project;


public class SkipCard extends upei.project.ActionCard {
    public SkipCard(String color) {
        super(color, "Skip");
    }

    @Override
    public void play(upei.project.Game game) {
        game.setTopCard(this);
        game.skipNextPlayer();
    }
}

