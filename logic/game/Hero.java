package logic.game;





public class Hero extends Casa {
	 public int x,y;
	 public boolean haveSword;
	 public Hero(int x, int y) {
		this.x=x;
		this.y=y;
		haveSword=false;
		letra = 'H';
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
	 
	
}
