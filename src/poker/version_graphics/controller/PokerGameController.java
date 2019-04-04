package poker.version_graphics.controller;


import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.DeckOfCards;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;
import poker.version_graphics.model.WinEvaluation;
import poker.version_graphics.view.ChangePlayerNamesView;
import poker.version_graphics.view.PlayerPane;
import poker.version_graphics.view.PokerGameView;


public class PokerGameController {
	private PokerGameModel model;
	private PokerGameView view;
	private boolean autoshuffleon;
	private ChangePlayerNamesView changeView;
	
	
	public PokerGameController(PokerGameModel model, PokerGameView view) {
		this.model = model;
		this.view = view;
		
		//Initiate FaceDown Label (ControlArea) with Deal
		view.getfaceDownLabel().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				deal();
			}
		});
		//Initiate ShuffleLabel (ControlArea) with Shuffle
		view.getShuffleLabel().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				shuffle();
			}
		});	
		//Initiate Change PlayerNames
		view.getChangePlayerNameButton().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				changePlayerName();
			}
		});
	
	}
	
	
	
    //Remove all cards from players hands, and shuffle the deck
    private void shuffle() {
    	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
    		Player p = model.getPlayer(i);
    		p.discardHand();
    		PlayerPane pp = view.getPlayerPane(i);
    		pp.updatePlayerDisplay();
    	}

    	model.getDeck().shuffle();
    }
    
    
     // Deal each player five cards, then evaluate the two hands
    private void deal() {
    	autoshuffleon = view.getAutoshuffleon();
    	if(autoshuffleon == false) {
    	int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		p.evaluateHand();
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
        	Player winner = WinEvaluation.Win(model);
        	view.updateWinner(winner);
        	
        	
    	} else {
            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
            alert.showAndWait();
    	}
    	}else if(autoshuffleon == true) {
    		autoshuffle();
    	}
    }
    
    
    //AutoShuffle is almost same as deal(), but it first checks if there are enough card in the deck to deal as required.
    public void autoshuffle() {
    	int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
    	if(cardsRequired >= deck.getCardsRemaining()) {
    		shuffle();
    	}else {
    		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		
        		
        		
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        			 
        			
        		}
        		p.evaluateHand();
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
        	Player winner = WinEvaluation.Win(model);
        	view.updateWinner(winner);
    	}
    }
    
    //Initiate View for ChangePlayerNames
    private void changePlayerName() {
    	changeView = new ChangePlayerNamesView(model.getPlayers());
    	changeView.initOwner(view.getStage());
    	
    	changeView.getCancelButton().setOnAction(this::processCancelButton);
    	changeView.getSafeButton().setOnAction(this::processSafeButton);
    	
    	changeView.show();
    }
    
    //Close ChangePlayerNamesView
    public void processCancelButton(ActionEvent e) {
    	changeView.close();
    }
    
    //Initiate Safe PlayerNames
    public void processSafeButton(ActionEvent e) {
    	ArrayList<String> names = changeView.getPlayerNames();
    	for(int i = 0; i < model.getPlayers().size(); i++) {
    		model.getPlayer(i).setPlayerName(names.get(i));
    		view.updatePlayer(i);
    		changeView.close();
    	}
    }
}
