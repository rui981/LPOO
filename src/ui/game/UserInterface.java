package ui.game;

import java.io.IOException;
import java.util.Scanner;

import logic.game.Celula;
import logic.game.Maze;

public class UserInterface {
	public UserInterface(){};
	
	
	
	
	public static char uiMove(char dir) {
		try {
			dir = (char) System.in.read();
		} catch (IOException e) {
			System.out.println("Erro!!!1");
			e.printStackTrace();
		}
		return dir;
	}
	
	
	public static int[] menu(){
		
		int[] option= new int[2];
		System.out.println("MENU\n\n");
		System.out.println("Choose the size of the board \n\n");
		Scanner s= new Scanner(System.in);
		option[0]=s.nextInt();
		
		
		//option é size do tabuleiro
		
		System.out.println("What type of game do you want?");
		System.out.println("Always sleeping dragon (the easy one)-------1");
		System.out.println("Moving dragon (the hard one) ---------------2");
		System.out.println("Both of the above (intermediate)------------3");
		try {
			option[1] = System.in.read();
		} catch (IOException e) {
			System.out.println("Error!!!");
			e.printStackTrace();
		}
		
		//option2 é escolha de modo de jogo
		
		System.out.println("\n\n Let the game begin!");
		return option;
	}
	
	
	
	
	public void printMaze(Celula[][] board)
	{	int width= board.length;
		for (int i = 0; i < width; i++) {
			System.out.print(" ");
			for (int k = 0; k < width; k++)
				System.out.print("----");
			System.out.println();
			System.out.print("| ");
			for (int j = 0; j < width; j++) {

				System.out.print(board[i][j].getSymbol());
				System.out.print(" | ");

			}
			System.out.println();

		}
		for (int k = 0; k < width; k++)
			System.out.print("----");
	}
}
