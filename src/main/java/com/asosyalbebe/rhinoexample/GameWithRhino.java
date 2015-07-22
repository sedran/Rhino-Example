package com.asosyalbebe.rhinoexample;

import java.io.FileReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class GameWithRhino {
	private static final int EMPTY = 0;
	private static final int PLAYER1 = 1;
	private static final int PLAYER2 = 2;

	private int[][] board;
	private int emptySpace;
	private int turn;

	public GameWithRhino() {
		board = new int[3][3];
		resetBoard();
	}

	public void start(Function gameLogic, Context ctx, Scriptable scope) {
		turn = Math.random() < 0.5 ? PLAYER1 : PLAYER2;

		while (!isBoardFull()) {
			makeMove(gameLogic, ctx, scope, turn);
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

	private void makeMove(Function gameLogic, Context ctx, Scriptable scope, int player) {
		gameLogic.call(ctx, scope, scope, new Object[] { board, turn });
		emptySpace--;
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
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		Function makeMove;

		try {
			Reader reader = new FileReader("gameLogic.js");
			cx.evaluateReader(scope, reader, "gameLogic", 1, null);
			Scriptable jsContext = (Scriptable) scope.get("game", scope);
			makeMove = (Function) jsContext.get("makeMove", jsContext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		GameWithRhino game = new GameWithRhino();
		long t = System.currentTimeMillis();
		game.start(makeMove, cx, scope);
		System.out.println(System.currentTimeMillis() - t);
	}
}
