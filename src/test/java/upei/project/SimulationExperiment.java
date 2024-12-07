package upei.project;

import java.util.*;

public class SimulationExperiment {

    private static final int TRIALS = 30000;
    private static final int NUM_PLAYERS = 3;
    private static final String[] STRATEGY_NAMES = {
            "First Available Card Strategy",
            "Number Card First Strategy (Save action cards for last)",
            "Action Card First Strategy (Use action cards early)"
    };

    private static final String[] STRATEGY_DESCRIPTIONS = {
            "Simple approach - plays the first playable card found",
            "Strategic approach - saves powerful action cards for crucial moments",
            "Aggressive approach - uses action cards early to gain advantage\n"
    };

    private static class StrategyStats {
        int strategy;
        int wins;
        double winRate;

        StrategyStats(int strategy, int wins, int totalGames) {
            this.strategy = strategy;
            this.wins = wins;
            this.winRate = (double) wins / totalGames * 100;
        }
    }

    public static void main(String[] args) {
        // Print strategy descriptions first
        System.out.println("\n========= STRATEGY DESCRIPTIONS =========");
        for (int i = 0; i < STRATEGY_NAMES.length; i++) {
            System.out.printf("\nStrategy %d: %s\n", i + 1, STRATEGY_NAMES[i]);
            System.out.printf("   %s\n", STRATEGY_DESCRIPTIONS[i]);
        }

        // Run only one simulation
        if (args.length > 0 && args[0].equals("three")) {
            // Original 3-player simulation
            System.out.println("\n\n========= THREE PLAYER SIMULATION =========");
            threePlayerSimulation();
        } else if (args.length > 0 && args[0].equals("two")) {
            // Run all 2-player matchups
            System.out.println("\n\n========= TWO PLAYER MATCHUPS =========");
            runAllTwoPlayerMatchups();
        } else {
            // Run both simulations once
            System.out.println("\n\n========= THREE PLAYER SIMULATION =========");
            threePlayerSimulation();

            System.out.println("\n\n========= TWO PLAYER MATCHUPS =========");
            runAllTwoPlayerMatchups();
        }
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

        System.out.println("\nResults after " + TRIALS + " games:\n");
        System.out.println("Wins:");
        System.out.println("First Available Card Strategy: " + wins[0] + " wins (" +
                String.format("%.2f", (double)wins[0]/TRIALS * 100) + "%)");
        System.out.println("Number Card First Strategy (Save action cards for last): " + wins[1] + " wins (" +
                String.format("%.2f", (double)wins[1]/TRIALS * 100) + "%)");
        System.out.println("Action Card First Strategy (Use action cards early): " + wins[2] + " wins (" +
                String.format("%.2f", (double)wins[2]/TRIALS * 100) + "%)");
        System.out.printf("Draws: %d games (%.2f%%)\n\n",
                draws, (double)draws/TRIALS * 100);

        System.out.println("========= STRATEGY RANKINGS =========");
        printStrategyRankings(wins);
    }

    private static List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        return players;
    }

    private static Player playGame(Game game) {
        int turnLimit = 1000; // Add a turn limit to prevent infinite games
        int turns = 0;

        while (!game.isGameOver() && turns < turnLimit) {
            turns++;
            Player currentPlayer = game.getCurrentPlayer();

            Card playedCard = switch (game.getCurrentPlayerIndex() % 3) {
                case 1 -> currentPlayer.playNumberCardFirst(game.getTopCard());
                case 2 -> currentPlayer.playActionCardFirst(game.getTopCard());
                default -> currentPlayer.playCard(game.getTopCard());
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
        return null;
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

    public static int[] twoPlayerSimulation(int strat1, int strat2) {
        System.out.printf("\n=== %s vs %s ===\n", STRATEGY_NAMES[strat1], STRATEGY_NAMES[strat2]);

        int[] wins = new int[2];
        int draws = 0;

        for (int i = 0; i < TRIALS; i++) {
            List<Player> players = new ArrayList<>();
            players.add(new Player("Player 1"));
            players.add(new Player("Player 2"));

            Game game = new Game(players);
            Player winner = playTwoPlayerGame(game, strat1, strat2);

            if (winner != null) {
                int winnerIndex = players.indexOf(winner);
                wins[winnerIndex]++;
            } else {
                draws++;
            }
        }

        System.out.println("\nResults after " + TRIALS + " games:\n");
        System.out.println("Wins:");
        System.out.printf("Player 1 (%s): %d wins (%.2f%%)\n",
                STRATEGY_NAMES[strat1], wins[0], (double)wins[0]/TRIALS * 100);
        System.out.printf("Player 2 (%s): %d wins (%.2f%%)\n",
                STRATEGY_NAMES[strat2], wins[1], (double)wins[1]/TRIALS * 100);
        System.out.printf("Draws: %d games (%.2f%%)\n\n",
                draws, (double)draws/TRIALS * 100);

        return wins;
    }

    private static Player playTwoPlayerGame(Game game, int strat1, int strat2) {
        int turnLimit = 1000; // Add a turn limit to prevent infinite games
        int turns = 0;

        while (!game.isGameOver() && turns < turnLimit) {
            turns++;
            Player currentPlayer = game.getCurrentPlayer();

            Card playedCard;
            if (game.getCurrentPlayerIndex() % 2 == 0) {
                playedCard = switch (strat1) {
                    case 0 -> currentPlayer.playCard(game.getTopCard());
                    case 1 -> currentPlayer.playNumberCardFirst(game.getTopCard());
                    case 2 -> currentPlayer.playActionCardFirst(game.getTopCard());
                    default -> currentPlayer.playCard(game.getTopCard());
                };
            } else {
                playedCard = switch (strat2) {
                    case 0 -> currentPlayer.playCard(game.getTopCard());
                    case 1 -> currentPlayer.playNumberCardFirst(game.getTopCard());
                    case 2 -> currentPlayer.playActionCardFirst(game.getTopCard());
                    default -> currentPlayer.playCard(game.getTopCard());
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
        // Track total wins for each strategy
        int[] totalWins = new int[3];
        int totalGames = TRIALS * 2; // Each strategy plays against other two strategies

        // Strategy 0 vs Strategy 1
        int[] result01 = twoPlayerSimulation(0, 1);
        totalWins[0] += result01[0];
        totalWins[1] += result01[1];

        // Strategy 1 vs Strategy 2
        int[] result12 = twoPlayerSimulation(1, 2);
        totalWins[1] += result12[0];
        totalWins[2] += result12[1];

        // Strategy 0 vs Strategy 2
        int[] result02 = twoPlayerSimulation(0, 2);
        totalWins[0] += result02[0];
        totalWins[2] += result02[1];

        // Create and sort strategy statistics
        List<StrategyStats> stats = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stats.add(new StrategyStats(i, totalWins[i], totalGames));
        }
        stats.sort((a, b) -> Double.compare(b.winRate, a.winRate)); // Sort by win rate descending

        // Print overall rankings
        System.out.println("\n========= STRATEGY RANKINGS =========");
        System.out.println("Based on " + (TRIALS * 2) + " total games per strategy\n");

        for (int i = 0; i < stats.size(); i++) {
            StrategyStats stat = stats.get(i);
            System.out.printf("#%d: %s\n", i + 1, STRATEGY_NAMES[stat.strategy]);
            System.out.printf("   Win Rate: %.2f%% (%d wins)\n\n", stat.winRate, stat.wins);
        }
    }
}