
class Coin {
	private int x, y;
	private boolean collected;

	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		this.collected = false;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isCollected() { return collected; }
	public void collect() { collected = true; }
}