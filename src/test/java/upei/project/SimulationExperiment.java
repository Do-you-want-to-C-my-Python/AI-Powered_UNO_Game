package upei.project;

import java.util.*;

public class SimulationExperiment {

    private static final int TRIALS = 10000;
    private static final int NUM_PLAYERS = 3;
    private static final String[] STRATEGY_NAMES = {
            "First Available Card Strategy",
            "Number Card First Strategy",
            "Action Card First Strategy"
    };

    public static void main(String[] args) {
        // Original 3-player simulation
        System.out.println("========= THREE PLAYER SIMULATION =========");
        threePlayerSimulation();

        // Run all 2-player matchups
        runAllTwoPlayerMatchups();
    }

    public static void threePlayerSimulation() {
        int[] wins = new int[3];
        int draws = 0;  // Track games that end in a draw (no winner)

        for (int i = 0; i < TRIALS; i++) {
            List<Player> players = createPlayers();
            Game game = new Game(players);
            Player winner = playGame(game);
            if (winner != null) {
                int strategyIndex = players.indexOf(winner);
                wins[strategyIndex % 3]++;
            } else {
                draws++;  // Increment draws when there's no winner
            }
        }

        System.out.println("Simulation results after " + TRIALS + " trials:");
        System.out.println("\nWins:");
        System.out.println("First Available Card Strategy: " + wins[0] + " wins (" +
                String.format("%.2f", (double)wins[0]/TRIALS * 100) + "%)");
        System.out.println("Number Card First Strategy: " + wins[1] + " wins (" +
                String.format("%.2f", (double)wins[1]/TRIALS * 100) + "%)");
        System.out.println("Action Card First Strategy: " + wins[2] + " wins (" +
                String.format("%.2f", (double)wins[2]/TRIALS * 100) + "%)");
        System.out.println("\nDraws (No Winner): " + draws + " games (" +
                String.format("%.2f", (double)draws/TRIALS * 100) + "%)");

        System.out.println("\nStrategy Rankings (by win rate):");
        printStrategyRankings(wins);

        System.out.println("\nStrategy Descriptions:");
        System.out.println("Player 1 played the first available card");
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

            Card playedCard = switch (game.getCurrentPlayerIndex() % 3) {
                case 1 -> currentPlayer.playNumberCardFirst(topCard);
                case 2 -> currentPlayer.playActionCardFirst(topCard);
                default -> currentPlayer.playCard(topCard);
            };

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
        return null;  // Return null when game ends in a draw (no winner)
    }

    private static void printStrategyRankings(int[] wins) {
        List<Integer> strategyIndices = new ArrayList<>();
        for (int i = 0; i < wins.length; i++) {
            strategyIndices.add(i);
        }

        strategyIndices.sort((a, b) -> wins[b] - wins[a]);

        for (int i = 0; i < strategyIndices.size(); i++) {
            int strategyIndex = strategyIndices.get(i);
            double winRate = (double)wins[strategyIndex]/TRIALS * 100;
            System.out.printf("%d. %s - %.2f%% win rate (%d wins)%n",
                    i + 1,
                    STRATEGY_NAMES[strategyIndex],
                    winRate,
                    wins[strategyIndex]);
        }
    }

    public static void twoPlayerSimulation(int strat1, int strat2) {
        int[] wins = new int[2];  // Only track 2 players
        int draws = 0;

        for (int i = 0; i < TRIALS; i++) {
            List<Player> players = new ArrayList<>();
            players.add(new Player("Player 1 (" + STRATEGY_NAMES[strat1] + ")"));
            players.add(new Player("Player 2 (" + STRATEGY_NAMES[strat2] + ")"));

            Game game = new Game(players);
            Player winner = playTwoPlayerGame(game, strat1, strat2);

            if (winner != null) {
                int strategyIndex = players.indexOf(winner);
                wins[strategyIndex]++;
            } else {
                draws++;
            }
        }

        System.out.println("\n=== Two Player Simulation Results ===");
        System.out.println("Strategy matchup: " + STRATEGY_NAMES[strat1] + " vs " + STRATEGY_NAMES[strat2]);
        System.out.println("\nResults after " + TRIALS + " trials:");
        System.out.println("Player 1 (" + STRATEGY_NAMES[strat1] + "): " + wins[0] + " wins (" +
                String.format("%.2f", (double)wins[0]/TRIALS * 100) + "%)");
        System.out.println("Player 2 (" + STRATEGY_NAMES[strat2] + "): " + wins[1] + " wins (" +
                String.format("%.2f", (double)wins[1]/TRIALS * 100) + "%)");
        System.out.println("Draws: " + draws + " games (" + String.format("%.2f", (double)draws/TRIALS * 100) + "%)");
    }

    private static Player playTwoPlayerGame(Game game, int strat1, int strat2) {
        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            Card topCard = game.getTopCard();

            Card playedCard;
            if (game.getCurrentPlayerIndex() % 2 == 0) {
                // Player 1's strategy
                playedCard = switch (strat1) {
                    case 0 ->  // First Available
                            currentPlayer.playCard(topCard);
                    case 1 ->  // Number Card First
                            currentPlayer.playNumberCardFirst(topCard);
                    case 2 ->  // Action Card First
                            currentPlayer.playActionCardFirst(topCard);
                    default -> currentPlayer.playCard(topCard);
                };
            } else {
                // Player 2's strategy
                playedCard = switch (strat2) {
                    case 0 ->  // First Available
                            currentPlayer.playCard(topCard);
                    case 1 ->  // Number Card First
                            currentPlayer.playNumberCardFirst(topCard);
                    case 2 ->  // Action Card First
                            currentPlayer.playActionCardFirst(topCard);
                    default -> currentPlayer.playCard(topCard);
                };
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

    public static void runAllTwoPlayerMatchups() {
        System.out.println("\n========= TWO PLAYER MATCHUPS =========");

        // Strategy 0 vs Strategy 1
        twoPlayerSimulation(0, 1);

        // Strategy 1 vs Strategy 2
        twoPlayerSimulation(1, 2);

        // Strategy 0 vs Strategy 2
        twoPlayerSimulation(0, 2);
    }
}