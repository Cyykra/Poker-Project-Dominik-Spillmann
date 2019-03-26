package poker.version_graphics.model;

import java.util.ArrayList;
import java.util.Collections;

import poker.version_graphics.model.Card.Suit;

public enum HandType {
    HighCard, OnePair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush;
    
    /**
     * Determine the value of this hand. Note that this does not
     * account for any tie-breaking
     */
    public static HandType evaluateHand(ArrayList<Card> cards) {
        HandType currentEval = HighCard;
        
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
        Collections.sort(clonedCards);
        
        
        if (isOnePair(clonedCards)) currentEval = OnePair;
        if (isTwoPair(clonedCards)) currentEval = TwoPair;
        if (isThreeOfAKind(clonedCards)) currentEval = ThreeOfAKind;
        if (isStraight(clonedCards)) currentEval = Straight;
        if (isFlush(clonedCards)) currentEval = Flush;
        if (isFullHouse(clonedCards)) currentEval = FullHouse;
        if (isFourOfAKind(clonedCards)) currentEval = FourOfAKind;
        if (isStraightFlush(clonedCards)) currentEval = StraightFlush;
        
        return currentEval;
    }
    
    
    public static boolean isOnePair(ArrayList<Card> cards) {
        boolean found = false;
        
        for (int i = 0; i < cards.size() - 1 && !found; i++) {
            for (int j = i+1; j < cards.size() && !found; j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) found = true;
            }
        }
        return found;
    }
    
    
    public static boolean isTwoPair(ArrayList<Card> cards) {
        // Clone the cards, because we will be altering the list
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

        // Find the first pair; if found, remove the cards from the list
        boolean firstPairFound = false;
        
        for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
            for (int j = i+1; j < clonedCards.size() && !firstPairFound; j++) {
                if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
                    firstPairFound = true;
                    clonedCards.remove(j);  // Remove the later card
                    clonedCards.remove(i);  // Before the earlier one
                }
            }
        }
        // If a first pair was found, see if there is a second pair
        return firstPairFound && isOnePair(clonedCards);
    }
    
    public static boolean isThreeOfAKind(ArrayList<Card> cards) {
        boolean found = false;
        
        for(int i = 0; i < cards.size() - 2 && !found; i++) {
        	for(int j = i + 1; j < cards.size() - 1 && !found; j++) {
       			for(int k = j + 1; k < cards.size() && !found; k++) {
        				if(cards.get(i).getRank() == cards.get(j).getRank() &&
        					cards.get(j).getRank() == cards.get(k).getRank())
        					found = true;
        		}
        	}
        }
        return found;
    }
    
    
    //New Method
    public static boolean isStraight(ArrayList<Card> cards) {
    	boolean straightFound = true;
    	
    	for(int i = 0; i < cards.size()-1 && straightFound; i++) {
    		if(cards.get(i).compareTo(cards.get(i+1)) !=-1)
    			straightFound = false;	
    	}
    	return straightFound;
    }
    
    
    //New Method
    public static boolean isFlush(ArrayList<Card> cards) {
        boolean flush = true;
        
        Suit suitOfCard = cards.get(0).getSuit();
        
        for(int i = 1; i < cards.size() && flush; i++) {
        	if(cards.get(i).getSuit() != suitOfCard)
        		flush = false;
        }
      
        return flush;
    }
    
    
    //New Method
    public static boolean isFullHouse(ArrayList<Card> cards) {
        boolean fullHouseFound = false;
        //first three cards must be equal & last two cards must be equal
        if(cards.get(0).getRank().ordinal() == cards.get(1).getRank().ordinal() &&
        		cards.get(2).getRank().ordinal() == cards.get(3).getRank().ordinal() &&
        		cards.get(3).getRank().ordinal() == cards.get(4).getRank().ordinal() 
        		||
        		//or first two cards must be equal & last three cards must be equal
        		cards.get(0).getRank().ordinal() == cards.get(1).getRank().ordinal() &&
        		cards.get(1).getRank().ordinal() == cards.get(2).getRank().ordinal() &&
        		cards.get(3).getRank().ordinal() == cards.get(4).getRank().ordinal()) {
        	fullHouseFound = true;
        }
        return fullHouseFound;
    }
    
    
    //New Method
    public static boolean isFourOfAKind(ArrayList<Card> cards) {
    	boolean found = false;
    	if(cards.get(0).getRank().ordinal() == cards.get(3).getRank().ordinal() ||
    		cards.get(4).getRank().ordinal() == cards.get(1).getRank().ordinal()) {
    		found = true;
    
    	}
    return found;   
    }
    
    
    //New Method
    public static boolean isStraightFlush(ArrayList<Card> cards) {
    	boolean straight = HandType.isStraight(cards);
    	boolean flush = HandType.isFlush(cards);
    	boolean straightFlush = straight && flush;   	
    	
               
        return straightFlush;
    }
}
