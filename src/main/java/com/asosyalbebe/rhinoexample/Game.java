package com.asosyalbebe.rhinoexample;

public class Game {
	private static final int EMPTY = 0;
	private static final int PLAYER1 = 1;
	private static final int PLAYER2 = 2;

	private int[][] board;
	private int emptySpace;
	private int turn;

	public Game() {
		board = new int[3][3];
		resetBoard();
	}

	public void start() {
		turn = Math.random() < 0.5 ? PLAYER1 : PLAYER2;
		while (!isBoardFull()) {
			makeMove(turn);
			turn = turn == PLAYER1 ? PLAYER2 : PLAYER1;
			printBoard();
		}
	}

	private void printBoard() {
		System.out.println("+---+---+---+");
		for (int i = 0; i < board.length; i++) {
			System.out.printf("| %d | %d | %d |\n", board[i][0], board[i][1], board[i][2]);
			System.out.println("+---+---+---+");
		}
		System.out.println("--------------------------------------");
	}

	private void makeMove(int player) {
		// Simple algorithm, choose the first empty place
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == EMPTY) {
					board[i][j] = player;
					emptySpace--;
					return;
				}
			}
		}
	}

	private boolean isBoardFull() {
		return emptySpace == 0;
	}

	private void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = EMPTY;
			}
		}

		emptySpace = 9;
	}

	public static void main(String[] args) {
		Game game = new Game();
		long t = System.currentTimeMillis();
		game.start();
		System.out.println(System.currentTimeMillis() - t);
	}
}
