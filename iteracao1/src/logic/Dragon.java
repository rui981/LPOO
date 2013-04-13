package logic;

public class Dragon extends Casa {
	public int x, y;
	private boolean adormecido = false;
	
	boolean alive= true;

	public Dragon(int x, int y) {
		super('D');
		this.x = x;
		this.y = y;
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


}
