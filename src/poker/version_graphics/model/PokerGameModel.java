package poker.version_graphics.model;

import java.util.ArrayList;
import poker.version_graphics.PokerGame;



public class PokerGameModel {
	private  ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	
	
	
	
	public PokerGameModel() {
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			players.add(new Player("Player " + i));
		}
		
		deck = new DeckOfCards();
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public String getPlayerGetName(int i) {
		return players.get(i).getPlayerName();
	}
	
	public void addPlayer() {
		PokerGame.NUM_PLAYERS++;
		players.add(new Player("Player " + (PokerGame.NUM_PLAYERS-1)));
		
	}
	
	public void removePlayer() {
		PokerGame.NUM_PLAYERS--;
		players.remove(PokerGame.NUM_PLAYERS);
	}
	
	public DeckOfCards getDeck() {
		return deck;
		
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}


	


}
