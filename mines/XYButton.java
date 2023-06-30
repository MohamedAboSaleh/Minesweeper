package mines;

import javafx.scene.control.Button;

public class XYButton extends Button {
//this class is for the buttons so we can get the coordinates when we want
	private int x, y;

	public XYButton(int x, int y, String txt) {
		super(txt);
		this.x = x;
		this.y = y;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}