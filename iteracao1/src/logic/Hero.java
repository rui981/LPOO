package logic;


public class Hero extends Casa {
	 private int x;
	 private int y;
	 private boolean haveSword;
	
	 public Hero(int x, int y) {
		super('H');
		 this.x=x;
		this.y=y;
		setHaveSword(false);
	}
	public int getX() {
	return x;
			}
	public int getY(){
		return y;
	}
	public void setY(int i) {
		this.y=i;
		
	}
	public void setX(int i) {
		this.x=i;
		
	}
	 
	public char getLetra(){
		return letra;
	}
	
	public void setLetra(char le){
		letra=le;
	}
	public boolean isHaveSword() {
		return haveSword;
	}
	public void setHaveSword(boolean haveSword) {
		this.haveSword = haveSword;
	}
	
}
