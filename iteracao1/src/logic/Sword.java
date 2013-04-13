package logic;



public class Sword extends Casa {
	
	private int x;
	private int y;
	private int inicialx;
	private int inicialy;
	
	
	 public Sword(int x, int y){
		 super('E');
		 this.setX(x);
		 this.setY(y);
		 inicialx=x;
		 inicialy=y;
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
	public int getInicialx() {
		return inicialx;
	}
	public void setInicialx(int inicialx) {
		this.inicialx = inicialx;
	}
	public int getInicialy() {
		return inicialy;
	}
	public void setInicialy(int inicialy) {
		this.inicialy = inicialy;
	}

	 
	 
	
}
