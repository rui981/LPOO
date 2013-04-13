package logic;

public class Eagle extends Casa{
	boolean active;
	boolean hasSword;
	public int initX, initY;
	boolean isDown;
	boolean alive;
	public int x, y;
	
	
	public Eagle(int x, int y){
		super('G');
		this.x=x;
		this.y=y;
		active = false;
		hasSword=false;
		initX=0; 
		initY=0;
		isDown=false;
		alive=true;
	}
	

	public char getLetra(){
		return letra;
	}
}
