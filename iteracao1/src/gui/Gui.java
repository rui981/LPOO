package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.Casa;
import logic.Maze;

public class Gui {
	public static void main(String args[]) throws Exception {
/*
		final JFrame init = new JFrame("INICIO");
		final JFrame opcoes = new JFrame("OPCOES");
		final JFrame jogo = new JFrame("JOGO");

		final Container cinit = init.getContentPane();
		Container cop = opcoes.getContentPane();
		final Container cjogo = jogo.getContentPane();

		cinit.setLayout(new GridLayout(3, 1));
		cop.setLayout(new GridLayout(4, 1));

		JButton jogar = new JButton("Jogar");
		JButton opc = new JButton("Opcoes");
		JButton sair = new JButton("Sair");
		cinit.add(jogar);
		cinit.add(opc);
		cinit.add(sair);
		init.setBounds(200, 200, 300, 300);
		opcoes.setBounds(200, 200, 300, 400);
		jogo.setBounds(200, 200, 1200, 1200);
		init.pack();

		// jogo.pack();
		init.setVisible(true);
		// //////////////////////////////////OPCOES////////////////////////////////////
		JButton saidaOp = new JButton("Guardar");
		SpinnerNumberModel sm = new SpinnerNumberModel(5, 5, 45, 2);
		final JSpinner size = new JSpinner(sm);
		size.setBorder(BorderFactory.createTitledBorder("Tamanho do mapa"));
		final JSlider nrDragoes = new JSlider(1, 5);
		String[] difi = { "easy", "normal", "hard" };
		final JComboBox<String> dificuldade = new JComboBox<String>(difi);
		dificuldade.setSelectedIndex(2);
		dificuldade.setBorder(BorderFactory.createTitledBorder("Dificuldade"));
		cop.add(size);
		cop.add(dificuldade);
		cop.add(nrDragoes);
		cop.add(saidaOp);
		nrDragoes.setBorder(BorderFactory
				.createTitledBorder("Numero de dragoes"));
		nrDragoes.setPaintTicks(true);
		nrDragoes.setMajorTickSpacing(1);
		nrDragoes.setMinorTickSpacing(2);
		nrDragoes.setPaintLabels(true);

		
		nrDragoes.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Maze.numDragoes = nrDragoes.getValue();

			}
		});

		// //////////////////////////////////FIM
		// OPCOES//////////////////////////////////

		saidaOp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				init.setVisible(true);
				opcoes.setVisible(false);
				Maze.dificuldade = dificuldade.getSelectedIndex();
				Maze.tamanho = (int) size.getValue();
			}
		});

		opc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				init.setVisible(false);
				opcoes.setVisible(true);

			}
		});
		// /////////////////////////////////////////////////////JOGO/////////////////////////////////////

		jogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Maze lab = new Maze();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cjogo.setLayout(new GridLayout(Maze.tamanho, Maze.tamanho,
						1 / 9, 1 / 9));
				
				String board = new String();
				//Casa[][] b = Maze.getBoard();
				for (int i = 0; i < Maze.tamanho; i++) {
					for (int j = 0; j < Maze.tamanho; j++) {
						board = Character.toString(b[j][i].getLetra());
						JLabel s = new JLabel();
						s.setText(board);
						cjogo.add(s);
						jogo.pack();
					}
				}
				init.setVisible(false);

				jogo.setVisible(true);

			}
		});

		jogo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				char tecla = e.getKeyChar();

				if (e.getKeyCode() == KeyEvent.VK_W) {
					
					
					cjogo.removeAll();
					
					
					
					//cjogo.setBounds(200, 200, 1200, 800);
					String board = new String();
					Casa[][] b = Maze.getBoard();
					b[Maze.h.x][Maze.h.y-1].setLetra('H');
					Maze.h.x-=1;
					b[Maze.h.x][Maze.h.y].setLetra(' ');
					//b[1][1].setLetra('C');
					for (int i = 0; i < Maze.tamanho; i++) {
						for (int j = 0; j < Maze.tamanho; j++) {
							board = Character.toString(b[j][i].getLetra());
							
							JLabel s = new JLabel();
							s.setText(board);
							cjogo.add(s);
						}
					}
					//jogo.pack();
					jogo.paintAll(jogo.getGraphics());
					System.out.println("cima");
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					cjogo.removeAll();
					//cjogo.setBounds(200, 200, 1200, 800);
					String board = new String();
					Casa[][] b = Maze.getBoard();
					b[Maze.h.x][Maze.h.y+1].setLetra('H');
					Maze.h.y+=1;
					b[Maze.h.x][Maze.h.y].setLetra(' ');
					//b[1][1].setLetra('B');
					for (int i = 0; i < Maze.tamanho; i++) {
						for (int j = 0; j < Maze.tamanho; j++) {
							board = Character.toString(b[j][i].getLetra());
							
							JLabel s = new JLabel();
							s.setText(board);
							cjogo.add(s);
						}
					}
					//jogo.pack();
					jogo.paintAll(jogo.getGraphics());
					System.out.println("baixo");
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					
					cjogo.removeAll();
					//cjogo.setBounds(200, 200, 1200, 800);
					String board = new String();
					Casa[][] b = Maze.getBoard();
					b[Maze.h.x-1][Maze.h.y].setLetra('H');
					Maze.h.x-=1;
					b[Maze.h.x][Maze.h.y].setLetra(' ');
					//b[1][1].setLetra('E');
					for (int i = 0; i < Maze.tamanho; i++) {
						for (int j = 0; j < Maze.tamanho; j++) {
							board = Character.toString(b[j][i].getLetra());
							
							JLabel s = new JLabel();
							s.setText(board);
							cjogo.add(s);
						}
					}
					//jogo.pack();
					jogo.paintAll(jogo.getGraphics());
					System.out.println("esquerda");
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					cjogo.removeAll();
					//cjogo.setBounds(200, 200, 1200, 800);
					String board = new String();
					Casa[][] b = Maze.getBoard();
					b[Maze.h.x+1][Maze.h.y].setLetra('H');
					Maze.h.x+=1;
					b[Maze.h.x][Maze.h.y].setLetra(' ');
				//	b[1][1].setLetra('D');
					for (int i = 0; i < Maze.tamanho; i++) {
						for (int j = 0; j < Maze.tamanho; j++) {
							board = Character.toString(b[j][i].getLetra());
							
							JLabel s = new JLabel();
							s.setText(board);
							cjogo.add(s);
						}
					}
					//jogo.pack();
					jogo.paintAll(jogo.getGraphics());
					System.out.println("direita");
				}
			}
		});

		// ///////////////////////////////////FIM
		// JOGO//////////////////////////////////////////
		/*
		 * 
		 * Maze.tamanho=25; int t= Maze.tamanho; Maze.dificuldade=1; try { Maze
		 * lab = new Maze();
		 * 
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } final Container c = init.getContentPane();
		 * c.setLayout(new GridLayout(3, 1)); JButton jogo = new
		 * JButton("Jogar"); c.add(jogo); init.setVisible(true); init.pack();
		 * Maze.tamanho=25; int t= Maze.tamanho; Maze.dificuldade=1; try { Maze
		 * lab = new Maze();
		 * 
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } final Container c = init.getContentPane();
		 * c.setLayout(new GridLayout(3, 1)); JButton jogo = new
		 * JButton("Jogar"); c.add(jogo); init.setVisible(true); init.pack();
		 * 
		 * final JFrame game = new JFrame(); game.setBounds(200, 200, 800, 600);
		 * 
		 * final Container cg = game.getContentPane();
		 * 
		 * 
		 * 
		 * jogo.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * 
		 * 
		 * paint(cg, t);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * init.setVisible(false); game.setVisible(true);
		 * 
		 * 
		 * }
		 * 
		 * private void paint(final Container cg, int t) { String board = new
		 * String(); Casa[][] b= Maze.getBoard(); cg.setLayout( new
		 * GridLayout(t,t,1,1)); for(int i=0;i< Maze.tamanho; i++){ for(int
		 * j=0;j< Maze.tamanho;j++){ board=
		 * Character.toString(b[j][i].getLetra()); JLabel s= new JLabel();
		 * s.setText(board); cg.add(s);
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * } } }); game.addKeyListener(new KeyListener() {
		 * 
		 * @Override public void keyTyped(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyReleased(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyPressed(KeyEvent e) { char tecla=
		 * e.getKeyChar();
		 * 
		 * 
		 * if(e.getKeyCode()==KeyEvent.VK_W){ Maze.h.y--;
		 * 
		 * System.out.println("cima"); } if(e.getKeyCode()==KeyEvent.VK_S){
		 * Maze.h.y++;
		 * 
		 * System.out.println("baixo"); } if(e.getKeyCode()==KeyEvent.VK_A){
		 * Maze.h.x--;
		 * 
		 * System.out.println("esquerda"); } if(e.getKeyCode()==KeyEvent.VK_D){
		 * Maze.h.x++;
		 * 
		 * System.out.println("direita"); } game.repaint(); } });
		 * 
		 * final JFrame game = new JFrame(); game.setBounds(200, 200, 800, 600);
		 * 
		 * final Container cg = game.getContentPane();
		 * 
		 * 
		 * 
		 * jogo.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * 
		 * 
		 * paint(cg, t);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * init.setVisible(false); game.setVisible(true);
		 * 
		 * 
		 * }
		 * 
		 * private void paint(final Container cg, int t) { String board = new
		 * String(); Casa[][] b= Maze.getBoard(); cg.setLayout( new
		 * GridLayout(t,t,1,1)); for(int i=0;i< Maze.tamanho; i++){ for(int
		 * j=0;j< Maze.tamanho;j++){ board=
		 * Character.toString(b[j][i].getLetra()); JLabel s= new JLabel();
		 * s.setText(board); cg.add(s);
		 * 
		 * } Maze.tamanho=25; int t= Maze.tamanho; Maze.dificuldade=1; try {
		 * Maze lab = new Maze();
		 * 
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } final Container c = init.getContentPane();
		 * c.setLayout(new GridLayout(3, 1)); JButton jogo = new
		 * JButton("Jogar"); c.add(jogo); init.setVisible(true); init.pack();
		 * 
		 * final JFrame game = new JFrame(); game.setBounds(200, 200, 800, 600);
		 * 
		 * final Container cg = game.getContentPane();
		 * 
		 * 
		 * 
		 * jogo.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * 
		 * 
		 * paint(cg, t);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * init.setVisible(false); game.setVisible(true);
		 * 
		 * 
		 * }
		 * 
		 * private void paint(final Container cg, int t) { String board = new
		 * String(); Casa[][] b= Maze.getBoard(); cg.setLayout( new
		 * GridLayout(t,t,1,1)); for(int i=0;i< Maze.tamanho; i++){ for(int
		 * j=0;j< Maze.tamanho;j++){ board=
		 * Character.toString(b[j][i].getLetra()); JLabel s= new JLabel();
		 * s.setText(board); cg.add(s);
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * } } }); game.addKeyListener(new KeyListener() {
		 * 
		 * @Override public void keyTyped(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyReleased(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyPressed(KeyEvent e) { char tecla=
		 * e.getKeyChar();
		 * 
		 * 
		 * if(e.getKeyCode()==KeyEvent.VK_W){ Maze.h.y--;
		 * 
		 * System.out.println("cima"); } if(e.getKeyCode()==KeyEvent.VK_S){
		 * Maze.h.y++;
		 * 
		 * System.out.println("baixo"); } if(e.getKeyCode()==KeyEvent.VK_A){
		 * Maze.h.x--;
		 * 
		 * System.out.println("esquerda"); } if(e.getKeyCode()==KeyEvent.VK_D){
		 * Maze.h.x++;
		 * 
		 * System.out.println("direita"); } game.repaint(); } });
		 * 
		 * 
		 * 
		 * 
		 * } } }); game.addKeyListener(new KeyListener() {
		 * 
		 * @Override public void keyTyped(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyReleased(KeyEvent e) {
		 * 
		 * 
		 * }
		 * 
		 * @Override public void keyPressed(KeyEvent e) { char tecla=
		 * e.getKeyChar();
		 * 
		 * 
		 * if(e.getKeyCode()==KeyEvent.VK_W){ Maze.h.y--;
		 * 
		 * System.out.println("cima"); } if(e.getKeyCode()==KeyEvent.VK_S){
		 * Maze.h.y++;
		 * 
		 * System.out.println("baixo"); } if(e.getKeyCode()==KeyEvent.VK_A){
		 * Maze.h.x--;
		 * 
		 * System.out.println("esquerda"); } if(e.getKeyCode()==KeyEvent.VK_D){
		 * Maze.h.x++;
		 * 
		 * System.out.println("direita"); } game.repaint(); } });
		 */
	}
}
