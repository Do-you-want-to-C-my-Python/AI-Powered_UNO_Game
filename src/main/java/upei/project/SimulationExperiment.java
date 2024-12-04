package upei.project;

import java.util.*;

public class SimulationExperiment {

    private static final int TRIALS = 10000;
    private static final int NUM_PLAYERS = 4;

    public static void main(String[] args) {
        int[] wins = new int[3];

        for (int i = 0; i < TRIALS; i++) {
            List<Player> players = createPlayers();
            Game game = new Game(players);
            Player winner = playGame(game);
            if (winner != null) {
                int strategyIndex = players.indexOf(winner);
                wins[strategyIndex % 3]++;
            }
        }

        System.out.println("Simulation results after " + TRIALS + " trials:");
        System.out.println("First Available Card Strategy: " + wins[0] + " wins");
        System.out.println("Number Card First Strategy: " + wins[1] + " wins");
        System.out.println("Action Card First Strategy: " + wins[2] + " wins");
        System.out.println("Player 1 & 4 played the first available card");
        System.out.println("Player 2 played the number cards first");
        System.out.println("Player 3 played the action cards first");
    }

    private static List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        return players;
    }

    private static Player playGame(Game game) {
        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            Card topCard = game.getTopCard();

            Card playedCard;
            switch (game.getCurrentPlayerIndex() % 3) {
                case 1:
                    playedCard = currentPlayer.playNumberCardFirst(topCard);
                    break;
                case 2:
                    playedCard = currentPlayer.playActionCardFirst(topCard);
                    break;
                default:
                    playedCard = currentPlayer.playCard(topCard);
            }

            if (playedCard != null) {
                game.setTopCard(playedCard);
                if (currentPlayer.getHandSize() == 0) {
                    return currentPlayer;
                }
            } else {
                game.drawCards(1);
            }

            game.moveToNextPlayer();
        }
        return null;
    }
}