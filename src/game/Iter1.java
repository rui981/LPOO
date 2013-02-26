package game;

import java.io.IOException;
import java.util.Random;

import logic.game.Celula;
import logic.game.Dragon;
import logic.game.Hero;
import logic.game.Maze;


public class Iter1 {
 
	public static int BOARDSIZE = 14;

	// private static final String Random = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Maze maze = new Maze(BOARDSIZE);
		maze.updateBoard();
		
		
		fillMaze(maze);
		//algorithm(maze);
		/*
		insertExit(maze, 6, 9);
		insertHero(maze, h.x, h.y, h);
		insertSword(maze, 8, 1);
		insertDragon(maze, d);*/
		
		//moveHero(maze, h, d);

	}

	

	

	
		
		public static void fillMaze(Maze maze) {
            for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {
                            if ((j > 1 && i > 1) && (j < 4 && i < 5)) {
                                    maze.getBoard()[i][j].setSymbol('X');
                            }
                            if ((j > 1 && i > 5) && (j < 4 && i < 9)) {
                                    maze.getBoard()[i][j].setSymbol('X');
                            }

                            if ((j == 5 && i > 1) && (j == 5 && i < 5)) {
                                    maze.getBoard()[i][j].setSymbol('X');
                            }
                            if ((j == 5 && i > 5) && (j == 5 && i < 8)) {
                                    maze.getBoard()[i][j].setSymbol('X');
                            }

                            if ((j == 7 && i > 1) && (j == 7 && i < 8)) {
                                    maze.getBoard()[i][j].setSymbol('X');
                            }
                    }
            }
    }
		/*int coord[] = new int[2]; //FILLMAZE
		saida(maze);
		coord = findExit(maze);*/

	

	public static void algorithm(Maze maze) {
		int num;
		Random ra = new Random();
		num = ra.nextInt(BOARDSIZE - 1) + 1;
		maze.getBoard()[num][num].visited = true;
		percorre(maze, maze.getBoard()[num][num]);
	}

	public static void percorre(Maze maze, Celula cel) {
		//printMaze(maze);

		if (right(cel, maze))
			percorre(maze, maze.getBoard()[cel.getX() + 1][cel.getY()]);
		if (up(cel, maze))
			percorre(maze, maze.getBoard()[cel.getX()][cel.getY() - 1]);
		if (left(cel, maze))
			percorre(maze, maze.getBoard()[cel.getX() - 1][cel.getY()]);
		if (down(cel, maze))
			percorre(maze, maze.getBoard()[cel.getX()][cel.getY() + 1]);
		else return;
	}

	public static int[] findExit(Maze maze) {
		int coord[] = new int[2];
		for (int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				if (maze.getBoard()[i][j].getSymbol() == 'S') {
					coord[0] = i;
					coord[1] = j;
				}
			}

		}
		return coord;
	}

	
	
	

	
	
	


	
	

	public static boolean right(Celula cel, Maze maze) {
		if (maze.getBoard()[cel.getX() + 1][cel.getY()].visited == false) {
			maze.getBoard()[cel.getX() + 1][cel.getY()].setSymbol('X');
			cel.visited = true;
			return true;
		}
		return false;

	}

	public static boolean left(Celula cel, Maze maze) {
		if (maze.getBoard()[cel.getX() - 1][cel.getY()].visited == false) {
			maze.getBoard()[cel.getX() - 1][cel.getY()].setSymbol('X');
			cel.visited = true;
			return true;
		}
		return false;
	}

	public static boolean up(Celula cel, Maze maze) {
		if (maze.getBoard()[cel.getX()][cel.getY() - 1].visited == false) {
			maze.getBoard()[cel.getX()][cel.getY() - 1].setSymbol('X');
			cel.visited = true;
			return true;
		}

		return false;

	}

	public static boolean down(Celula cel, Maze maze) {
		if (maze.getBoard()[cel.getX()][cel.getY() + 1].visited == false) {
			maze.getBoard()[cel.getX()][cel.getY() + 1].setSymbol('X');
			cel.visited = true;
			return true;
		}
		return false;
	}

}
