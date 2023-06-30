package mines;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Q4Controller {// controller class

	@FXML
	private Button reset;

	@FXML
	private TextArea widthtxt;
	@FXML
	private TextArea heighttxt;

	@FXML
	private TextArea minestxt;

	@FXML
	void resetFunc(ActionEvent event) {

	}

	public Button resetbutton() {
		return reset;// return the reset button
	}

	public int retWidth() {
		return Integer.parseInt(widthtxt.getText());// return the string in the widthtxt as integer
	}

	public int retHeight() {
		return Integer.parseInt(heighttxt.getText());// return the string in the heighttxt as integer
	}

	public int retMines() {
		return Integer.parseInt(minestxt.getText());// return the string in the minestxt as integer
	}
}
