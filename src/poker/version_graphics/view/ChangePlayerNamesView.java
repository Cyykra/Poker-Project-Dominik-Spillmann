package poker.version_graphics.view;


import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;


public class ChangePlayerNamesView extends Stage {
	
	private int i;
	private Label oldName;
	private TextField newName;
	private Button safe;
	private Button cancel;
	private PokerGameModel model;
	private VBox mid;

	public ChangePlayerNamesView(ArrayList<Player> players) {
		super();
		
		BorderPane root = new BorderPane();
		root.setPrefSize(275, 200);
		
		//GridPane with all Players visible
		mid = new VBox();
		int i = 0;
		for(Player p : players) {
			HBox box = new HBox();
			newName = new TextField();
			oldName = new Label("Player " + i);
			Region r = new Region();
			r.setPrefWidth(30);
			box.getChildren().addAll(oldName, r,newName);
			mid.getChildren().add(box);
			i++;
		}
		
		root.setCenter(mid);
		
		//Controlarea with safe and cancel
		HBox bottom = new HBox();
		safe = new Button("Safe");
		cancel = new Button("Cancel");
		Region rleft = new Region();
		safe.setPrefSize(50, 50);
		cancel.setPrefSize(70, 50);
		rleft.setPrefSize(150, 30);
		bottom.setPrefSize(300, 30);
		
		bottom.getChildren().addAll(rleft, safe, cancel);
		root.setBottom(bottom);
		
		
		//Scene
		Scene scene = new Scene(root);
		this.setTitle("Change Player Names");
		this.initModality(Modality.WINDOW_MODAL);
		this.setScene(scene);
		this.setResizable(false);
		
		
}

	public Button getSafeButton() {
		return safe;
	}
	
	public Button getCancelButton() {
		return cancel;
	}
	
	public ArrayList<String> getPlayerNames(){
		ArrayList<String> names = new ArrayList<String>();
		for( i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			HBox box = (HBox) mid.getChildren().get(i);
			TextField txt = (TextField)box.getChildren().get(2);
			names.add(txt.getText());
		}
		
		return names;
	}
	
	
}

    

