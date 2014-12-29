package maze;

public class Cell {
	public static final int PATH = 0, WALL = 1;
	public int x, y, value;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Cell(int x, int y, int value) {
		this(x, y);
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + x + " ," + y + ") = "
				+ ((value == PATH) ? "PATH" : "WALL");
	}
}
