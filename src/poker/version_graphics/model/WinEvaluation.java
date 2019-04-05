package poker.version_graphics.model;

import java.util.ArrayList;
import java.util.Collections;

import poker.version_graphics.PokerGame;

public class WinEvaluation {
	ArrayList<Card> clonedCards = HandType.getCardArray();
 	HandType handType;
	 
	
	public static Player Win(PokerGameModel model) {
	
		
		//Players HandType Ordinal in new ArrayList (not sorted, so the list is correct), then compare ordinals of each player
		ArrayList<Integer> playerordinals = new ArrayList<Integer>();	
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Card> cardsp2 = new ArrayList<Card>();
		int s;
		int sp2;
		
			int i;
		for(i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			HandType p = model.getPlayer(i).getHandType();
			int ordinal = p.ordinal();
			playerordinals.add(ordinal);
		}		
		int winner = playerordinals.get(0);
		Player player = model.getPlayer(0);
		for(int j = 1; j < playerordinals.size(); j++) {
			if(winner < playerordinals.get(j)) {
				winner = playerordinals.get(j);
				
				player = model.getPlayer(j);
				
		//TIE BREAK		
		}else if(winner == playerordinals.get(j)) {
		
//---------------------------------------------------------------------------------			
			//For ordinals, get HandType detected to evaluate
			switch(winner) {
			//HighCard
			case 0:
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				//score = cards.get(4).getCardScore();
				s = cards.get(4).getCardScoreAsInt();				
				//other player
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				//scorep2 = cardsp2.get(4).getCardScore();
				sp2 = cardsp2.get(4).getCardScoreAsInt();
				
				if(s < sp2) {
					winner = playerordinals.get(j);
					player = model.getPlayer(j);
					
				//if first two cards are equal --> 
				//	then else compare second card of each deck	
				}else if(s == sp2){
				String scorescndCardp1 = cards.get(3).getCardScore();
				int scndCardScorep1 = Integer.parseInt(scorescndCardp1);
				
				String scorescndCardp2 = cardsp2.get(3).getCardScore();
				int scndCardScorep2 = Integer.parseInt(scorescndCardp2);
				
				if(scndCardScorep1 < scndCardScorep2) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				}
				
				cards.clear();
				cardsp2.clear();
				break;
//---------------------------------------------------------------------------------
				
			//OnePair
			case 1:	
				//player 1
				int pl1=0;
				cards.addAll(player.getCards());
				Collections.sort(cards);
				boolean foundpl1 = false;
				for (int c = 0; c < cards.size() - 1 && !foundpl1; c++) {
		               if (cards.get(c).getRank() == cards.get(c+1).getRank()) {
		            	   foundpl1 = true;   
		            	   pl1 = cards.get(c).getCardScoreAsInt();
		               }
		        }
				//player 2
				int pl2=0;
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				boolean foundpl2 = false;
					for (int c = 0; c < cardsp2.size() -1 && !foundpl2; c++) {
		                if (cardsp2.get(c).getRank() == cardsp2.get(c+1).getRank()) {
		                foundpl2 = true;
		                pl2 = cardsp2.get(c).getCardScoreAsInt();
		                }
		        }
					if(pl1 < pl2) {
						winner = playerordinals.get(j);
						player = model.getPlayer(j);
					}
				cards.clear();
				cardsp2.clear();
				break;
	
//---------------------------------------------------------------------------------
			//TwoPair
			case 2:
				//player 1
				int pl1tp=0;
				cards.addAll(player.getCards());
				Collections.sort(cards);
				boolean foundpl1tp = false;
				for (int c = 0; c < cards.size() - 1 && !foundpl1tp; c++) {
		               if (cards.get(c).getRank() == cards.get(c+1).getRank()) {
		            	   foundpl1tp = true;   
		            	   pl1tp = cards.get(c).getCardScoreAsInt();
		            	   cards.remove(c);
		            	   cards.remove(c+1);
		               }
				}
		        boolean foundpl1tpsecondPair = false;
		        for(int d=0; d < cards.size() -1 && !foundpl1tpsecondPair; d++) {
		        	if(cards.get(d).getRank() == cards.get(d+1).getRank()) {
		        		foundpl1tpsecondPair = true;
		        		pl1tp +=cards.get(d).getCardScoreAsInt();
		        	}
		        }
  
				//player 2
				int pl2tp=0;
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				boolean foundpl2tp = false;
					for (int c = 0; c < cardsp2.size() -1 && !foundpl2tp; c++) {
		                if (cardsp2.get(c).getRank() == cardsp2.get(c+1).getRank()) {
		                foundpl2tp = true;
		                pl2tp = cardsp2.get(c).getCardScoreAsInt();
		                cardsp2.remove(c);
		                cardsp2.remove(c+1);
		                }
		        }
				boolean foundpl2tpSecondPair = false;
					for(int d= 0; d < cardsp2.size()-1 && !foundpl2tpSecondPair; d++) {
						if(cardsp2.get(d).getRank() == cardsp2.get(d+1).getRank()) {
							foundpl2tpSecondPair = true;
							pl2tp += cardsp2.get(d).getCardScoreAsInt();
						}
					}

					if(pl1tp < pl2tp) {
						player = model.getPlayer(j);
						winner = playerordinals.get(j);
					}
					
				cards.clear();
				cardsp2.clear();
				break;
				
//---------------------------------------------------------------------------------				
			//ThreeOfAKind	
			case 3:
				//player 1
				int threeofaKpl1 = 0;
				cards.addAll(player.getCards());
				Collections.sort(cards);
				boolean threeofakindpl1 = false;
				for(int q = 0; q < cards.size() - 2 && !threeofakindpl1; q++) {
					if(cards.get(q).getRank() == cards.get(q+1).getRank() &&
						cards.get(q+1).getRank() == cards.get(q+2).getRank()) {
						threeofakindpl1 = true;
						threeofaKpl1 = cards.get(q).getCardScoreAsInt();
					}
				}
				
				//player 2
				int threeofaKpl2 = 0;
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				boolean threeofakindpl2 = false;
				for(int r = 0; r < cardsp2.size() -2 && !threeofakindpl2; r++) {
					if(cardsp2.get(r).getRank() == cardsp2.get(r+1).getRank() &&
						cardsp2.get(r+1).getRank() == cardsp2.get(r+2).getRank()) {
						threeofakindpl2 = true;
						threeofaKpl2 = cardsp2.get(r).getCardScoreAsInt();
					}
				}
				
				if(threeofaKpl1 < threeofaKpl2 ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;
			
//---------------------------------------------------------------------------------
			//Straight	
			case 4:	
				int player1 =0;
				int player2 =0;
				
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				
				player1 = cards.get(4).getCardScoreAsInt();	
				
				//player 2
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				
				player2 = cardsp2.get(4).getCardScoreAsInt();
				
				if(player1 < player2 ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;

//---------------------------------------------------------------------------------				
			//Flush
			case 5:
				int player1Flush = 0;
				int player2Flush = 0;
				
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				
				player1Flush = cards.get(4).getCardScoreAsInt();				
				
				//player 2
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				
				player2Flush = cardsp2.get(4).getCardScoreAsInt();
				
				if(player1Flush < player2Flush ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;
				
//---------------------------------------------------------------------------------				
			//FullHouse
			case 6:
				int player1FullHouse = 0;
				int player2FullHouse = 0;
				
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				
				player1FullHouse = cards.get(4).getCardScoreAsInt();
				player1FullHouse += cards.get(0).getCardScoreAsInt();
				
				//player 2
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				
				player2FullHouse = cardsp2.get(4).getCardScoreAsInt();
				player2FullHouse += cardsp2.get(0).getCardScoreAsInt();
				
				if(player1FullHouse < player2FullHouse ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;
				
//----------------------------------------------------------------------------------				
			//FourOfAKind
			case 7:
				int player1fouroAK =0;
				int player2fouroAK =0;
				
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				
				player1 = cards.get(3).getCardScoreAsInt();	
				
				//player 2
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				
				player2 = cardsp2.get(3).getCardScoreAsInt();
				
				if(player1fouroAK < player2fouroAK ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;
			
			//StraightFlush	
			case 8:
				int player1StraightFlush =0;
				int player2StraightFlush =0;
				
				//player 1
				cards.addAll(player.getCards());
				Collections.sort(cards);
				
				player1StraightFlush = cards.get(4).getCardScoreAsInt();	
				
				//player 2
				cardsp2.addAll(model.getPlayer(j).getCards());
				Collections.sort(cardsp2);
				
				player2StraightFlush = cardsp2.get(4).getCardScoreAsInt();
				
				if(player1StraightFlush < player2StraightFlush ) {
					player = model.getPlayer(j);
					winner = playerordinals.get(j);
				}
				
				cards.clear();
				cardsp2.clear();
				break;
			}		
		}
		}
		return player;
		}

}


