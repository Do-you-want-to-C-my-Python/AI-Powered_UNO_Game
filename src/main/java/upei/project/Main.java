package upei.project;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<upei.project.Player> players = Arrays.asList(
                new upei.project.Player("Player 1"),
                new upei.project.Player("Player 2"),
                new upei.project.Player("Player 3"),
                new upei.project.Player("Player 4")
        );

        upei.project.Game game = new upei.project.Game(players);
        game.play();
    }
}

