package logic;

public class Dragon extends Casa {
	private int x;
	private int y;
	private boolean adormecido = false;
	private boolean aboveSword = false;
	
	private boolean alive;

	public Dragon(int x, int y) {
		super('D');
		this.setX(x);
		this.setY(y);
		alive=true;
	}

	/**
	 * @return the adormecido
	 */
	public boolean isAdormecido() {
		return adormecido;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @param adormecido
	 *            the adormecido to set
	 */
	public void setAdormecido(boolean adormecido) {
		this.adormecido = adormecido;
	}
	
	public char getLetra(){
		return letra;
	}

	public boolean isAboveSword() {
		return aboveSword;
	}

	public void setAboveSword(boolean aboveSword) {
		this.aboveSword = aboveSword;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


}
