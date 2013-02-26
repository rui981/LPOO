package logic.game;



public class Hero extends Celula {
	 public int x,y;
	 public boolean haveSword;
	 public char symbol;
	 public Hero(int x, int y) {
		this.x=x;
		this.y=y;
		haveSword=false;
		symbol = 'H';
	}
	 
	
}
