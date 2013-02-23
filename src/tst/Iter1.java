package tst;

import java.io.IOException;
import java.util.Random;

public class Iter1 {

	private static final int BOARDSIZE = 10;
	private static final String Random = null;



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char maze[][] = new char[BOARDSIZE][BOARDSIZE];
		Hero h = new Hero(1, 1);
		Dragon d = new Dragon (3,1);
		createMaze(maze);

		fillMaze(maze);
		insertExit(maze, 6, 9);
		insertHero(maze, h.x, h.y, h);
		insertSword(maze, 8, 1);
		insertDragon(maze, d);
		printMaze(maze);
		moveHero(maze, h,d);

	}

	public static void createMaze(char maze[][]) {

		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {

				if ((i == 0 || i == BOARDSIZE-1) || (j == 0 || j == BOARDSIZE-1)) {
					maze[i][j] = 'X';
				} else {
					maze[i][j] = ' ';
				}

			}
		}
	}

	public static void printMaze(char maze[][]) {
		for (int i = 0; i < BOARDSIZE; i++) {
			System.out.print(" ");
			for (int k = 0; k < BOARDSIZE; k++) 
			System.out.print("----");
			System.out.println();
			System.out.print("| ");
			for (int j = 0; j < BOARDSIZE; j++) {

				System.out.print(maze[i][j]);
				System.out.print(" | ");

			}
			System.out.println();

		}
		for (int k = 0; k < BOARDSIZE; k++) 
			System.out.print("----");
	}

	public static void fillMaze(char maze[][]) {
		int tX,tY;
		Random pX= new Random();
		tX=pX.nextInt(4);
		tY = pX.nextInt(BOARDSIZE-2)+1;
		System.out.println(tX);
		System.out.println(tY);
		switch (tX) {
		case 0: //em cima
			maze[0][tY]='S';
			break;
		case 1: // direita
			maze[tY][BOARDSIZE-1]= 'S';
			break;
		case 2: // baixo 
			maze[BOARDSIZE-1][tY]='S';
			break;
		case 3: // esquerda
			maze[tY][0]= 'S';
			break;
		default:
			break;
		}
	}

	public static void insertExit(char maze[][], int i, int j) {
		maze[i][j] = 'S';
	}

	public static void insertHero(char maze[][], int i, int j, Hero h) {
		maze[i][j] = h.symbol;
	}

	public static void insertSword(char maze[][], int i, int j) {
		maze[i][j] = 'E';
	}
	
	public static void insertDragon(char maze[][], Dragon d){
		maze[d.x][d.y] = d.symbol;
	}

	public static void delete(char maze[][], int i, int j) {
		maze[i][j] = ' ';
	}
	
	public static int randomMove() {
		Random nr = new Random();
		return nr.nextInt(4);
	}

	
	public static int checkKill(char maze[][],Hero h){
		
		if(maze[h.x][h.y+1]=='D'){
			if (h.haveSword){
				System.out.println("You killed the dragon! You are the dragon born.");
				delete(maze, h.x, h.y+1);
				return 0;
			} 
		}
		
		if(maze[h.x][h.y-1]=='D'){
			if (h.haveSword){
				System.out.println("You killed the dragon! You are the dragon born.");
				delete(maze, h.x, h.y-1);
				return 0;
			}
		}
		
		if(maze[h.x+1][h.y]=='D'){
			if (h.haveSword){
				System.out.println("You killed the dragon! You are the dragon born.");
				delete(maze, h.x+1, h.y);
				return 0;
			}
		}	
		
		if(maze[h.x-1][h.y]=='D'){
			if (h.haveSword){
				System.out.println("You killed the dragon! You are the dragon born.");
				delete(maze, h.x-1, h.y);
				return 0;
			}
		}	
				
		if(maze[h.x-1][h.y]=='D' || maze[h.x+1][h.y]=='D' 
				|| maze[h.x][h.y-1]=='D' || maze[h.x][h.y+1]=='D'){
			
		System.out.println("You were killed by the Dragon and you lost the game.. Noob");
		return 1;		
		
	}
		return 2;
	}
	
	public static void moveDragon(char maze[][],Dragon d, int dir){
		int posx, posy;
		posx=d.x;
		posy=d.y;
		switch (dir) {
		case 0://esquerda
			
			if(maze[d.x][d.y-1]=='E'){
				d.symbol='F';
				d.y--;
			}else
			
			if(maze[d.x][d.y-1]==' '){
				if(d.symbol== 'F'){
					maze[d.x][d.y]='E';
					d.symbol='D';
				}
				d.y--;
				
		}	
			break;
		case 1: //direita
			if(maze[d.x][d.y+1]=='E'){
				d.symbol='F';
				d.y++;
			}else
			
			if(maze[d.x][d.y+1]==' '){
				if(d.symbol== 'F'){
					maze[d.x][d.y]='E';
					d.symbol='D';
				}
				d.y++;
			
		}	
		
			break;
		case 2: // cima
			if(maze[d.x-1][d.y]=='E'){
				d.symbol='F';
				d.x--;
			}else
			if(maze[d.x-1][d.y]==' '){
				if(d.symbol== 'F'){
					maze[d.x][d.y]='E';
					d.symbol='D';
				}
				d.x--;
			
		}	
			break;
		case 3:// baixo
			if(maze[d.x+1][d.y]=='E'){
				d.symbol='F';
				d.x++;
			}else
			if(maze[d.x+1][d.y]==' '){
				if(d.symbol== 'F'){
					maze[d.x][d.y]='E';
					d.symbol='D';
				}
					
				d.x++;
			
		}	
			break;

		default:
			break;
		}
		delete(maze, posx, posy);
		insertDragon(maze, d);
		
	}
	
	
	
	public static void moveHero(char maze[][], Hero h, Dragon d) {
		char dir = 0;
		int end, nr;
		boolean b = true;
		while (b) {
			int posi = h.x;
			int posj = h.y;
			
			end=checkKill(maze, h);
			if(end==1)
				return;
			
			
			//TODO ver Keylistener
			try {
				dir = (char) System.in.read();
			} catch (IOException e) {
				System.out.println("Erro!!!1");
				e.printStackTrace();
			}

			switch (dir) {
			case 'd':
				if (maze[h.x][h.y + 1] != 'X') {

					if(maze[h.x][h.y + 1] == 'S'){
						if(h.haveSword=true){
							System.out.println("You won the game! GG WP");
							b=false;
						}

					}else{
						if (maze[h.x][h.y + 1] == 'E')
						{
							h.symbol = 'A';
							h.haveSword=true;
						}
						
						
					}
					h.y++;
				}

				break;
			case 'a':
				if (maze[h.x][h.y - 1] != 'X') {
					if (maze[h.x][h.y - 1] == 'E')
					{
						h.haveSword=true;
						h.symbol = 'A';
					}
					h.y--;
				}

				break;
			case 'w':
				if (maze[h.x - 1][h.y] != 'X') {
					if (maze[h.x - 1][h.y] == 'E')
					{
						h.haveSword=true;
						h.symbol = 'A';
					}
					h.x--;
				}

				break;
			case 's':
				if (maze[h.x + 1][h.y] != 'X') {
					if (maze[h.x + 1][h.y] == 'E')
					{
						h.haveSword=true;
						h.symbol = 'A';
					}
						h.x++;
				}

				break;
			case 'p':
				b = false;
				break;
			default:
				
				break;
			}
			delete(maze, posi, posj);
			insertHero(maze, h.x, h.y, h);
			nr =randomMove();
			System.out.println(nr);
			moveDragon(maze, d, nr);
			printMaze(maze);
		}
	}

}
