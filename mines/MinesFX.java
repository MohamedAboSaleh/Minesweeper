package mines;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {
	// local variables
	private int height = 10, width = 10, mines = 10;// default values
	private Mines m = new Mines(height, width, mines);// default game
	Q4Controller c;
	HBox h;
	GridPane g;
	Stage s;
	Scene scene;
	boolean end = false;// if its true so the game has finished

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		s = stage;

		FXMLLoader l = new FXMLLoader();// get the loader
		l.setLocation(getClass().getResource("q4.fxml"));// set the location
		h = l.load();

		c = l.getController();// get the controller
		Button r = c.resetbutton();// getting the reset button
		r.setOnAction(new EventHandler<ActionEvent>() {// if the player clicked the reset button it reset the game with
														// the values in the fields (height,width,number of mines)

			@Override
			public void handle(ActionEvent e) {
				reset();// calling the reset function

			}
		});
		g = GridPaneCreator();// getting the buttons grid
		h.getChildren().add(g);
		scene = new Scene(h);
		stage.setTitle("The Amazing Mines Sweeper");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

	}

	void update() {// this method updates the game all the time
		h.getChildren().remove(1);// remove the grid
		g.getChildren().removeAll();
		g = GridPaneCreator();// update the grid
		h.getChildren().add(g);

		s.setScene(scene);
		s.sizeToScene();

	}

	void reset() {
		end = false;// reset the end variable
		// geting the numbers in the text areas
		height = c.retHeight();
		width = c.retWidth();
		mines = c.retMines();
		if (mines >= height * width || mines < 0 || height <= 0 || width <= 0) {// if the input is wrong
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setContentText("you should insert correct input");// set the alert text
			a.showAndWait();// showing the alert
		} else {
			m = new Mines(height, width, mines);// if the input is ok
			update();
		}

	}

	private GridPane GridPaneCreator() {
		g = new GridPane();
		g.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				{
//Declaring buttons and adding them to the grid
					Button button = new XYButton(i, j, m.get(i, j));
					button.setPrefSize(40, 40);
					button.setAlignment(Pos.CENTER);
					g.add(button, i, j);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							XYButton button = (XYButton) event.getSource();

							if (end) {// if its end the alert apears
								m.setShowAll(true);
								Alert a = new Alert(Alert.AlertType.INFORMATION);
								a.setContentText("You have to reset the game");
								a.showAndWait();
							} else if (event.getButton() == MouseButton.PRIMARY
									&& m.get(button.getX(), button.getY()) != "F")// if the click was left click

							{
								if (!m.open(button.getX(), button.getY())) {// if the open returns false then the game
																			// is over
									end = true;
									m.setShowAll(true);
									Alert a = new Alert(Alert.AlertType.INFORMATION);
									a.setContentText("You lost!");
									a.showAndWait();
								} else if (m.isDone()) {// if its done then he won
									m.setShowAll(true);
									Alert a = new Alert(Alert.AlertType.INFORMATION);
									a.setContentText("OMG, You won!!!");
									a.showAndWait();
								}
							} else if (event.getButton() == MouseButton.SECONDARY)// if the click was right click
								m.toggleFlag(button.getX(), button.getY());

							update();// update the game
						}
					});
				}
			}
		}
		return g;

	}

}
