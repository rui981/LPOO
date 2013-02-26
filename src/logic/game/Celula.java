package logic.game;

public class Celula{
	private int x;
	private int y;
	public boolean visited;
	protected char symbol;
	
	public Celula(int x, int y){
		this.setX(x);
		this.setY(y);
		visited= false;
		setSymbol(' ');
	}
	public Celula(){
		
		visited= false;
		setSymbol(' ');
	}
	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	   
}
