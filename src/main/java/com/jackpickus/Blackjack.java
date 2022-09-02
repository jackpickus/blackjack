package com.jackpickus;

import com.jackpickus.Card.Card;
import com.jackpickus.Deck.Deck;
import com.jackpickus.Hand.Hand;

import java.util.Scanner;
import java.util.Stack;

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

            Hand playerHand = new Hand(playerCard1, playerCard2);
            Hand dealerHand = new Hand(dealerUpCard, dealerDownCard);

            boolean playerBlackjack = playerHand.hasBlackJack();
            boolean dealerBlackjack = dealerHand.hasBlackJack();

            dealer += dealerHand.getHandTotal();

            boolean playerHasOneAce;
            boolean dealerHasOneAce = dealerHand.hasOneAce(dealerUpCard, dealerDownCard);

            int tempDealerAceTotal = dealer;



            System.out.println("Dealer is showing: " + dealerUpCard + "\n");

            boolean paidInsurance = false;
            if (dealerUpCard.getName().equals("Ace")) {
                System.out.println("Would you like to buy insurance? (y/n)");
                System.out.print("> ");
                String insuranceInput = myScanner.nextLine(); // Read user input

                if (insuranceInput.equals("y") || insuranceInput.equals("Y") ) paidInsurance = true;
            }

            double insuranceBet = 0.0;
            if (paidInsurance) {
                insuranceBet = bet / 2.0;
                money -= insuranceBet;
                System.out.println("Insurance bet: " + insuranceBet);
                System.out.println("Money left: " + money + "\n");
            }

            if (playerBlackjack) {
                money += bet * 2.5;
                System.out.println("BLACKJACK! Win $" + bet * 1.5);
                continue;
            }
            if (dealerBlackjack) {
                System.out.println("Dealer has Blackjack. Tough luck");
                if (paidInsurance) {
                    money += (insuranceBet * 2.0) + insuranceBet;
                    System.out.println("Insurance paid " + bet);
                }
                continue;
            }

            System.out.println("Enter 'h' to hit and 's' to stand");
            System.out.println("Enter 'dd' to double down and 'spl' to split\n");

            Stack<Hand> playingHandStack = new Stack<>();
            Stack<Hand> handStack = new Stack<>();
            int numHands = 1; // init to 1 because player will always have 1 hand
            playingHandStack.add(playerHand);

            String decision = "";
            boolean playerBusted = false;
            boolean playerContinuesHand;
            while (!playingHandStack.isEmpty()) {
                Hand temp = playingHandStack.pop();

                if (temp.isBusted()) continue;

                if (temp.hasBlackJack()) {
                    money += bet * 2.5;
                    System.out.println("BLACKJACK! Win $" + bet * 1.5);
                    continue;
                }
                System.out.println(temp);
                playerContinuesHand = false;
                playerHasOneAce = temp.getHasOneAce();

                System.out.print("> ");
                decision = myScanner.nextLine();

                if (!hasEnoughMoney(money, bet) && (decision.equals("dd") || decision.equals("spl"))) {
                    playingHandStack.add(temp);
                    continue;
                }
                if (decision.equals("h")) {
                    Card nextCard = theDeck.dealCard();
                    System.out.println("Card dealt is " + nextCard);

                    temp.addCard(nextCard);

                    if (nextCard.getName().equals("Ace") && !playerHasOneAce) {
                        playerHasOneAce = true;
                        temp.setHasOneAce(true);
                    }

                    if (playerHasOneAce && temp.getHandAceTotal() < 22) {
                        System.out.println("Total: " + temp.getHandAceTotal() + "\n");
                    } else {
                        System.out.println("Total: " + temp.getHandTotal() + "\n");
                    }
                    playingHandStack.add(temp);
                    playerContinuesHand = true;

                } else if (decision.equals("dd") && temp.numCards() == 2 && hasEnoughMoney(money, bet)) {

                    money -= bet;
                    bet += bet;
                    Card doubleDownCard = theDeck.dealCard();
                    temp.addCard(doubleDownCard);

                    if (playerHasOneAce) temp.setHandAceTotal(doubleDownCard.getValue());

                    System.out.println("Card dealt is " + doubleDownCard);

                } else if (decision.equals("spl") && temp.numCards() == 2 && temp.getCard(0).getName().equals(temp.getCard(1).getName()) && hasEnoughMoney(money, bet)) {
                    // player can only split if cards are the same rank ie Jack and Jack
                    System.out.println("SPLIT!");
                    numHands++; // player now has at least two hands
                    Card newCard1 = theDeck.dealCard();
                    Card newCard2 = theDeck.dealCard();

                    money -= bet;

                    Hand playerHand2 = new Hand(temp.getCard(1), newCard2);

                    temp.removeCard(1);
                    temp.addCard(newCard1);

                    playingHandStack.add(playerHand2);
                    playingHandStack.add(temp);

                    int playerHandTotal2 = playerHand2.getHandTotal();

                    if (temp.getHasOneAce()) {
                        temp.setHasOneAce(true);
                    }

                    if (playerHand2.getHasOneAce()) {
                        temp.setHasOneAce(true);
                    }

                    System.out.println("Hand 1: " + temp);
                    System.out.println("Hand 2: " + playerHand2);

                    playerContinuesHand = true;

                }
                if (!playerContinuesHand) {
                    handStack.add(temp);
                }

//                System.out.println("The hand total: " + temp.getHandTotal());
                playerBusted = temp.busted();
                if (playerBusted) {
                    temp.setBusted(true);
                }

            }

            if (playerBusted && numHands <= 1) {
                continue;
            }

            System.out.println("\nDealer has " + dealerDownCard + " for their down card");
            System.out.println("Dealer cards: " + dealerUpCard + " and " + dealerDownCard);

            if (dealerHasOneAce) {
                tempDealerAceTotal = dealer + 10;
                System.out.println("Dealer total is " + tempDealerAceTotal);
            } else {
                System.out.println("Dealer total is " + dealer);
            }

            boolean softSeventeen = tempDealerAceTotal == 17 && dealerHasOneAce;

            boolean firstCycle = true; // used for when dealer has soft 17 to ensure they hit at least once
            while (dealer < 17) {

                // dealer has an ace and total is greater than or equal to 17
                if (tempDealerAceTotal >= 17 && dealerHasOneAce && tempDealerAceTotal <= 21) {
                    if (!softSeventeen) break;

                    // dealer has soft 17 but has received at least three cards to make total at least >= 17 
                    else if (!firstCycle) break; 
                }

                Card dealerNextCard = theDeck.dealCard();
                dealer += dealerNextCard.getValue();
                tempDealerAceTotal += dealerNextCard.getValue();
                System.out.println("\nDealer gets " + dealerNextCard);

                if (!dealerHasOneAce && dealerNextCard.getName().equals("Ace")) {
                    dealerHasOneAce = true;
                    tempDealerAceTotal += 10;
                }

                if (dealerHasOneAce && tempDealerAceTotal < 22) {
                    System.out.println("Dealer total: " + tempDealerAceTotal + "\n");
                } else {
                    System.out.println("Dealer total: " + dealer + "\n");
                }

                // dealer has now received at least 3 cards now
                if (firstCycle) firstCycle = false;
                
            }

           if (dealerHasOneAce) {
                if (tempDealerAceTotal > dealer && tempDealerAceTotal <= 21) {
                    dealer = tempDealerAceTotal;
                }
            } 

            if (dealer > 21) {
                System.out.println("Dealer busted!");
                dealerHand.setBusted(true);
                if (numHands <= 1) {
                    money += bet * (numHands * 2);
                    continue;
                }

            }

            while (!handStack.isEmpty()) {
                Hand temp2 = handStack.pop();
                int temp2Total = temp2.getHandTotal();

                if (temp2.doesHandHaveAce()) {
                    if (temp2.getHandAceTotal() > temp2.getHandTotal() && temp2.getHandAceTotal() <= 21) {
                        temp2Total = temp2.getHandAceTotal();
                    }
                }

                if (temp2.isBusted()) {
                    continue;
                } else if (dealerHand.isBusted()) {
                    money += bet * 2;
                    continue;
                }

                if (dealer == temp2Total) {
                    System.out.println("\nPush");
                    money += bet;
                } else if (dealer > temp2Total) {
                    System.out.println("\nDealer wins :(");
                } else {
                    System.out.println("\nPlayer wins $" + bet);
                    money += bet * 2;
                }
            }

        }

        myScanner.close();
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

    private Deck makeDeck() {
        return new Deck();
    }

}
