package uno;

import java.util.*;

public class UnoSimulation {
    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
            new Player("Player 1"),
            new Player("Player 2"),
            new Player("Player 3"),
            new Player("Player 4")
        );

        Game game = new Game(players);
        game.play();
    }
}

