package com.jackpickus;

import java.util.Scanner;

import com.jackpickus.Card.Card;
import com.jackpickus.Deck.Deck;

public class Blackjack {

    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.playGame();
    }

    void playGame() {

        System.out.println("Welcome to Blackjack! Starting amount is $1000. Minimum bet $5");
        System.out.println("Blackjack pays 3 to 2. Insurance pays 2 to 1");
        System.out.println("Dealer hits on soft 17\n");

        Deck theDeck = makeDeck();

        int deckFullAmount = theDeck.cardsLeft();

        theDeck.shuffle();
        double money = 1000;
        Scanner myScanner = new Scanner(System.in); // Create a Scanner object

        while (money > 0) {
            int player = 0;
            int dealer = 0;
            int bet;

            // deal cards until 75% are gone, make new deck and shuffle
            if (theDeck.cardsLeft() < deckFullAmount * 0.25) {
                theDeck = makeDeck();
                theDeck.shuffle();
            }

            System.out.println("\nYou have $" + money + " left to bet\n");
            System.out.print("Enter bet amount: ");
            String input = myScanner.nextLine(); // Read user input
            if (input.equals("q") || input.equals("quit")) {
                break;
            }

            while (!isValidInput(input, money)) {
                input = myScanner.nextLine();
            }
            bet = Integer.parseInt(input);

            money -= bet;

            Card playerCard1 = theDeck.dealCard();
            Card dealerUpCard = theDeck.dealCard();
            Card playerCard2 = theDeck.dealCard();
            Card dealerDownCard = theDeck.dealCard();

            boolean playerBlackjack = hasBlackJack(playerCard1, playerCard2);
            boolean dealerBlackjack = hasBlackJack(dealerUpCard, dealerDownCard);

            player += playerCard1.getValue() + playerCard2.getValue();
            dealer += dealerUpCard.getValue() + dealerDownCard.getValue();

            System.out.println("Player cards: " + playerCard1 + " and " + playerCard2);
            boolean playerHasOneAce = hasOneAce(playerCard1, playerCard2);

            int tempPlayerAceTotal = 0;
            if (playerHasOneAce) {
                tempPlayerAceTotal = player + 10; // add 10 because the 1 from Ace is already accounted for
            }

            System.out.println("Total: " + player + "\n");

            System.out.println("Dealer is showing: " + dealerUpCard + "\n");

            if (playerBlackjack) {
                money += bet * 2.5;
                System.out.println("BLACKJACK! Win $" + bet * 1.5);
                continue;
            }
            if (dealerBlackjack) {
                System.out.println("Dealer has Blackjack. Tough luck");
                continue;
            }

            System.out.println("Enter 'h' to hit and 's' to stand");
            System.out.println("Enter 'dd' to double down and 'spl' to split");

            boolean playerMove = true;
            boolean playerBusted = false;

            while (playerMove) {
                System.out.print("> ");
                String decision = myScanner.nextLine();
                if (decision.equals("h")) {
                    Card nextCard = theDeck.dealCard();
                    System.out.println("Card dealt is " + nextCard);
                    player += nextCard.getValue();

                    if (nextCard.getName().equals("Ace")) {
                        playerHasOneAce = true;
                        tempPlayerAceTotal = player + 10;
                    } else {
                        tempPlayerAceTotal += nextCard.getValue();
                    }

                    if (playerHasOneAce && tempPlayerAceTotal < 22) {
                        System.out.println("Total: " + tempPlayerAceTotal + "\n");
                    } else {
                        System.out.println("Total: " + player + "\n");
                    }

                } else if (decision.equals("dd")) {
                    money -= bet;
                    bet += bet;
                    Card doubleDownCard = theDeck.dealCard();
                    player += doubleDownCard.getValue();

                    if (playerHasOneAce)
                        tempPlayerAceTotal += doubleDownCard.getValue();

                    System.out.println("Card dealt is " + doubleDownCard);
                    System.out.println("Total: " + player + "\n");
                    break;
                } else if (decision.equals("spl")) {
                    // TODO implement split
                } else {
                    playerMove = false;
                }

                playerBusted = busted(player);
                if (playerBusted) {
                    playerMove = false;
                }
            }

            if (playerHasOneAce) {
                if (tempPlayerAceTotal > player && tempPlayerAceTotal <= 21) {
                    player = tempPlayerAceTotal;
                }
            }

            // if player double downs, then break out of loop without
            // setting playerMove to false so we must check to see if
            // player busted after doubling down
            if (playerMove)
                playerBusted = busted(player);

            if (playerBusted) {
                continue;
            }

            System.out.println("\nDealer has " + dealerDownCard + " for their down card");
            System.out.println("Dealer cards: " + dealerUpCard + " and " + dealerDownCard);
            System.out.println("Dealer total is " + dealer);

            while (dealer < 17) {
                Card dealerNextCard = theDeck.dealCard();
                dealer += dealerNextCard.getValue();
                System.out.println("\nDealer gets " + dealerNextCard);
                System.out.println("Dealer now has a total of " + dealer);
            }
            if (dealer > 21) {
                System.out.println("Dealer busted!");
                money += bet * 2;
                continue;
            }

            if (dealer == player) {
                System.out.println("\nPush");
                money += bet;
            } else if (dealer > player) {
                System.out.println("\nDealer wins :(");
            } else {
                System.out.println("\nPlayer wins $" + bet);
                money += bet * 2;
            }

        }

        myScanner.close();
    }

    // Only want to check if player has 1 ace
    // if they have two that will be handled by split
    private boolean hasOneAce(Card playerCard1, Card playerCard2) {
        if (playerCard1.getName().equals("Ace")) {
            return true;
        } else if (playerCard2.getName().equals("Ace")) {
            return true;
        } else {
            return false;
        }

    }

    private boolean hasBlackJack(Card card1, Card card2) {
        return (card1.getName().equals("Ace") || card2.getName().equals("Ace")) &&
                (card1.getValue() == 10 || card2.getValue() == 10);
    }

    private boolean isValidInput(String input, double money) {
        boolean isNumber = input != null && input.matches("\\d+");

        if (!isNumber) {
            System.out.println("Please enter in a numeric value");
            return false;
        } else {
            int bet = Integer.parseInt(input);
            if (bet > money || bet < 5) {
                System.out.println("Please enter a positive value less than or equal to: " + money);
                System.out.println("Min bet is 5");
                return false;
            } else {
                return true;
            }
        }

    }

    private boolean busted(int total) {
        if (total > 21) {
            System.out.println("Busted!");
            return true;
        }
        return false;
    }

    private Deck makeDeck() {
        return new Deck();
    }

}