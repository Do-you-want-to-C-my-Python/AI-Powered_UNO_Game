package upei.project;


public class DrawTwoCard extends upei.project.ActionCard {
    public DrawTwoCard(String color) {
        super(color, "Draw Two");
    }

    @Override
    public void play(upei.project.Game game) {
        game.setTopCard(this);
        game.drawCards(2);
        game.skipNextPlayer();
    }
}

