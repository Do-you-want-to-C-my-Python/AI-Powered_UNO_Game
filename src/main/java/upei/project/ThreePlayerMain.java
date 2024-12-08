package upei.project;

import java.util.*;

/**
 * Main class for running a three-player UNO game simulation.
 * Tests the interaction between three different strategies in a single game.
 * Provides detailed game state visualization and turn-by-turn progress.
 */
public class ThreePlayerMain {
    /**
     * Names of the different strategies being tested.
     * Each player uses a different strategy to evaluate their effectiveness
     * in a three-player dynamic.
     */
    private static final String[] STRATEGY_NAMES = {
            "First Available Card Strategy",
            "Number Card First Strategy",
            "Action Card First Strategy"
    };

    /**
     * Main method that runs a single three-player game.
     * Each player uses a different strategy, and the game continues
     * until someone wins or the turn limit is reached.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        int turnCount = 0;
        System.out.println("=== UNO Three Player Game ===\n");

        // Create players with their respective strategies
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player 1 (" + STRATEGY_NAMES[0] + ")");
        Player player2 = new Player("Player 2 (" + STRATEGY_NAMES[1] + ")");
        Player player3 = new Player("Player 3 (" + STRATEGY_NAMES[2] + ")");
        players.add(player1);
        players.add(player2);
        players.add(player3);

        System.out.println("Players:");
        System.out.println("1. " + player1);
        System.out.println("2. " + player2);
        System.out.println("3. " + player3+"\n");

        // Create and play the game
        Game game = new Game(players);

        while (!game.isGameOver() && turnCount < 1000) {
            turnCount++;
            System.out.println("\n--- Turn " + turnCount + " ---");
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\nCurrent player: " + currentPlayer);
            displayGameState(game);

            // Display all players' hands
            displayPlayerHands(players);
            System.out.println("Cards left in deck: " + game.getDeck().getSize()+"\n");

            TurnResult result = playTurn(game, currentPlayer);
            Card playedCard = result.playedCard;
            Card drawnCard = result.drawnCard;

            if (playedCard != null) {
                if (playedCard != drawnCard) {
                    System.out.println(currentPlayer + " plays " + playedCard);
                }
                game.setTopCard(playedCard);
                if (currentPlayer.getHandSize() == 1) {
                    currentPlayer.callUno();
                    System.out.println(currentPlayer + " calls UNO!");
                } else if (currentPlayer.getHandSize() == 0) {
                    System.out.println("\n" + currentPlayer + " wins!");
                    return;
                }
            }

            displayHandSizes(players);
            game.checkForMissedUnoCall(currentPlayer);
            currentPlayer.resetUnoCall();
        }

        if (turnCount >= 1000) {
            System.out.println("\nGame forcefully ended after 1000 turns to prevent infinite loop.");
        }
        System.out.println("\nGame over after " + turnCount + " turns.");
        if (game.getDeck().isEmpty()) {
            System.out.println("Deck is empty.");
        }
        Player winner = game.getWinner();
        System.out.println("\nFinal hand sizes:");
        displayHandSizes(players);
        System.out.println("\nWinner: " + winner + " with " + winner.getHandSize() + " cards");
    }

    /**
     * Inner class to hold the result of a player's turn.
     * Tracks both the played card and any card drawn during the turn.
     */
    private static class TurnResult {
        Card playedCard;
        Card drawnCard;

        TurnResult(Card played, Card drawn) {
            this.playedCard = played;
            this.drawnCard = drawn;
        }
    }

    /**
     * Displays the current state of the game.
     * Shows:
     * - Top card
     * - Current color
     * - Direction of play
     * - Cards in deck
     *
     * @param game The current game instance
     */
    private static void displayGameState(Game game) {
        System.out.println("Top card: " + game.getTopCard() + ", Current color: " + game.getCurrentColor());
    }

    /**
     * Shows the hands of all players in the game.
     * Displays each player's cards for game state visualization.
     *
     * @param players List of players in the game
     */
    private static void displayPlayerHands(List<Player> players) {
        System.out.println("\nCurrent hands:");
        for (Player player : players) {
            displayPlayerHand(player);
        }
        System.out.println();
    }

    /**
     * Displays a single player's hand.
     * Shows the player's name and their cards.
     *
     * @param player The player whose hand to display
     */
    private static void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
        System.out.println(player + " (" + hand.size() + " cards): " +
                hand.stream()
                        .map(Card::toString)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Empty hand"));
    }

    /**
     * Displays the hand sizes of all players in the game.
     * Shows the number of cards each player has in their hand.
     *
     * @param players List of players in the game
     */
    private static void displayHandSizes(List<Player> players) {
        System.out.print("\nHand sizes - ");
        for (int i = 0; i < players.size(); i++) {
            System.out.print("P" + (i + 1) + ": " + players.get(i).getHandSize());
            if (i < players.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    /**
     * Handles a single player's turn based on their strategy.
     * Implements different play strategies and manages card playing/drawing.
     *
     * @param game The current game instance
     * @param currentPlayer The player whose turn it is
     * @return TurnResult containing the played card and any drawn card
     */
    private static TurnResult playTurn(Game game, Player currentPlayer) {
        Card playedCard;
        Card drawnCard = null;

        if (game.getDrawTwoCount() > 0) {
            playedCard = currentPlayer.playDrawTwo(game.getTopCard());
            if (playedCard == null) {
                System.out.println(currentPlayer + " draws " + (2 * game.getDrawTwoCount()) + " cards");
                System.out.println(currentPlayer + " turn skipped");
                game.drawCards(2 * game.getDrawTwoCount());
                return new TurnResult(null, null);
            }
        } else if (game.getDrawFourCount() > 0) {
            playedCard = currentPlayer.playDrawFour();
            if (playedCard == null) {
                System.out.println(currentPlayer + " draws " + (4 * game.getDrawFourCount()) + " cards");
                System.out.println(currentPlayer + " turn skipped");
                game.drawCards(4 * game.getDrawFourCount());
                return new TurnResult(null, null);
            }
        } else {
            playedCard = switch (game.getCurrentPlayerIndex() % 3) {
                case 1 -> currentPlayer.playNumberCardFirst(game.getTopCard());
                case 2 -> currentPlayer.playActionCardFirst(game.getTopCard());
                default -> currentPlayer.playCard(game.getTopCard());
            };
            if (playedCard == null && !game.getDeck().isEmpty()) {
                drawnCard = game.getDeck().drawCard();
                if (game.canPlayCard(drawnCard)) {
                    System.out.println(currentPlayer + " draws a card");
                    System.out.println(currentPlayer + " plays drawn card: " + drawnCard);
                    playedCard = drawnCard;
                } else {
                    currentPlayer.addCard(drawnCard);
                    System.out.println(currentPlayer + " draws a card");
                    System.out.println(currentPlayer + " turn skipped");
                    game.moveToNextPlayer();
                }
            }
        }
        return new TurnResult(playedCard, drawnCard);
    }
}
