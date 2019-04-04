package poker.version_graphics.view;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.HandType;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.WinEvaluation;


public class PlayerPane extends VBox {
    private Label lblName = new Label();
    private HBox hboxCards = new HBox();
    private Label lblEvaluation = new Label("--");
    private int winCounter = 0;
    private Label lblWins = new Label("Wins: " + winCounter);
    
    
    
    
    // Link to player object
    private Player player;
    
    public PlayerPane() {
        super(); // Always call super-constructor first !!
        this.getStyleClass().add("player"); // CSS style class
        lblName.getStyleClass().add("player");
        
        // Add child nodes
        this.getChildren().addAll(lblName, hboxCards, lblEvaluation);
        this.getChildren().add(lblWins);
        
        hboxCards.setSpacing(20);
        
        // Add CardLabels for the cards
        for (int i = 0; i < 5; i++) {
            Label lblCard = new CardLabel();
            hboxCards.getChildren().add(lblCard);
            
            Path path = new Path();
    		path.getElements().add(new MoveTo(90, 900));
    		path.getElements().add(new LineTo(60, 75));
    		
    		PathTransition move = new PathTransition(Duration.millis(2000), path);
    		move.setNode(lblCard);
    		move.play();
    		
    		
        }
    } 
    
    public void setPlayer(Player player) {
    	this.player = player;
    	updatePlayerDisplay();// Immediately display the player information
    	
    	
    }
    
    public void updatePlayerDisplay() {
    	lblName.setText(player.getPlayerName());
    	for (int i = 0; i < Player.HAND_SIZE; i++) {
    		Card card = null;
    		if (player.getCards().size() > i) card = player.getCards().get(i);
    		CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
    		lblWins.setTextFill(Color.WHITE);
    		
    		
    		
    		cl.setCard(card);
    		HandType evaluation = player.evaluateHand();
    		if (evaluation != null) {
    			lblEvaluation.setText(evaluation.toString());
    			lblEvaluation.setTextFill(Color.WHITE);
    			
    			
    		}else {
    			lblEvaluation.setText("--");
    	}
    	}
    	
    	
    	
    }
    
    public Player getPlayer() {
    	return this.player;
    }

	public void increaseCounter() {
		winCounter++;
		lblWins.setText("Wins: "+ winCounter);
		
		lblWins.setTextFill(Color.RED);
		ScaleTransition sT = new ScaleTransition(Duration.millis(500));
		sT.setToX(3);
		sT.setToY(3);
		sT.setAutoReverse(true);
		sT.setCycleCount(4);
				
		RotateTransition t1 = new RotateTransition(Duration.millis(800));
		t1.setByAngle(360);
		t1.setCycleCount(1);
		ParallelTransition pT = new ParallelTransition(t1, sT);
		pT.setNode(lblWins);
		pT.setDelay(Duration.millis(2800));
		pT.play();
		
		ScaleTransition st = new ScaleTransition(Duration.millis(800));
		st.setToX(4);
		st.setToY(4);
		st.setAutoReverse(true);
		st.setCycleCount(4);
		
		st.setNode(lblEvaluation);
		st.play();
	}
	
	public void updateNames() {
		lblName.setText(player.getPlayerName());
	}
    
    
    
}
