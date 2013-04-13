package logic;

public class Eagle extends Casa{
	private boolean active;
	private boolean hasSword;
	private int initX;
	private int initY;
	private boolean isDown;
	private boolean alive;
	private int x;
	private int prevx;
	private int prevy;
	private boolean isWall=false;
	
	
	public boolean isDown() {
		return isDown;
	}


	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}


	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
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


	private int y;
	
	
	
	
	public Eagle(int x, int y){
		super('G');
		this.x=x;
		this.y=y;
		setActive(false);
		setHasSword(false);
		setInitX(0); 
		setInitY(0);
		isDown=false;
		alive=true;
		isWall=false;
	}
	

	public char getLetra(){
		return letra;
	}


	public int getInitX() {
		return initX;
	}


	public void setInitX(int initX) {
		this.initX = initX;
	}


	public int getInitY() {
		return initY;
	}


	public void setInitY(int initY) {
		this.initY = initY;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public boolean isHasSword() {
		return hasSword;
	}


	public void setHasSword(boolean hasSword) {
		this.hasSword = hasSword;
	}


	public int getPrevx() {
		return prevx;
	}


	public void setPrevx(int prevx) {
		this.prevx = prevx;
	}


	public int getPrevy() {
		return prevy;
	}


	public void setPrevy(int prevy) {
		this.prevy = prevy;
	}


	public boolean isWall() {
		return isWall;
	}


	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}
}
