package logic;

import java.io.IOException;

import java.security.AllPermission;
import java.util.Random;
import java.util.Vector;

import ui.Interface;

import logic.Casa;
import logic.Dragon;
import logic.Hero;
import logic.Sword;
import logic.Eagle;

public class Maze implements Builder {

	private int[] b = new int[2];

	public static int tamanho=5;
	public static int dificuldade=1;
	public static int numDragoes=1;

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

	public void updateBoard() throws Exception {
		moveHero(h, d, s, g);
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
		//insertDragon();

	}

	/**
	 * Vector
	 * 
	 * @return the board
	 */
	public static Casa[][] getBoard() {
		return board;
	}

	/**
	 * @param board
	 *            the board to set
	 */
	public void setBoard(Casa[][] board) {
		this.board = board;
	}

	public static void insertHero(int i, int j, Hero h) {
		if(!h.haveSword)
		board[i][j].setLetra('H');
		else
			board[i][j].setLetra('A');
	}

	public static void insertEagle(int i, int j, Eagle g) {
		board[i][j].setLetra(g.getLetra());
	}

	public static void insertDragon(Dragon dragon) {
		board[dragon.x][dragon.y].setLetra('D');
	}

	public static void insertSword(int i, int j) {
		board[i][j].setLetra('E');
	}

	public static void findExit(Casa[][] maze) {
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

		char dir = 0;
		int end = 0;

		int posi = h.getX();
		int posj = h.getY();
		boolean b = true;

		while (b) {

			posi = h.getX();
			posj = h.getY();

			delete(posi, posj);
			// TODO ver Keylistener
			dir = Interface.uiMove(dir);

			b = move(h, s, e, dir, posi, posj, b);

			if (!checkalldead()) {

				for (int i = 0; i < d.length; i++) {
					end = checkKill(h, d[i]);
					
					if (end == 1){
						b=false;
						return;
					}

				}
			}


		

	}

}

	public static  boolean move(Hero h, Sword s, Eagle e, char dir, int posi,
			int posj, boolean b) {
		switch (dir) {
		case 'd':

			if (board[h.x][h.y + 1].getLetra() != 'X') {
				if (board[h.x][h.y + 1].getLetra() == 'S') {

					if (h.haveSword && checkalldead()) {
						System.out.println("You won the game! GG WP");
						b = false;
						return false;
					}
					break;

				} else {
					if (board[h.x][h.y + 1].getLetra() == 'E'
							|| (board[h.x][h.y + 1].getLetra() == 'G'
							&& e.hasSword && e.alive)) {
						// h.setLetra();
						h.haveSword = true;
						e.hasSword = false;
						//h.setLetra('A');

					}
				}

				h.y++;

				// if (!h.haveSword){
				if ((!e.active) && e.alive) {
					e.y++;
				}
				if ((e.active) && (!e.hasSword)) {
					moveEagle(e, board, s);
					// }
				}

				gamePlay2(posi, posj);
				// gamePlay(posi, posj, e, d, h, s);

			}

			break;
		case 'a':
			if (board[h.x][h.y - 1].getLetra() != 'X') {
				if (board[h.x][h.y - 1].getLetra() == 'S') {
					if (h.haveSword && checkalldead()) {
						System.out.println("You won the game! GG WP");
						b = false;

					}
					break;

				} else {
					if (board[h.x][h.y - 1].getLetra() == 'E'
							|| (board[h.x][h.y - 1].getLetra() == 'G'
							&& e.hasSword && e.alive)) {
						h.haveSword = true;
						// h.setLetra();
						e.hasSword = false;
						//h.setLetra('A');

					}
				}

				h.y--;

				// if (!h.haveSword){
				if ((!e.active) && e.alive) {
					e.y--;
				}
				if ((e.active) && (!e.hasSword)) {
					moveEagle(e, board, s);
					// }
				}
				gamePlay2(posi, posj);
				// gamePlay(posi, posj, e, d, h, s);
			}

			break;
		case 'w':
			if (board[h.x - 1][h.y].getLetra() != 'X') {
				if (board[h.x - 1][h.y].getLetra() == 'S') {
					if (h.haveSword && checkalldead()) {
						System.out.println("You won the game! GG WP");
						b = false;

					}
					break;

				} else {
					if (board[h.x - 1][h.y].getLetra() == 'E'
							|| (board[h.x - 1][h.y].getLetra() == 'G'
							&& e.hasSword && e.alive)) {
						h.haveSword = true;
						// h.setLetra();
						//h.setLetra('A');
						e.hasSword = false;

					}
				}

				h.x--;

				// if (!h.haveSword){
				if ((!e.active) && e.alive) {
					e.x--;

				}
				if ((e.active) && (!e.hasSword)) {
					moveEagle(e, board, s);
					// }
				}
				gamePlay2(posi, posj);
				// gamePlay(posi, posj, e, d, h, s);
			}

			break;
		case 's':
			if (board[h.x + 1][h.y].getLetra() != 'X') {

				if (board[h.x + 1][h.y].getLetra() == 'S') {
					if (h.haveSword && checkalldead()) {
						System.out.println("You won the game! GG WP");
						b = false;

					}
					break;

				} else {
					if (board[h.x + 1][h.y].getLetra() == 'E'
							|| (board[h.x + 1][h.y].getLetra() == 'G'
							&& e.hasSword && e.alive)) {
						e.hasSword = false;
						h.haveSword = true;
						//h.setLetra('A');

					}
				}
				h.x++;

				// if (!h.haveSword){
				if ((!e.active) && e.alive) {
					e.x++;
				}

				if ((e.active) && (!e.hasSword)) {
					moveEagle(e, board, s);
					// }
				}
				gamePlay2(posi, posj);
				// gamePlay(posi, posj, e, d, h, s);
			}

			break;

		case 'e':
			e.active = true;
			e.initX = e.x;
			e.initY = e.y;
			moveEagle(e, board, s);
			gamePlay2(posi, posj);
			// gamePlay(posi, posj, e, d, h, s);
			break;

		case 'p':
			b = false;
			break;
		default:

			break;
		}
		return b;
	}

public static boolean checkDragon(Dragon dragon, Eagle e) throws Exception {
	if (e.isDown && dragon.alive && e.alive) {
		if (board[e.x - 1][e.y].getLetra() == 'D'
				|| board[e.x + 1][e.y].getLetra() == 'D'
				|| board[e.x][e.y - 1].getLetra() == 'D'
				|| board[e.x][e.y + 1].getLetra() == 'D') {
			e.alive = false;
			e.active = false;
			delete(e.x, e.y);
			board[e.x][e.y].setLetra('S');

			System.out.println("Your eagle was killed by the Dragon...");
			return true;
		}
	}
	return false;
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

public static void gamePlay2(int posi, int posj) {
	delete(posi, posj);

	if (g.hasSword && g.alive)
		eagleReturn(g, s);

	if (g.alive)
		insertEagle(g.x, g.y, g);

	insertHero(h.getX(), h.getY(), h);

	moveAllDragons();

	print_maze();
}

public static void eagleReturn(Eagle e, Sword s) {

	if (e.x > e.initX && board[e.x - 1][e.y].getLetra() != 'X') {
		delete(e.x, e.y);
		e.x--;
		s.x--;
		return;
	}

	if (e.x < e.initX && board[e.x + 1][e.y].getLetra() != 'X') {
		delete(e.x, e.y);
		e.x++;
		s.x++;
		return;
	}

	if (e.y > e.initY && board[e.x][e.y - 1].getLetra() != 'X') {
		delete(e.x, e.y);
		e.y--;
		s.y--;
		return;
	}

	if (e.y < e.initY && board[e.x][e.y + 1].getLetra() != 'X') {
		delete(e.x, e.y);
		e.y++;
		s.y++;
		return;
	}

	if (e.x == e.initX && e.y == e.initY) {
		e.isDown = true;
		e.active = false;
	}

}

public static void moveEagle(Eagle e, Casa[][] board, Sword s) {
	if (e.x > s.x) {

		if (board[e.x - 1][e.y].getLetra() == 'E') {
			e.hasSword = true;
		}

		delete(e.x, e.y);
		e.x--;
		return;
	}

	if (e.x < s.x) {
		if (board[e.x + 1][e.y].getLetra() == 'E') {
			e.hasSword = true;
		}

		delete(e.x, e.y);
		e.x++;

		return;
	}

	if (e.y < s.y) {

		if (board[e.x][e.y + 1].getLetra() == 'E') {
			e.hasSword = true;
		}
		delete(e.x, e.y);
		e.y++;

		return;
	}

	if (e.y > s.y) {
		if (board[e.x][e.y - 1].getLetra() == 'E') {
			e.hasSword = true;
		}
		delete(e.x, e.y);
		e.y--;

		return;
	}
}

public static void delete(int i, int j) {
	board[i][j].setLetra(' ');
}

public static int randomMove() {
	Random nr = new Random();
	return nr.nextInt(4);
}

public int checkKill(Hero h, Dragon dragon) {
	if (dragon.alive) {

		if (board[h.x][h.y + 1].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
				.println("You killed the dragon! You are the dragon born.");
				delete(h.x, h.y + 1);
				dragon.alive = false;
				return 0;
			}
		}

		if (board[h.x][h.y - 1].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
				.println("You killed the dragon! You are the dragon born.");
				delete(h.x, h.y - 1);
				dragon.alive = false;
				return 0;
			}
		}

		if (board[h.x + 1][h.y].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
				.println("You killed the dragon! You are the dragon born.");
				delete(h.x + 1, h.y);
				dragon.alive = false;
				return 0;
			}
		}

		if (board[h.x - 1][h.y].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
				.println("You killed the dragon! You are the dragon born.");
				delete(h.x - 1, h.y);
				dragon.alive = false;
				return 0;
			}
		}

		if (!dragon.isAdormecido()) {
			if (board[h.x - 1][h.y].getLetra() == 'D'
					|| board[h.x + 1][h.y].getLetra() == 'D'
					|| board[h.x][h.y - 1].getLetra() == 'D'
					|| board[h.x][h.y + 1].getLetra() == 'D') {

				System.out
				.println("You were killed by the Dragon and you lost the game.. Noob");
				return 1;
			}
		}
	}
	return 2;
}

public static void moveDragon(Dragon dragon, int dir) {
	int posx, posy;
	posx = dragon.x;
	posy = dragon.y;

	switch (dir) {
	case 0:// esquerda
		if (board[dragon.x][dragon.y - 1].getLetra() != 'X') {
			if (board[dragon.x][dragon.y - 1].getLetra() == 'E') {
				dragon.setLetra('F');//TODO
				dragon.y--;
			} else

				if (board[dragon.x][dragon.y - 1].getLetra() == ' ') {
					if (dragon.getLetra() == 'F') {
						board[dragon.x][dragon.y].setLetra('E');
						dragon.setLetra('D');
					}
					dragon.y--;

				}
		}
		break;
	case 1: // direita
		if (board[dragon.x][dragon.y + 1].getLetra() != 'X') {
			if (board[dragon.x][dragon.y + 1].getLetra() == 'E') {
				dragon.setLetra('F');
				dragon.y++;
			} else

				if (board[dragon.x][dragon.y + 1].getLetra() == ' ') {
					if (dragon.getLetra() == 'F') {
						board[dragon.x][dragon.y].setLetra('E');
						dragon.setLetra('D');
					}
					dragon.y++;

				}
		}
		break;
	case 2: // cima
		if (board[dragon.x - 1][dragon.y].getLetra() != 'X') {
			if (board[dragon.x - 1][dragon.y].getLetra() == 'E') {
				dragon.setLetra('F');
				dragon.x--;
			} else if (board[dragon.x - 1][dragon.y].getLetra() == ' ') {
				if (dragon.getLetra() == 'F') {
					board[dragon.x][dragon.y].setLetra('E');
					dragon.setLetra('D');
				}
				dragon.x--;

			}
		}
		break;
	case 3:// baixo
		if (board[dragon.x + 1][dragon.y].getLetra() == 'E') {
			dragon.setLetra('F');
			dragon.x++;
		} else if (board[dragon.x + 1][dragon.y].getLetra() == ' ') {
			if (dragon.getLetra() == 'F') {
				board[dragon.x][dragon.y].setLetra('E');
				dragon.setLetra('D');
			}

			dragon.x++;

		}
		break;

	case 4:
		dragon.setAdormecido(true);
		break;
	default:
		break;
	}
	delete(posx, posy);
	if(dragon.isAlive())
	insertDragon(dragon);

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
	if (x < 0 || x >= tamanho - 0 || y < 0 || y >= tamanho - 0)

		return true;

	return board[x][y].isVisited();
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

	updateBoard();
	//System.out.println("X= " + h.getX() + " Y= " + h.getY());
	print_maze();

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

		board[nrx][nry] = d[i];
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

public static boolean checkalldead() {
	for (int i = 0; i < d.length; i++) {
		if (d[i].isAlive())
			return false;
	}

	return true;
}

public void killDragon(Dragon dragon) {
	dragon.setAlive(false);
	board[dragon.x][dragon.y].setLetra(' ');
}

public static void moveAllDragons() {
	int dire = 0;
	for (int i = 0; i < d.length; i++) {
		if (d[i].isAlive()) {
			dire = randomMove();
			moveDragon(d[i], dire);
		}
	}

}

}