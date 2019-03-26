package poker.version_graphics.model;

import java.util.ArrayList;

import poker.version_graphics.PokerGame;
import poker.version_graphics.view.PlayerPane;


public class PokerGameModel {
	private final ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	private int i;
	
	
	
	public PokerGameModel() {
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			players.add(new Player("Player " + i));
		}
		
		deck = new DeckOfCards();
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
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
}
