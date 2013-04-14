package logic;

import java.io.IOException;
import java.security.AllPermission;
import java.util.Random;
import ui.Interface;
  
import logic.Casa;
import logic.Dragon;
import logic.Hero;
import logic.Sword;
import logic.Eagle;

public class Maze implements Builder {

	private int[] b = new int[2];

	public  static int tamanho;
	public  static int dificuldade;
	public static  int numDragoes;

	private static Dragon[] d;


	public static Hero h = new Hero(1, 2);
	public static Sword s = new Sword(8, 1);
	public static Exit exit = new Exit();
	public  static Eagle g = new Eagle(h.getX(), h.getY());
	public static Casa[][] board;


	public void fillDragons() {

		for (int i = 0; i < numDragoes; i++) {
			d[i] = new Dragon(i, i);
		}
		insertDragon();
	}

	// imprime a maze depois de construida
	public static void print_maze() {
		for (int i = 0; i < tamanho; i++) {
			System.out.println();
			for (int k = 0; k < tamanho; k++) {
				System.out.print(" ");
				System.out.print(board[i][k].getLetra());

			}
		}
	}



	public Maze() throws Exception {
		d= new Dragon[numDragoes];
		board = new Casa[tamanho][tamanho];

		for (int i = 0; i < tamanho; i++) {
			for (int k = 0; k < tamanho; k++) {
				board[i][k] = new Casa();
				if (k % 2 != 0 && i % 2 != 0) {
					board[i][k].setVisited(false);
					board[i][k].setLetra(' ');
				}
			}
		}

		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if ((i == 0 || i == tamanho - 1)
						|| (j == 0 || j == tamanho - 1)) {
					board[i][j].setVisited(true);
					board[i][j].setLetra('X');
				}
			}
		}

		b = findExit();
		find(b[0], b[1]);
		termina();
		insertSword();
		insertHero();
		fillDragons();

	}

	/**
	 * Vector
	 * 
	 * @return the board
	 */
	public Casa[][] getBoard() {
		return board;
	}
	

	/**
	 * @param board
	 *            the board to set
	 */
	public void setBoard(Casa[][] board) {
		Maze.board = board;
	}

	public void insertHero(int i, int j, Hero h) {
		if(!h.isHaveSword())
			board[i][j].setLetra('H');
		else
			board[i][j].setLetra('A');
	}

	public void insertEagle(int i, int j, Eagle g) {
		board[i][j].setLetra(g.getLetra());
	}

	public void insertDragon(Dragon dragon) {
		if(!dragon.isAboveSword())
			board[dragon.getX()][dragon.getY()].setLetra('D');
		else
			board[dragon.getX()][dragon.getY()].setLetra('F');
	}

	public void insertSword(int i, int j) {
		board[i][j].setLetra('E');
	}

	public void findExit(Casa[][] maze) {
		int tX, tY;
		Exit exit = new Exit();
		Random pX = new Random();
		tX = pX.nextInt(4);
		tY = pX.nextInt(tamanho - 2) + 1;
		System.out.println(tX);
		System.out.println(tY);
		switch (tX) {
		case 0: // em cima
			board[0][tY] = exit;
			board[1][tY].setLetra(' ');
			board[1][tY].setVisited(true);
			break;
		case 1: // direita
			board[tY][tamanho - 1] = exit;
			board[tY][tamanho - 2].setLetra(' ');
			board[tY][tamanho - 2].setVisited(true);
			break;
		case 2: // baixo
			board[tamanho - 1][tY] = exit;
			board[tamanho - 2][tY].setLetra(' ');
			board[tamanho - 2][tY].setVisited(true);
			break;
		case 3: // esquerda
			board[tY][0] = exit;
			board[tY][1].setLetra(' ');
			board[tY][1].setVisited(true);
			break;
		default:
			break;
		}
	}

	public void moveHero(Hero h, Dragon[] d, Sword s, Eagle e) throws Exception {

		String dir="";
		int end = 0;

		int posi = h.getX();
		int posj = h.getY();
		boolean b = true;

		while (b) {
			
			posi = h.getX();
			posj = h.getY();
			
			delete(posi, posj);
			// TODO ver Keylistener
			dir=Interface.uiMove(dir);

			switch (dir) {
			

			case "d":
				if (board[h.getX()][h.getY() + 1].getLetra() != 'X') {
					if (board[h.getX()][h.getY() + 1].getLetra() == 'S') {

						if (h.isHaveSword() && checkalldead()) {
							System.out.println("You won the game! GG WP");
							b = false;
							return;
						}
						break;

					} else {
						if (board[h.getX()][h.getY() + 1].getLetra() == 'E'
								|| (board[h.getX()][h.getY() + 1].getLetra() == 'G'
								&& e.isHasSword() && e.isAlive())) {
							// h.setLetra();
							h.setHaveSword(true);
							e.setHasSword(false);
							//h.setLetra('A');

						}
					}

					h.setY(h.getY() + 1);

					
					
						 if ((!e.isActive()) && e.isAlive()) {
						e.setY(e.getY() + 1);
					}
						 if (!h.isHaveSword()){
					if ((e.isActive()) && (!e.isHasSword())) {
						moveEagle(e, board, s);
						 }
					}

					gamePlay2(posi, posj);
					// gamePlay(posi, posj, e, d, h, s);

				}

				break;
			case "a":
				if (board[h.getX()][h.getY() - 1].getLetra() != 'X') {
					if (board[h.getX()][h.getY() - 1].getLetra() == 'S') {
						if (h.isHaveSword() && checkalldead()) {
							System.out.println("You won the game! GG WP");
							b = false;

						}
						break;

					} else {
						if (board[h.getX()][h.getY() - 1].getLetra() == 'E'
								|| (board[h.getX()][h.getY() - 1].getLetra() == 'G'
								&& e.isHasSword() && e.isAlive())) {
							h.setHaveSword(true);
							// h.setLetra();
							e.setHasSword(false);
							//h.setLetra('A');

						}
					}

					h.setY(h.getY() - 1);

					 
					if ((!e.isActive()) && e.isAlive()) {
						e.setY(e.getY()-1);
					}
					if (!h.isHaveSword()){
					if ((e.isActive()) && (!e.isHasSword())) {
						moveEagle(e, board, s);
						 }
					}
					gamePlay2(posi, posj);
					// gamePlay(posi, posj, e, d, h, s);
				}

				break;
			case "w":
				if (board[h.getX() - 1][h.getY()].getLetra() != 'X') {
					if (board[h.getX() - 1][h.getY()].getLetra() == 'S') {
						if (h.isHaveSword() && checkalldead()) {
							System.out.println("You won the game! GG WP");
							b = false;

						}
						break;

					} else {
						if (board[h.getX() - 1][h.getY()].getLetra() == 'E'
								|| (board[h.getX() - 1][h.getY()].getLetra() == 'G'
								&& e.isHasSword() && e.isAlive())) {
							h.setHaveSword(true);
							// h.setLetra();
							//h.setLetra('A');
							e.setHasSword(false);

						}
					}

					h.setX(h.getX() - 1);

					
					if ((!e.isActive()) && e.isAlive()) {
						e.setX(e.getX() - 1);

					}
					 if (!h.isHaveSword()){
					if ((e.isActive()) && (!e.isHasSword())) {
						moveEagle(e, board, s);
						 }
					}
					gamePlay2(posi, posj);
					// gamePlay(posi, posj, e, d, h, s);
				}

				break;
			case "s":
				if (board[h.getX() + 1][h.getY()].getLetra() != 'X') {

					if (board[h.getX() + 1][h.getY()].getLetra() == 'S') {
						if (h.isHaveSword() && checkalldead()) {
							System.out.println("You won the game! GG WP");
							b = false;

						}
						break;

					} else {
						if (board[h.getX() + 1][h.getY()].getLetra() == 'E'
								|| (board[h.getX() + 1][h.getY()].getLetra() == 'G'
								&& e.isHasSword() && e.isAlive())) {
							e.setHasSword(false);
							h.setHaveSword(true);
							//h.setLetra('A');

						}
					}
					h.setX(h.getX() + 1);

					
					if ((!e.isActive()) && e.isAlive()) {
						e.setX(e.getX() + 1);
					}

					if (!h.isHaveSword()){

					if ((e.isActive()) && (!e.isHasSword())) {
						moveEagle(e, board, s);
						 }
					}
					gamePlay2(posi, posj);
					// gamePlay(posi, posj, e, d, h, s);
				}

				break;

			case "e":
				e.setActive(true);
				e.setInitX(e.getX());
				e.setInitY(e.getY());
				moveEagle(e, board, s);
				gamePlay2(posi, posj);
				
				break;

			case "p":
				b = false;
				break;
			default:
				break;
			}
			
			
			
				for (int i = 0; i < d.length; i++) {
					end = checkKill(h, d[i]);

					if (end == 1){
						b=false;
						return;
					}
					

				}
			

			moveAllDragons();

		}

	}

	public  boolean checkDragon(Dragon dragon) throws Exception {
		if (g.isDown() && dragon.isAlive() && g.isAlive()) {
			if (board[g.getX() - 1][g.getY()].getLetra() == 'D'
					|| board[g.getX() + 1][g.getY()].getLetra() == 'D'
					|| board[g.getX()][g.getY() - 1].getLetra() == 'D'
					|| board[g.getX()][g.getY() + 1].getLetra() == 'D') {
				
				g.setAlive(false);
				g.setActive(false);
				delete(g.getX(),g.getY());
				board[g.getX()][g.getY()].setLetra('S');

				System.out.println("Your eagle was killed by the Dragon...");
				return true;
			}
		}
		return false;
	}

	

	public void gamePlay2(int posi, int posj) throws Exception {
		delete(posi, posj);

		for(int i=0; i<d.length; i++)
			checkDragon(d[i]);
		
		
		if (g.isHasSword() && g.isAlive())
			eagleReturn(g, s);

		if (g.isAlive())
			insertEagle(g.getX(), g.getY(), g);

		insertHero(h.getX(), h.getY(), h);

		print_maze();
	}

	public void eagleReturn(Eagle e, Sword s) {

		if (e.getX() > e.getInitX() ) {
			
			if(!e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX() - 1][e.getY()].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			
			e.setX(e.getX()-1);
			s.setX(s.getX() - 1);
			return;
		}

		if (e.getX() < e.getInitX()) {
			
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX() + 1][e.getY()].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			e.setX(e.getX() + 1);
			s.setX(s.getX() + 1);
			return;
		}

		if (e.getY() > e.getInitY() ) {
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX()][e.getY()-1].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			e.setY(e.getY()-1);
			s.setY(s.getY() - 1);
			return;
		}

		if (e.getY() < e.getInitY() ) {
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX()][e.getY()+1].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			e.setY(e.getY()+1);
			s.setY(s.getY() + 1);
			return;
		}

		if (e.getX() == e.getInitX() && e.getY() == e.getInitY()) {
			e.setDown(true);
			e.setActive(false);
		}

	}


	
	public  void moveEagle(Eagle e, Casa[][] board, Sword s) {
		
		
		if (e.getX() > s.getInicialx()) {

			if (board[e.getX() - 1][e.getY()].getLetra() == 'E') {
				e.setHasSword(true);
			}
			
				
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX() - 1][e.getY()].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			
			e.setX(e.getX()-1);
			return;
		}

		if (e.getX() < s.getInicialx()) {
			if (board[e.getX() + 1][e.getY()].getLetra() == 'E') {
				e.setHasSword(true);
			}

			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX() + 1][e.getY()].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			e.setX(e.getX()+1);

			return;
		}

		if (e.getY() < s.getInicialy()) {

			if (board[e.getX()][e.getY() + 1].getLetra() == 'E') {
				e.setHasSword(true);
			}
			
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX() ][e.getY()+1].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			e.setY(e.getY()+1);

			return;
		}

		if (e.getY() > s.getInicialy()) {
			if (board[e.getX()][e.getY() - 1].getLetra() == 'E') {
				e.setHasSword(true);
			}
			
			if(! e.isWall())
				delete(e.getX(), e.getY());
			else
			{
				board[e.getX()][e.getY()].setLetra('X');
				e.setWall(false);
			}
				
			
			if(board[e.getX()][e.getY()-1].getLetra() == 'X')
			{
			e.setWall(true);
			
			}
			
			e.setY(e.getY()-1);

			return;
		}
	}

	public  void delete(int i, int j) {
		board[i][j].setLetra(' ');
	}

	public  int randomMove() {
		Random nr = new Random();
		return nr.nextInt(4);
	}

	public int checkKill(Hero h, Dragon dragon) {
		if (dragon.isAlive()) {

			if (board[h.getX()][h.getY() + 1].getLetra() == 'D') {
				if (h.isHaveSword()) {
					System.out.println("You killed the dragon! You are the dragon born.");
					delete(h.getX(), h.getY() + 1);
					dragon.setAlive(false);
					return 0;
				}
			}

			if (board[h.getX()][h.getY() - 1].getLetra() == 'D') {
				if (h.isHaveSword()) {
					System.out
					.println("You killed the dragon! You are the dragon born.");
					delete(h.getX(), h.getY() - 1);
					dragon.setAlive(false);
					return 0;
				}
			}

			if (board[h.getX() + 1][h.getY()].getLetra() == 'D') {
				if (h.isHaveSword()) {
					System.out
					.println("You killed the dragon! You are the dragon born.");
					delete(h.getX() + 1, h.getY());
					dragon.setAlive(false);
					return 0;
				}
			}

			if (board[h.getX() - 1][h.getY()].getLetra() == 'D') {
				if (h.isHaveSword()) {
					System.out
					.println("You killed the dragon! You are the dragon born.");
					delete(h.getX() - 1, h.getY());
					dragon.setAlive(false);
					return 0;
				}
			}

			if (!dragon.isAdormecido()) {
				if (board[h.getX() - 1][h.getY()].getLetra() == 'D'
						|| board[h.getX() + 1][h.getY()].getLetra() == 'D'
						|| board[h.getX()][h.getY() - 1].getLetra() == 'D'
						|| board[h.getX()][h.getY() + 1].getLetra() == 'D') {

					System.out
					.println("You were killed by the Dragon and you lost the game.. Noob");
					return 1;
				}
			}
		}
		return 2;
	}

	public void moveDragon(Dragon dragon, int dir) {
		if(dragon.isAlive()){
	
		
		int posx = dragon.getX();
		int posy = dragon.getY();
		delete(posx, posy);

		switch (dir) {
		case 0:// esquerda
			if (board[dragon.getX()][dragon.getY() - 1].getLetra() != 'X') {
				if (board[dragon.getX()][dragon.getY() - 1].getLetra() == 'E') {
					//dragon.setLetra('F');//TODO
					dragon.setAboveSword(true);
					dragon.setY(dragon.getY() - 1);
				} else

					if (board[dragon.getX()][dragon.getY() - 1].getLetra() == ' ') {
						if (dragon.isAboveSword()) {
							board[s.getInicialx()][s.getInicialy()].setLetra('E');
							dragon.setAboveSword(false);
							

						}
						dragon.setY(dragon.getY() - 1);

					}
			}
			break;
		case 1: // direita
			if (board[dragon.getX()][dragon.getY() + 1].getLetra() != 'X') {
				if (board[dragon.getX()][dragon.getY() + 1].getLetra() == 'E') {
					//dragon.setLetra('F');
					dragon.setAboveSword(true);
					dragon.setY(dragon.getY() + 1);
				} else

					if (board[dragon.getX()][dragon.getY() + 1].getLetra() == ' ') {
						if (dragon.isAboveSword()) {
							board[dragon.getX()][dragon.getY()].setLetra('E');
							dragon.setAboveSword(false);
							
						}
						dragon.setY(dragon.getY() + 1);

					}
			}
			break;
		case 2: // cima
			if (board[dragon.getX() - 1][dragon.getY()].getLetra() != 'X') {
				if (board[dragon.getX() - 1][dragon.getY()].getLetra() == 'E') {
					//dragon.setLetra('F');
					dragon.setAboveSword(true);
					dragon.setX(dragon.getX() - 1);
				} else 
					if (board[dragon.getX() - 1][dragon.getY()].getLetra() == ' ') {
						if (dragon.isAboveSword()) {
							board[dragon.getX()][dragon.getY()].setLetra('E');
							dragon.setAboveSword(false);
							

						}
						dragon.setX(dragon.getX() - 1);

					}
			}
			break;
		case 3:// baixo
			if (board[dragon.getX() + 1][dragon.getY()].getLetra() == 'E') {
				//dragon.setLetra('F');
				dragon.setAboveSword(true);
				dragon.setX(dragon.getX() + 1);
			} else if (board[dragon.getX() + 1][dragon.getY()].getLetra() == ' ') {
				if (dragon.isAboveSword()) {
					board[dragon.getX()][dragon.getY()].setLetra('E');
					dragon.setAboveSword(false);
					
				}

				dragon.setX(dragon.getX() + 1);

			}
			break;

		case 4:
			dragon.setAdormecido(true);
			break;
		default:
			break;
		}

		

		if(dragon.isAlive())
			insertDragon(dragon);

		}
	}
	
	
	
	public boolean checkFinish() {
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if (!board[i][j].isVisited())
					return false;
			}
		}
		return true;
	}

	public void find(int x, int y) throws Exception {
		int nr = 0;
		board[x][y].setLetra(' ');
		System.out.println();
		if (checkFinish()) {
			return;
		}


		nr = selHipoteses(3);


		switch (nr) {
		case 0:
			cima(x, y);
			esquerda(x, y);
			baixo(x, y);
			direita(x, y);
			break;

		case 1:
			esquerda(x, y);
			baixo(x, y);
			direita(x, y);
			cima(x, y);
			break;
		case 2:
			baixo(x, y);
			direita(x, y);
			cima(x, y);
			esquerda(x, y);
			break;
		case 3:
			direita(x, y);
			cima(x, y);
			esquerda(x, y);
			baixo(x, y);
		default:
			break;
		}

		return;
	}

	public void direita(int x, int y) throws Exception {
		if (!visited(x, y + 2)) { // direita
			board[x][y + 1].setVisited(true);
			board[x][y + 1].setLetra(' ');
			board[x][y + 2].setVisited(true);
			find(x, y + 2);
		}
	}

	public void baixo(int x, int y) throws Exception {
		if (!visited(x + 2, y)) { // baixo
			board[x + 1][y].setVisited(true);
			board[x + 1][y].setLetra(' ');
			board[x + 2][y].setVisited(true);
			find(x + 2, y);
		}

		if (checkFinish()) {
			return;
		}
	}

	public void esquerda(int x, int y) throws Exception {
		if (!visited(x, y - 2)) { // esquerda

			board[x][y - 1].setVisited(true);
			board[x][y - 1].setLetra(' ');
			board[x][y - 2].setVisited(true);
			find(x, y - 2);
		}
		if (checkFinish()) {
			return;
		}
	}

	public void cima(int x, int y) throws Exception {
		if (!visited(x - 2, y)) { // cima
			board[x - 1][y].setVisited(true);
			board[x - 1][y].setLetra(' ');
			board[x - 2][y].setVisited(true);
			find(x - 2, y);
		}

		if (checkFinish()) {
			return;
		}
	}

	public boolean visited(int x, int y) throws Exception {
		if (x < 0 || x >= tamanho  || y < 0 || y >= tamanho )

			return true;
		return board[x][y].isVisited();

	}
/*public void gamePlay(int posi, int posj, Eagle e, Dragon dragon, Hero h, Sword s)
			throws Exception {

		delete(posi, posj);

		checkDragon(d, e);

		if (e.hasSword && e.alive)
			eagleReturn(e, s);

		if (e.alive)
			insertEagle(e.x, e.y, e);

		insertHero(h.x, h.y, h);

		moveAllDragons();

		print_maze();
	}*/




	public int selHipoteses(int nr) {
		Random rand = new Random();
		return rand.nextInt(nr);
	}

	public int getX(Casa act) {
		for (int i = 0; i < tamanho; i++) {
			for (int k = 0; k < tamanho; k++) {
				if (board[i][k] == act) {
					return i;
				}
			}
		}
		return -1;
	}

	public int getY(Casa act) {
		for (int i = 0; i < tamanho; i++) {
			for (int k = 0; k < tamanho; k++) {
				if (board[i][k] == act) {
					return k;
				}
			}
		}
		return -1;
	}

	public void termina() throws IOException {
		int nr = tamanho;
		Casa[][] casa = new Casa[3][3];
		for (int i = 2; i < tamanho - 2; i++) {

			for (int j = 2; j < tamanho - 2; j++) {

				if (nr <= 0)
					return;
				board[i][j].setLetra(' ');
				casa = cluster3(board[i][j]);
				if (!checkCluster(casa)) {
					board[i][j].setLetra('X');
					nr++;
				}
				nr--;
			}
		}
	}

	public Casa[][] cluster3(Casa ori) {
		Casa[][] clus = new Casa[3][3];
		clus[1][1] = ori;
		clus[0][0] = board[getX(ori) - 1][getY(ori) - 1];
		clus[0][1] = board[getX(ori)][getY(ori) - 1];
		clus[0][2] = board[getX(ori) + 1][getY(ori) - 1];
		clus[1][0] = board[getX(ori) - 1][getY(ori)];
		clus[1][1] = board[getX(ori)][getY(ori)];
		clus[1][2] = board[getX(ori) + 1][getY(ori)];
		clus[2][0] = board[getX(ori) - 1][getY(ori) + 1];
		clus[2][1] = board[getX(ori)][getY(ori) + 1];
		clus[2][2] = board[getX(ori) + 1][getY(ori) + 1];
		return clus;
	}

	public boolean checkCluster(Casa[][] clus) {

		if (clus[0][0].getLetra() == ' ' && clus[1][0].getLetra() == ' '
				&& clus[0][1].getLetra() == ' ' && clus[1][1].getLetra() == ' ') {
			return false;
		} else if (clus[1][0].getLetra() == ' ' && clus[2][0].getLetra() == ' '
				&& clus[1][1].getLetra() == ' ' && clus[2][1].getLetra() == ' ') {
			return false;
		} else if (clus[0][1].getLetra() == ' ' && clus[1][1].getLetra() == ' '
				&& clus[0][2].getLetra() == ' ' && clus[1][2].getLetra() == ' ') {
			return false;
		} else if (clus[1][1].getLetra() == ' ' && clus[2][1].getLetra() == ' '
				&& clus[1][2].getLetra() == ' ' && clus[2][2].getLetra() == ' ') {
			return false;
		}

		return true;
	}

	public void jogo() throws Exception {

		moveHero(h, d, s, g);
		//System.out.println("X= " + h.getX() + " Y= " + h.getY());
		//print_maze();

	}

	@Override
	public void buildMaze() {
		buildMaze();

	}

	public void insertSword() {

		int nrx, nry;
		do {
			nrx = selHipoteses(tamanho - 1) + 1;
			nry = selHipoteses(tamanho - 1) + 1;
		} while (board[nrx][nry].getLetra() != ' ');
		s = new Sword(nrx, nry);

		board[nrx][nry] = s;

	}

	public void insertHero() {
		int nrx, nry;
		do {
			nrx = selHipoteses(tamanho - 1) + 1;
			nry = selHipoteses(tamanho - 1) + 1;
		} while (board[nrx][nry].getLetra() != ' ');

		g = new Eagle(nrx, nry);
		h = new Hero(nrx, nry);

		board[nrx][nry] = h;
	}

	public void insertDragon() {
		int nrx, nry;

		for (int i = 0; i < d.length; i++) {

			do {
				nrx = selHipoteses(tamanho - 1) + 1;
				nry = selHipoteses(tamanho - 1) + 1;
			} while (board[nrx][nry].getLetra() != ' ');


			d[i] = new Dragon(nrx, nry);
			board[nrx][nry]=d[i];
		}
}





	public int[] findExit() {
		int[] b = new int[2];
		int tX, tY;
		Random pX = new Random();
		tX = pX.nextInt(4);
		do {
			tY = pX.nextInt(tamanho - 2) + 1;
		} while (tY % 2 == 0);
		// System.out.println(tX);
		System.out.println(tY);
		switch (tX) {
		case 0: // em cima
			board[0][tY].setLetra('S');
			board[1][tY].setLetra(' ');
			board[1][tY].setVisited(true);
			b[0] = 1;
			b[1] = tY;
			break;
		case 1: // direita
			board[tY][tamanho - 1].setLetra('S');
			board[tY][tamanho - 2].setLetra(' ');
			board[tY][tamanho - 2].setVisited(true);
			b[0] = tY;
			b[1] = tamanho - 2;
			break;
		case 2: // baixo
			board[tamanho - 1][tY].setLetra('S');
			board[tamanho - 2][tY].setLetra(' ');
			board[tamanho - 2][tY].setVisited(true);
			b[0] = tamanho - 2;
			b[1] = tY;
			break;
		case 3: // esquerda
			board[tY][0].setLetra('S');
			board[tY][1].setLetra(' ');
			board[tY][1].setVisited(true);
			b[0] = tY;
			b[1] = 1;
			break;
		default:
			break;
		}
		return b;
	}

	// Parte array dragoes

	public boolean checkalldead() {
		for (int i = 0; i < d.length; i++) {
			if (d[i].isAlive())
				return false;
		}

		return true;
	}

	public void killDragon(Dragon dragon) {
		dragon.setAlive(false);
		board[dragon.getX()][dragon.getY()].setLetra(' ');
	}

	public void moveAllDragons() {
		int dire = 0;
		
		for (int i = 0; i < d.length; i++) {
			if (d[i].isAlive()) {
				dire = randomMove();
				moveDragon(d[i], dire);
			}
		}

	}

}
