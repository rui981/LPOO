package logic;

public class Casa {
	

	protected char letra = 'X';
	private boolean visited = true;

	/**
	 * @return the letra
	 */
	public char getLetra() {
		return letra;
	}

	/**
	 * @param letra
	 *            the letra to set
	 */
	public void setLetra(char letra) {
		this.letra = letra;
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * @param visited
	 *            the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Casa() {
	}
	
	public Casa(char letra){
		this.letra=letra;
	}

}
