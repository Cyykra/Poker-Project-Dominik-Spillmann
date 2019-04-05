package poker.version_graphics.view;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;

public class PokerGameView {
	
	
	private GridPane players;
	
	private ControlArea controls;
	private Label imageLabel;
	private Label faceDown;
	private Label shuffle;
	private Stage stage;
	private Button enableautoShuffle;
	private Button disableautoShuffle;
	boolean autoshuffleon;
	private ChangePlayerNamesView view;
	private Button changeNames;
	
	private PokerGameModel model;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		this.stage = stage;
		
		// Create all of the player panes we need, and put them into an HBox
		players = new GridPane();
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			switch(i) {
			case 0:
				players.add(pp, 0, 0);
				break;
			case 1:
				players.add(pp, 1, 0);
				break;
			case 2:
				players.add(pp, 1, 1);
				break;
			case 3:
				players.add(pp, 2, 1);
			}
		}
		
		
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic
		
		faceDown = new CardLabel();
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/cardBack.png"));
		ImageView img = new ImageView(image);
		img.setFitHeight(160);
		img.setFitWidth(110);
		faceDown.setGraphic(img);
		
		
		
		controls.getChildren().add(faceDown);
		
		shuffle = new CardLabel();
		Image shuffleImage = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/shuffleCards.png"));
		ImageView shuffleView = new ImageView(shuffleImage);
		shuffleView.setFitHeight(160);
		shuffleView.setFitWidth(110);
		shuffle.setGraphic(shuffleView);
		controls.getChildren().add(shuffle);
		
		
		// Put players and controls into a BorderPane
		BorderPane root = new BorderPane();
		
		imageLabel = new Label();
		Image poker = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/PokerTop.png"));
		ImageView imv = new ImageView(poker);
		imv.setFitHeight(300);
		imv.setFitHeight(150);
		imageLabel.setGraphic(imv);
		root.setTop(imageLabel);
		
		Region regionleft = new Region();
		regionleft.setPrefWidth(160);
		root.setLeft(regionleft);
		

		root.setCenter(players);
		

		//Create Add and Remove Button for Players
		HBox box = new HBox();
		Button add2Pl = new Button("2 Player");
		Button add4Pl = new Button("4 Player");
		Button add6Pl = new Button("6 Player");
		changeNames = new Button("Change Names");
		Button exitgame = new Button("Exit");
		enableautoShuffle = new Button("Auto Shuffle");
		disableautoShuffle = new Button("Disable Auto Shuffle");
		
		Region r1 = new Region();
		Region r2 = new Region();
		
		VBox rightEdgeBox = new VBox();
		rightEdgeBox.getChildren().addAll(exitgame, enableautoShuffle, disableautoShuffle);
		rightEdgeBox.setSpacing(10);
		
		VBox leftBox = new VBox();
		leftBox.getChildren().addAll(add2Pl, add4Pl);
		leftBox.setSpacing(15);
		
		VBox rightBox = new VBox();
		rightBox.getChildren().addAll(add6Pl, changeNames);
		rightBox.setSpacing(15);

		r1.setPrefWidth(430);
		r2.setPrefWidth(220);


		add2Pl.setPrefSize(200, 80);
		add4Pl.setPrefSize(200, 80);
		add6Pl.setPrefSize(200, 80);
		changeNames.setPrefSize(200, 80);
		exitgame.setPrefSize(200,80);
		enableautoShuffle.setPrefSize(200, 30);
		disableautoShuffle.setPrefSize(200, 30);
		
		box.getChildren().addAll(r1, leftBox, controls, rightBox, r2, rightEdgeBox);
		box.setSpacing(20);
		
		root.setBottom(box);
		
		
		
		add2Pl.setOnMouseClicked(this::processAdd2Player);
		add4Pl.setOnMouseClicked(this::processAdd4Player);
		add6Pl.setOnMouseClicked(this::processAdd6Player);
		exitgame.setOnMouseClicked(this::processExitGame);
		enableautoShuffle.setOnMouseClicked(this::processEnableAutoShuffle);
		disableautoShuffle.setOnMouseClicked(this::processdisableautoshuffle);
		
		
		// Disallow resizing - which is difficult to get right with images
		stage.setResizable(true);
		stage.setFullScreen(true);
		
		
        // Create the scene using our layout; then display it
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("poker.css").toExternalForm());
        stage.setTitle("Cyykra's Poker-Project");
        stage.setScene(scene);
        stage.setFullScreenExitHint("Welcome to Cyykra's Poker Project!");
        stage.show();  
	}
	
	public Label getfaceDownLabel() {
		return faceDown;
	}
	
	public Label getShuffleLabel() {
		return shuffle;
	}

	public PlayerPane getPlayerPane(int i) {
		return (PlayerPane) players.getChildren().get(i);
	}
	
	public void processAdd2Player(MouseEvent e) {
		for(int i = PokerGame.NUM_PLAYERS; PokerGame.NUM_PLAYERS > 2; i--) {
			model.removePlayer();
			players.getChildren().remove(PokerGame.NUM_PLAYERS);}
		
		if(PokerGame.NUM_PLAYERS < 2) {
		for(int i = PokerGame.NUM_PLAYERS; i <= 1; i++) {
		model.addPlayer();	
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(PokerGame.NUM_PLAYERS-1));
		pp.setPrefWidth(675);
		switch(i) {
		case 0:
			players.add(pp, 0, 0);
			break;
		case 1: 
			players.add(pp, 1, 0);
			}
		}
	}			
}
	
	public void processAdd4Player(MouseEvent e) {
		for(int i = PokerGame.NUM_PLAYERS; PokerGame.NUM_PLAYERS > 0; i--) {
			model.removePlayer();
			players.getChildren().remove(PokerGame.NUM_PLAYERS);}
		
		if(PokerGame.NUM_PLAYERS < 4) {
		for(int i = 0; i <= 3; i++) {
		model.addPlayer();	
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(PokerGame.NUM_PLAYERS-1));
		
		switch(i) {
		case 0:
			players.add(pp, 0, 0);
			break;
		case 1:
			players.add(pp, 1, 0);
			break;
		case 2:
			players.add(pp, 1, 1);
			break;
		case 3:
			players.add(pp, 0, 1);
			}
		}				
	}		
}
		
	public void processAdd6Player(MouseEvent e) {
		for(int i = PokerGame.NUM_PLAYERS; PokerGame.NUM_PLAYERS > 0; i--) {
			model.removePlayer();
			players.getChildren().remove(PokerGame.NUM_PLAYERS);}
		
		if(PokerGame.NUM_PLAYERS < 6) {
		for(int i = 0; i <= 5; i++) {
		model.addPlayer();	
		PlayerPane pp = new PlayerPane();
		pp.setPlayer(model.getPlayer(PokerGame.NUM_PLAYERS-1));
		
		switch(i) {
		case 0:
			players.add(pp, 0, 0);
			break;
		case 1:
			players.add(pp, 1, 0);
			break;
		case 2:
			players.add(pp, 0, 1);
			break;
		case 3:
			players.add(pp, 1, 1);
			break;
		case 4:
			players.add(pp, 0, 2);
			break;
		case 5:
			players.add(pp, 1, 2);
		
				}
			}
		}
	}
	
	
		
	public void processExitGame(MouseEvent e) {		
		this.stage.close();
		
		
	}
	
	public void processEnableAutoShuffle(MouseEvent e) {
		enableautoShuffle.setText("Auto Shuffle On");
		autoshuffleon = true;
		enableautoShuffle.setDisable(true);
		disableautoShuffle.setDisable(false);
		disableautoShuffle.setText("Disable Auto Shuffle");
	}
	
	public void processdisableautoshuffle(MouseEvent e) {
		disableautoShuffle.setText("Auto Shuffle Disabled");
		enableautoShuffle.setText("Auto Shuffle");
		autoshuffleon = false;
		disableautoShuffle.setDisable(true);
		enableautoShuffle.setDisable(false);
	}
	
	public boolean getAutoshuffleon() {
		return autoshuffleon;
	}

	public void updateWinner(Player winner) {
		for(int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			if (winner == getPlayerPane(i).getPlayer()) {
				getPlayerPane(i).increaseCounter();
			}
			
		
		}
		
	}
	
	public Button getChangePlayerNameButton() {
		return changeNames;
	}
	
	public Stage getStage() {
		return stage;
	}

	public void updatePlayer(int i) {
		this.getPlayerPane(i).updateNames();
	}
	
}
