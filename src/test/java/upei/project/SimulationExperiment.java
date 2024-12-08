package upei.project;

import java.util.*;

/**
 * Simulation experiment class for testing and comparing different UNO card play strategies.
 * This class runs multiple trials of both two-player and three-player games to analyze
 * the effectiveness of different card playing strategies.
 * <p>
 * The strategies being compared are:
 * 1. First Available Card Strategy - plays the first playable card found
 * 2. Number Card First Strategy - prioritizes playing number cards, saving action cards
 * 3. Action Card First Strategy - prioritizes playing action cards early
 */
public class SimulationExperiment {

    private static final int TRIALS = 30000; // Used for three-player simulations
    private static final int TWO_PLAYER_TRIALS = 10000; // Added constant for two-player matchups
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

    /**
     * Main method to run the simulation experiments.
     * Provides options to run either three-player simulations, two-player matchups, or both.
     * <p>
     * Command line arguments:
     * - "three": runs only three-player simulations
     * - "two": runs only two-player matchups
     * - no args: runs both simulations
     *
     * @param args Command line arguments to specify simulation type
     */
    public static void main(String[] args) {
        System.out.println("\n========= STRATEGY DESCRIPTIONS =========");
        for (int i = 0; i < STRATEGY_NAMES.length; i++) {
            System.out.printf("\nStrategy %d: %s\n", i + 1, STRATEGY_NAMES[i]);
            System.out.printf("   %s\n", STRATEGY_DESCRIPTIONS[i]);
        }

        if (args.length > 0 && args[0].equals("three")) {
            System.out.println("\n\n========= THREE PLAYER SIMULATION =========");
            threePlayerSimulation();
        } else if (args.length > 0 && args[0].equals("two")) {
            System.out.println("\n\n========= TWO PLAYER MATCHUPS =========");
            runAllTwoPlayerMatchups();
        } else {
            System.out.println("\n\n========= THREE PLAYER SIMULATION =========");
            threePlayerSimulation();

            System.out.println("\n\n========= TWO PLAYER MATCHUPS =========");
            runAllTwoPlayerMatchups();
        }
    }

    /**
     * Runs a simulation of three-player games for a specified number of trials.
     * Each player uses a different strategy, and statistics are collected on:
     * - Number of wins per strategy
     * - Win rates (both raw and adjusted for draws)
     * - Number of draws
     * <p>
     * Results are displayed showing strategy rankings and performance metrics.
     */
    public static void threePlayerSimulation() {
        int[] wins = new int[3];
        int draws = 0; // Track games that end in a draw (no winner)

        for (int i = 0; i < TRIALS; i++) {
            List<Player> players = createPlayers();
            Game game = new Game(players);
            Player winner = playGame(game);
            if (winner != null) {
                int strategyIndex = players.indexOf(winner);
                wins[strategyIndex % 3]++;
            } else {
                draws++; // Increment draws when there's no winner
            }
        }

        double totalGames = TRIALS;

        // Calculate draw rate
        double drawRate = (double) draws / totalGames * 100;

        // Calculate win rates and adjusted win rates (accounting for draws)
        double[] winRates = new double[3];
        double[] adjustedWinRates = new double[3];
        for (int i = 0; i < 3; i++) {
            winRates[i] = (double) wins[i] / totalGames * 100; // Overall win rate
            adjustedWinRates[i] = winRates[i] * (100 - drawRate) / 100; // Adjusted for rankings
        }

        // Print results
        System.out.println("\nResults after " + TRIALS + " games:\n");
        System.out.println("Wins:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("%s: %d wins (%.2f%%)%n",
                    STRATEGY_NAMES[i],
                    wins[i],
                    winRates[i]);
        }
        System.out.printf("Draws: %d games (%.2f%%)\n\n", draws, drawRate);

        // Print rankings (using the same winRates for consistency)
        System.out.println("========= STRATEGY RANKINGS =========\n");

        List<StrategyStats> stats = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stats.add(new StrategyStats(i, wins[i], TRIALS));
        }
        stats.sort((a, b) -> Double.compare(adjustedWinRates[b.strategy], adjustedWinRates[a.strategy]));

        for (int i = 0; i < stats.size(); i++) {
            StrategyStats stat = stats.get(i);
            System.out.printf("%d. %s - %.2f%% win rate (%d wins)%n",
                    i + 1,
                    STRATEGY_NAMES[stat.strategy],
                    winRates[stat.strategy], // Use original winRates for consistency
                    stat.wins);
        }
        System.out.printf("Draws: %.2f%% (%d games)\n\n", drawRate, draws); // Include draw percentage
    }

    /**
     * Runs all possible two-player matchups between the different strategies.
     * Each matchup is simulated multiple times to gather statistical data.
     * Tracks:
     * - Wins for each strategy
     * - Overall win rates
     * - Number of draws
     * <p>
     * Results are aggregated across all matchups to determine the most effective strategy.
     */
    public static void runAllTwoPlayerMatchups() {
        // Track total wins for each strategy
        int[] totalWins = new int[3];
        int totalGamesPlayed = TWO_PLAYER_TRIALS * 3; // Use TWO_PLAYER_TRIALS for two-player matchups
        int totalDraws = 0;

        // Strategy 0 vs Strategy 1
        int[] result01 = simulateTwoPlayerMatchup(0, 1);
        totalWins[0] += result01[0];
        totalWins[1] += result01[1];
        totalDraws += (TWO_PLAYER_TRIALS - (result01[0] + result01[1]));

        // Strategy 1 vs Strategy 2
        int[] result12 = simulateTwoPlayerMatchup(1, 2);
        totalWins[1] += result12[0];
        totalWins[2] += result12[1];
        totalDraws += (TWO_PLAYER_TRIALS - (result12[0] + result12[1]));

        // Strategy 0 vs Strategy 2
        int[] result02 = simulateTwoPlayerMatchup(0, 2);
        totalWins[0] += result02[0];
        totalWins[2] += result02[1];
        totalDraws += (TWO_PLAYER_TRIALS - (result02[0] + result02[1]));

        double[] winRates = new double[3];
        for (int i = 0; i < 3; i++) {
            winRates[i] = (double) totalWins[i] / totalGamesPlayed * 100;
        }
        double drawRate = (double) totalDraws / totalGamesPlayed * 100;

        // Create and sort strategy statistics
        List<StrategyStats> stats = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stats.add(new StrategyStats(i, totalWins[i], totalGamesPlayed));
        }
        stats.sort((a, b) -> Double.compare(winRates[b.strategy], winRates[a.strategy]));

        // Print rankings
        System.out.println("\n========= STRATEGY RANKINGS =========\n");
        for (int i = 0; i < stats.size(); i++) {
            StrategyStats stat = stats.get(i);
            System.out.printf("%d. %s - %.2f%% win rate (%d wins)%n",
                    i + 1,
                    STRATEGY_NAMES[stat.strategy],
                    winRates[stat.strategy],
                    stat.wins);
        }
        System.out.printf("Draws: %.2f%% (%d games)\n", drawRate, totalDraws);
    }

    /**
     * Simulates a matchup between two specific strategies over multiple games.
     *
     * @param strat1 Index of the first player's strategy
     * @param strat2 Index of the second player's strategy
     * @return Array containing the number of wins for each strategy [strat1Wins, strat2Wins]
     */
    private static int[] simulateTwoPlayerMatchup(int strat1, int strat2) {
        System.out.printf("\n=== %s vs %s ===\n", STRATEGY_NAMES[strat1], STRATEGY_NAMES[strat2]);

        int[] wins = new int[2];
        int draws = 0;

        for (int i = 0; i < TWO_PLAYER_TRIALS; i++) { // Use TWO_PLAYER_TRIALS here
            List<Player> players = new ArrayList<>();
            players.add(new Player("Player 1"));
            players.add(new Player("Player 2"));

            Game game = new Game(players);
            Player winner = playGame(game);
            if (winner != null) {
                int winnerIndex = players.indexOf(winner);
                wins[winnerIndex]++;
            } else {
                draws++;
            }
        }

        System.out.println("\nResults after " + TWO_PLAYER_TRIALS + " games:\n");
        System.out.printf("Player 1 (%s): %d wins (%.2f%%)\n",
                STRATEGY_NAMES[strat1], wins[0], (double) wins[0] / TWO_PLAYER_TRIALS * 100);
        System.out.printf("Player 2 (%s): %d wins (%.2f%%)\n",
                STRATEGY_NAMES[strat2], wins[1], (double) wins[1] / TWO_PLAYER_TRIALS * 100);
        System.out.printf("Draws: %d games (%.2f%%)\n\n",
                draws, (double) draws / TWO_PLAYER_TRIALS * 100);

        return wins;
    }

    /**
     * Creates a list of players for a game simulation.
     * Each player is initialized with a sequential name (Player 1, Player 2, etc.).
     *
     * @return List of initialized Player objects
     */
    private static List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        return players;
    }

    /**
     * Simulates a single game until completion or turn limit is reached.
     * Players use different strategies based on their index:
     * - Player 1: First Available Card Strategy
     * - Player 2: Number Card First Strategy
     * - Player 3: Action Card First Strategy
     *
     * @param game The Game instance to simulate
     * @return The winning Player, or null if the game ends in a draw
     */
    private static Player playGame(Game game) {
        int turnLimit = 1000;
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
}
