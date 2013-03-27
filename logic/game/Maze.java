package logic.game;

import java.io.IOException;

import java.util.Random;
import java.util.Vector;

import ui.game.Interface;

import logic.game.Casa;
import logic.game.Dragon;
import logic.game.Hero;
import logic.game.Sword;

public class Maze {


	private int[] b = new int[2];
	
	public static int tamanho;
	public static int dificuldade;
	private Hero h = new Hero(1, 1);
	private Dragon d = new Dragon(3, 1);
	private Sword s = new Sword(8, 1);
	private Exit exit = new Exit();
	public static Casa[][] board; 

	public static void print_maze() {
		for (int i = 0; i < tamanho; i++) {
			System.out.println();
			for (int k = 0; k < tamanho; k++) {
				System.out.print(" ");
				System.out.print(board[i][k].getLetra());

			}
		}
	}

	public void updateBoard() {
		moveHero(board, h, d);
	}

	public Maze() throws Exception {
		board=new Casa[tamanho][tamanho];
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
				if ((i == 0 || i == tamanho - 1) || (j == 0 || j == tamanho - 1)) {
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
		insertDragon();
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
		this.board = board;
	}

	public static void insertHero(Casa[][] maze, int i, int j, Hero h) {
		board[i][j].setLetra(h.getLetra());
	}

	public static void insertDragon(Casa[][] maze, Dragon d) {
		board[d.x][d.y].setLetra(d.getLetra());
	}

	public static void insertSword(Casa[][] maze, int i, int j) {
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

	public static void moveHero(Casa[][] board, Hero h, Dragon d) {
		char dir = 0;
		int end, nr;
		boolean b = true;
		while (b) {
			int posi = h.x;
			int posj = h.y;

			end = checkKill(board, h);
			if (end == 1)
				return;

			// TODO ver Keylistener
			dir = Interface.uiMove(dir);

			switch (dir) {
			case 'd':
				if (board[h.x][h.y + 1].isVisited()) {

					if (board[h.x][h.y + 1].getLetra() == 'S') {
						if (h.haveSword = true) {
							System.out.println("You won the game! GG WP");
							b = false;
						}

					} else {
						if (board[h.x][h.y + 1].getLetra() == 'E') {
							board[h.x][h.y + 1].setLetra('A');
							h.haveSword = true;
						}

					}
					h.y++;
				}

				break;
			case 'a':
				if (board[h.x][h.y - 1].getLetra() != 'X') {
					if (board[h.x][h.y - 1].getLetra() == 'E') {
						h.haveSword = true;
						board[h.x][h.y - 1].setLetra('A');
					}
					h.y--;
				}

				break;
			case 'w':
				print_maze();

				if (board[h.x - 1][h.y].getLetra() != 'X') {
					if (board[h.x - 1][h.y].getLetra() == 'E') {
						h.haveSword = true;
						board[h.x - 1][h.y].setLetra('A');
					}
					h.x--;
				}

				break;
			case 's':
				if (board[h.x + 1][h.y].getLetra() != 'X') {
					if (board[h.x + 1][h.y].getLetra() == 'E') {
						h.haveSword = true;
						board[h.x + 1][h.y].setLetra('A');
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
			delete(board, posi, posj);
			insertHero(board, h.x, h.y, h);
			nr = randomMove();
			System.out.println(nr);
			moveDragon(board, d, nr);
			print_maze();
		}
	}

	public static void delete(Casa[][] board, int i, int j) {
		board[i][j].setLetra(' ');
	}

	public static int randomMove() {
		Random nr = new Random();
		return nr.nextInt(4);
	}

	public static int checkKill(Casa[][] board, Hero h) {

		if (board[h.x][h.y + 1].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
						.println("You killed the dragon! You are the dragon born.");
				delete(board, h.x, h.y + 1);
				return 0;
			}
		}

		if (board[h.x][h.y - 1].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
						.println("You killed the dragon! You are the dragon born.");
				delete(board, h.x, h.y - 1);
				return 0;
			}
		}

		if (board[h.x + 1][h.y].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
						.println("You killed the dragon! You are the dragon born.");
				delete(board, h.x + 1, h.y);
				return 0;
			}
		}

		if (board[h.x - 1][h.y].getLetra() == 'D') {
			if (h.haveSword) {
				System.out
						.println("You killed the dragon! You are the dragon born.");
				delete(board, h.x - 1, h.y);
				return 0;
			}
		}

		if (board[h.x - 1][h.y].getLetra() == 'D'
				|| board[h.x + 1][h.y].getLetra() == 'D'
				|| board[h.x][h.y - 1].getLetra() == 'D'
				|| board[h.x][h.y + 1].getLetra() == 'D') {

			System.out
					.println("You were killed by the Dragon and you lost the game.. Noob");
			return 1;

		}
		return 2;
	}

	public static void moveDragon(Casa[][] board, Dragon d, int dir) {
		int posx, posy;
		posx = d.x;
		posy = d.y;
		switch (dir) {
		case 0:// esquerda

			if (board[d.x][d.y - 1].getLetra() == 'E') {
				d.setLetra('F');
				d.y--;
			} else

			if (board[d.x][d.y - 1].getLetra() == ' ') {
				if (d.getLetra() == 'F') {
					board[d.x][d.y].setLetra('E');
					d.setLetra('D');
				}
				d.y--;

			}
			break;
		case 1: // direita
			if (board[d.x][d.y + 1].getLetra() == 'E') {
				d.setLetra('F');
				d.y++;
			} else

			if (board[d.x][d.y + 1].getLetra() == ' ') {
				if (d.getLetra() == 'F') {
					board[d.x][d.y].setLetra('E');
					d.setLetra('D');
				}
				d.y++;

			}

			break;
		case 2: // cima
			if (board[d.x - 1][d.y].getLetra() == 'E') {
				d.setLetra('F');
				d.x--;
			} else if (board[d.x - 1][d.y].getLetra() == ' ') {
				if (d.getLetra() == 'F') {
					board[d.x][d.y].setLetra('E');
					d.setLetra('D');
				}
				d.x--;

			}
			break;
		case 3:// baixo
			if (board[d.x + 1][d.y].getLetra() == 'E') {
				d.setLetra('F');
				d.x++;
			} else if (board[d.x + 1][d.y].getLetra() == ' ') {
				if (d.getLetra() == 'F') {
					board[d.x][d.y].setLetra('E');
					d.setLetra('D');
				}

				d.x++;

			}
			break;

		default:
			break;
		}
		delete(board, posx, posy);
		insertDragon(board, d);

	}

	public void find(int x, int y) throws Exception {
		int nr = 0;
		board[x][y].setLetra(' ');
		System.out.println();
		if (checkFinish()) {
			return;
		}
		// System.out.println("estou na posicao x= " + x + " y= " + y);

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

	public void jogo() {
		boolean end = false;

		while (!end) {
			updateBoard();
			System.out.println("X= " + h.getX() + " Y= " + h.getY());
			print_maze();

			moveHero(board, h, d);

		}
	}

	// @Override
	// public void buildMaze() {
	// buildMaze();

	// }

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
		h = new Hero(nrx, nry);

		board[nrx][nry] = h;

	}

	public void insertDragon() {
		int nrx, nry;
		do {
			nrx = selHipoteses(tamanho - 1) + 1;
			nry = selHipoteses(tamanho - 1) + 1;
		} while (board[nrx][nry].getLetra() != ' ');
		d = new Dragon(nrx, nry);

		board[nrx][nry] = d;

	}

	public int[] findExit() {
		int[] b = new int[2];
		int tX, tY;
		Random pX = new Random();
		tX = pX.nextInt(4);
		do {
			tY = pX.nextInt(tamanho - 2) + 1;
		} while (tY % 2 == 0);
		System.out.println(tX);
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

}