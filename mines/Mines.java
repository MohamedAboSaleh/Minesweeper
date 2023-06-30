package mines;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mines {// declare variables
	private int height, width;
	private Place[][] minesweeper;
	public static Random rand = new Random();
	private boolean showAll = false;

	// ====================== INNER CLASS =========================//
	// an instance of this class is for real a place that may has a mine or has a
	// flag or opened
	private class Place {
		private boolean hasMine = false, opened = false, hasFlag = false;
		private int neighborMines = 0;

		@Override
		public String toString() {

			if (opened | showAll) {
				if (hasMine)
					return "X";
				else if (neighborMines == 0)
					return " ";
				else
					return "" + neighborMines;
			} else {
				if (hasFlag)
					return "F";
				else
					return ".";
			}
		}
	}
	// ----------------------------------------------------------//

	// ====================== INNER CLASS =========================//
//this class gets i and j and return the span of all the neighbors as list
	private class PlaceNeighbors {
		int i, j;
		List<PlaceNeighbors> l = new LinkedList<PlaceNeighbors>();

		private PlaceNeighbors(int i, int j) {
			this.i = i;
			this.j = j;

		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}

		public List<PlaceNeighbors> getNeighbors() {//adding the neighbors to the list
			if (i - 1 >= 0) {
				if (j - 1 >= 0)
					l.add(new PlaceNeighbors(i - 1, j - 1));
				if (j + 1 < width)
					l.add(new PlaceNeighbors(i - 1, j + 1));
				l.add(new PlaceNeighbors(i - 1, j));

			}

			if (i + 1 < height) {
				if (j - 1 >= 0)
					l.add(new PlaceNeighbors(i + 1, j - 1));
				if (j + 1 < width)
					l.add(new PlaceNeighbors(i + 1, j + 1));
				l.add(new PlaceNeighbors(i + 1, j));
			}
			if (j - 1 >= 0) {
				l.add(new PlaceNeighbors(i, j - 1));
			}
			if (j + 1 < width) {
				l.add(new PlaceNeighbors(i, j + 1));
			}

			return l;
		}

	}

//------------------------------------------------------------//

	public Mines(int height, int width, int numMines) {// constructor
		this.height = height;
		this.width = width;

		minesweeper = new Place[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)// initialize every cell in the 2d array
				minesweeper[i][j] = new Place();
		for (int i = 0; i < numMines; i++) {// randomly put a mine in the array
			int x = rand.nextInt(height);
			int y = rand.nextInt(width);
			if (minesweeper[x][y].hasMine)// if its already has mine we should put mine in another place
				i--;
			else {
				minesweeper[x][y].hasMine = true;
				PlaceNeighbors temp = new PlaceNeighbors(x, y);
				List<PlaceNeighbors> l = temp.getNeighbors();// gets the list of neighbors of this place
				for (int j = 0; j < l.size(); j++)
					minesweeper[l.get(j).getI()][l.get(j).getJ()].neighborMines++;// update the neighbors number of
																					// mines around the place

			}
		}
	}

	public boolean addMine(int i, int j) {
		if (minesweeper[i][j].hasMine == true || minesweeper[i][j].opened == true)// if its already have mine or its
																					// opened we return false
			return false;
		minesweeper[i][j].hasMine = true;// update the hasmine field
		PlaceNeighbors temp = new PlaceNeighbors(i, j);
		List<PlaceNeighbors> l = temp.getNeighbors();
		for (int k = 0; k < l.size(); k++)
			minesweeper[l.get(k).getI()][l.get(k).getJ()].neighborMines++;// update the neighbors number of
		// mines around the place

		return true;
	}

	public boolean open(int i, int j) {
		if (minesweeper[i][j].opened)//if its already opened
			return true;

		if (minesweeper[i][j].hasMine)//if it has mine we return false thats mean the player lost
			return false;
		minesweeper[i][j].opened = true;//update the opened field
		if (minesweeper[i][j].neighborMines == 0) {//if there are no mines around this place
			PlaceNeighbors temp = new PlaceNeighbors(i, j);
			List<PlaceNeighbors> l = temp.getNeighbors();
			for (int k = 0; k < l.size(); k++)
				open(l.get(k).getI(), l.get(k).getJ());//Recursively open the neighbors
		}

		return true;
	}

	public void toggleFlag(int x, int y) {
		if (!minesweeper[x][y].opened)//if its not opened
			minesweeper[x][y].hasFlag = !minesweeper[x][y].hasFlag;// change the value of the hasflag field
	}

	public boolean isDone() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (!minesweeper[i][j].hasMine && !minesweeper[i][j].opened)
					return false;//if there is at least one place that it not mine and still not opened we return false
		return true;
	}

	public String get(int i, int j) {
		return minesweeper[i][j].toString();
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;//set the show all variable
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				b.append(get(i, j));
			b.append("\n");
		}
		return b.toString();

	}

}
