package maze;

import ui.Interface;
import logic.Maze;

public class Iter1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Interface ini= new Interface();
		
		Maze lab;
		ini.menu();
		
		try {
			lab = new Maze();
			Maze.print_maze();
			lab.jogo();
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
}
