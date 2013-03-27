package ui.game;

import java.io.IOException;
import java.util.Scanner;

import logic.game.Maze;

public class Interface {
	public void print(String str){
		System.out.print(str);
	}
	public void println(String str){
		System.out.println(str);
	}
	
	public void clear(){
		for(int i=0; i < 10; i++){
			System.out.println();
		}
	}
	
	
	public int scan(){
		Scanner s= new Scanner(System.in);
		int nr= s.nextInt();
		return nr;
	}
	
	
	
	
	public void menu(){
		int options[]= new int[2];
		System.out.println("welcome to Maze \n select your options");
		options(options);
		Maze.tamanho= options[0];
		Maze.dificuldade= options[1];
		
	}
	
	
	public void options(int options[]){
		do {
			println("Select size of the maze (has to be odd number)");
		options[0]= scan();
		}while(options[0]%2==0);
		
		clear();
		println("Select dificulty \n" +
				"\n EASY------- Dragon does not move                         1" +
				"\n MEDIUM----- Dragon goes to sleep                         2" +
				"\n HARD------- Dragon moves randomly                        3");
		options[1]= scan();
		
		println("tamanho= "+ options[0]);
		println("DIFICULDADE= "+ options[1]);
	}
	
	public static char uiMove(char dir) {
        try {
                dir = (char) System.in.read();
        } catch (IOException e) {
                System.out.println("Error!");
                e.printStackTrace();
        }
        return dir;
}
	
	
}
