package logic.game;

public class Dragon extends Casa {
	public int x, y;
	private boolean adormecido = false;

	public Dragon(int x, int y) {
		this.x = x;
		this.y = y;
		letra = 'D';
	}

	/**
	 * @return the adormecido
	 */
	public boolean isAdormecido() {
		return adormecido;
	}

	/**
	 * @param adormecido
	 *            the adormecido to set
	 */
	public void setAdormecido(boolean adormecido) {
		this.adormecido = adormecido;
	}


}
