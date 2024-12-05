package upei.project;

import java.util.*;

public class TwoPlayerMain {
    private static final String[] STRATEGY_NAMES = {
            "First Available Card Strategy",
            "Number Card First Strategy",
            "Action Card First Strategy"
    };

    public static void main(String[] args) {
        System.out.println("=== UNO Two Player Matchups ===\n");

        // Play all possible combinations
        playMatch(0, 1); // First Available vs Number First
        System.out.println("\n" + "=".repeat(50) + "\n");

        playMatch(1, 2); // Number First vs Action First
        System.out.println("\n" + "=".repeat(50) + "\n");

        playMatch(0, 2); // First Available vs Action First
        System.out.println("\n" + "=".repeat(50) + "\n");

        // Reverse matchups to test if going first matters
        playMatch(1, 0); // Number First vs First Available
        System.out.println("\n" + "=".repeat(50) + "\n");

        playMatch(2, 1); // Action First vs Number First
        System.out.println("\n" + "=".repeat(50) + "\n");

        playMatch(2, 0); // Action First vs First Available
    }

    private static void playMatch(int strat1, int strat2) {
        System.out.println("Match: " + STRATEGY_NAMES[strat1] + " vs " + STRATEGY_NAMES[strat2]);

        // Create players with their respective strategies
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player 1 (" + STRATEGY_NAMES[strat1] + ")");
        Player player2 = new Player("Player 2 (" + STRATEGY_NAMES[strat2] + ")");
        players.add(player1);
        players.add(player2);

        // Create and play the game
        Game game = new Game(players);

        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            Card topCard = game.getTopCard();
            String currentColor = game.getCurrentColor();

            System.out.println("\nCurrent player: " + currentPlayer);
            System.out.println("Top card: " + topCard + ", Current color: " + currentColor);

            // Display all players' hands
            System.out.println("\nCurrent hands:");
            displayPlayerHand(player1);
            displayPlayerHand(player2);
            System.out.println("Cards left in deck: " + game.getDeck().getSize());

            Card playedCard;
            if (game.getCurrentPlayerIndex() % 2 == 0) {
                // Player 1's strategy
                playedCard = switch (strat1) {
                    case 1 -> currentPlayer.playNumberCardFirst(topCard);
                    case 2 -> currentPlayer.playActionCardFirst(topCard);
                    default -> currentPlayer.playCard(topCard);
                };
            } else {
                // Player 2's strategy
                playedCard = switch (strat2) {
                    case 1 -> currentPlayer.playNumberCardFirst(topCard);
                    case 2 -> currentPlayer.playActionCardFirst(topCard);
                    default -> currentPlayer.playCard(topCard);
                };
            }

            if (playedCard != null) {
                System.out.println(currentPlayer + " plays " + playedCard);
                game.setTopCard(playedCard);
                game.setCurrentColor(playedCard.getColor());

                if (currentPlayer.getHandSize() == 0) {
                    System.out.println("\n" + currentPlayer + " wins!");
                    return;
                }
            } else {
                if (!game.getDeck().isEmpty()) {
                    System.out.println(currentPlayer + " draws a card");
                    Card drawnCard = game.getDeck().drawCard();
                    currentPlayer.addCard(drawnCard);
                    if (drawnCard.canPlayOn(topCard)) {
                        System.out.println(currentPlayer + " plays drawn card: " + drawnCard);
                        game.setTopCard(drawnCard);
                        game.setCurrentColor(drawnCard.getColor());
                    }
                }
            }

            game.moveToNextPlayer();
            System.out.println("\nHand sizes - P1: " + player1.getHandSize() +
                    ", P2: " + player2.getHandSize());
        }
        System.out.println("\nGame over. Deck is empty.");
    }

    private static void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
        System.out.println(player + " (" + hand.size() + " cards): " +
                hand.stream()
                        .map(Card::toString)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Empty hand"));
    }
}