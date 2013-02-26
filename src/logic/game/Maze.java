package logic.game;

import java.io.IOException;
import java.util.Random;

import ui.game.UserInterface;

 

public class Maze { 
	private static UserInterface ui= new UserInterface();
	private static int width;
	public static Celula[][] board;
	private Hero hero = new Hero(1,1);
	private Dragon dragon = new Dragon(3,1);
	private Sword sword = new Sword(8,1);
	private Exit exit = new Exit();
	public void updateBoard(){
		moveHero(board, hero,dragon);
	}
	 public Maze (int width){
		 this.width= width;
		int[] options= new int[2];
		options= ui.menu();
		 	board= new Celula[width][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j]=  new Celula(i,j);
				if ((i == 0 || i == width - 1)
						|| (j == 0 || j == width - 1)) {
					board[i][j].visited= true;
					board[i][j].setSymbol('X');
				}
			}
		}
		findExit(board);
		insertHero(board,hero.x,hero.y, hero);
		insertDragon(board,dragon);
		insertSword(board, sword.x,sword.y);
		ui.printMaze(board);
	 }
	/**
	 * @return the board
	 */
	public Celula[][] getBoard() {
		return board;
	}  
	/**
	 * @param board the board to set
	 */
	public void setBoard(Celula[][] board) {
		this.board = board;
	}
	
	public static void insertHero(Celula[][] maze, int i, int j, Hero h) {
		board[i][j].setSymbol(h.symbol);
	}

	

	public static void insertDragon(Celula[][] maze, Dragon d) {
		board[d.x][d.y].setSymbol(d.symbol);
	}
	
	 public static void insertSword(Celula[][] maze, int i, int j) {
			board[i][j].setSymbol('E');
		}
	
	 

		public static void findExit(Celula[][] maze) {
			int tX, tY;
			Exit exit = new Exit();
			Random pX = new Random();
			tX = pX.nextInt(4);
			tY = pX.nextInt(width - 2) + 1;
			System.out.println(tX);
			System.out.println(tY);
			switch (tX) {
			case 0: // em cima
				board[0][tY]= exit;
				board[1][tY].setSymbol(' ');
				board[1][tY].visited=true;
				break;
			case 1: // direita
				board[tY][width - 1]=exit;
				board[tY][width-2].setSymbol(' ');
				board[tY][width-2].visited=true;
				break;
			case 2: // baixo
				board[width - 1][tY]=exit;
				board[width-2][tY].setSymbol(' ');
				board[width-2][tY].visited=true;
				break;
			case 3: // esquerda
				board[tY][0]=exit;
				board[tY][1].setSymbol(' ');
				board[tY][1].visited=true;
				break;
			default:
				break;
			}
		}
		
		
		
		
		public static void moveHero(Celula[][] board, Hero h, Dragon d) {
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
				dir = ui.uiMove(dir);

				switch (dir) {
				case 'd':
					if (board[h.x][h.y + 1].getSymbol() != 'X') {

						if (board[h.x][h.y + 1].getSymbol() == 'S') {
							if (h.haveSword = true) {
								System.out.println("You won the game! GG WP");
								b = false;
							}

						} else {
							if (board[h.x][h.y + 1].getSymbol() == 'E') {
								h.symbol = 'A';
								h.haveSword = true;
							}

						}
						h.y++;
					}

					break;
				case 'a':
					if (board[h.x][h.y - 1].getSymbol() != 'X') {
						if (board[h.x][h.y - 1].getSymbol() == 'E') {
							h.haveSword = true;
							h.symbol = 'A';
						}
						h.y--;
					}

					break;
				case 'w':
					if (board[h.x - 1][h.y].getSymbol() != 'X') {
						if (board[h.x - 1][h.y].getSymbol() == 'E') {
							h.haveSword = true;
							h.symbol = 'A';
						}
						h.x--;
					}

					break;
				case 's':
					if (board[h.x + 1][h.y].getSymbol() != 'X') {
						if (board[h.x + 1][h.y].getSymbol() == 'E') {
							h.haveSword = true;
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
				delete(board, posi, posj);
				insertHero(board, h.x, h.y, h);
				nr = randomMove();
				System.out.println(nr); 
				moveDragon(board, d, nr);
				ui.printMaze(board);
			}
		}
		
		
		
		public static void delete(Celula[][] board, int i, int j) {
			board[i][j].setSymbol(' ');
		}

		public static int randomMove() {
			Random nr = new Random();
			return nr.nextInt(4);
		}

		public static int checkKill(Celula[][] board, Hero h) {

			if (board[h.x][h.y + 1].getSymbol() == 'D') {
				if (h.haveSword) {
					System.out
							.println("You killed the dragon! You are the dragon born.");
					delete(board, h.x, h.y + 1);
					return 0;
				}
			}

			if (board[h.x][h.y - 1].getSymbol() == 'D') {
				if (h.haveSword) {
					System.out
							.println("You killed the dragon! You are the dragon born.");
					delete(board, h.x, h.y - 1);
					return 0;
				}
			}

			if (board[h.x + 1][h.y].getSymbol() == 'D') {
				if (h.haveSword) {
					System.out
							.println("You killed the dragon! You are the dragon born.");
					delete(board, h.x + 1, h.y);
					return 0;
				}
			}

			if (board[h.x - 1][h.y].getSymbol() == 'D') {
				if (h.haveSword) {
					System.out
							.println("You killed the dragon! You are the dragon born.");
					delete(board, h.x - 1, h.y);
					return 0;
				}
			}

			if (board[h.x - 1][h.y].getSymbol() == 'D'
					|| board[h.x + 1][h.y].getSymbol() == 'D'
					|| board[h.x][h.y - 1].getSymbol() == 'D'
					|| board[h.x][h.y + 1].getSymbol() == 'D') {

				System.out
						.println("You were killed by the Dragon and you lost the game.. Noob");
				return 1;

			}
			return 2;
		}

		public static void moveDragon(Celula[][] board, Dragon d, int dir) {
			int posx, posy;
			posx = d.x;
			posy = d.y;
			switch (dir) {
			case 0:// esquerda

				if (board[d.x][d.y - 1].getSymbol() == 'E') {
					d.setSymbol('F');
					d.y--;
				} else

				if (board[d.x][d.y - 1].getSymbol() == ' ') {
					if (d.getSymbol() == 'F') {
						board[d.x][d.y].setSymbol('E');
						d.setSymbol('D');
					}
					d.y--;

				}
				break;
			case 1: // direita
				if (board[d.x][d.y + 1].getSymbol() == 'E') {
					d.setSymbol('F');
					d.y++;
				} else

				if (board[d.x][d.y + 1].getSymbol() == ' ') {
					if (d.getSymbol() == 'F') {
						board[d.x][d.y].setSymbol('E');
						d.setSymbol('D');
					}
					d.y++;

				}

				break;
			case 2: // cima
				if (board[d.x - 1][d.y].getSymbol() == 'E') {
					d.setSymbol('F');
					d.x--;
				} else if (board[d.x - 1][d.y].getSymbol() == ' ') {
					if (d.getSymbol() == 'F') {
						board[d.x][d.y].setSymbol('E');
						d.setSymbol('D');
					}
					d.x--;

				}
				break;
			case 3:// baixo
				if (board[d.x + 1][d.y].getSymbol() == 'E') {
					d.setSymbol('F');
					d.x++;
				} else if (board[d.x + 1][d.y].getSymbol() == ' ') {
					if (d.getSymbol() == 'F') {
						board[d.x][d.y].setSymbol('E');
						d.setSymbol('D');
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

		
}
	

