this.game = {
	makeMove: function (board, turn) {
		for (var i = 0; i < board.length; i++) {
			for (var j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					board[i][j] = turn;
					return;
				}
			}
		}
	}
};