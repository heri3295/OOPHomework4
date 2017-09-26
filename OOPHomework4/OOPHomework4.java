/* 
Decided to cap my input from 4 to 7. Anything under 4 doesn't work, anything after 4 breaks my machine. (That was as I tried the RandomizedAnswers() method). 
I wil come back to comment out the code. 
*/


import java.util.Random; 
import java.util.Scanner; 

public class OOPHomework4 {	
	
	public static void main (String[] args) {
		Scanner read = new Scanner(System.in); 
		Random rand = new Random(); 
		final int BOARDSIZE = ErrorCheckValueN(); 
		Queens[] queens = new Queens[BOARDSIZE]; 
		for (int i = 0; i < BOARDSIZE; i++) {
			queens[i] = new Queens( rand.nextInt(BOARDSIZE) + 1, rand.nextInt(BOARDSIZE) + 1, BOARDSIZE ); 
		}		
		GameBoard gameboard = new GameBoard( queens, BOARDSIZE ); 
	}
	
	public static int ErrorCheckValueN () {
		Scanner read = new Scanner(System.in); 
		int boardsize = 0; 
		System.out.println("Hello, how big would you like are 'N'? "); 
		while (boardsize < 4 || boardsize > 7) {	
			if(read.hasNextInt()) {
				boardsize = read.nextInt(); 
				if( boardsize < 4 || boardsize > 7 ) {
					System.out.println("Try a number between 4 and 7. ");
				}
			}
			else {
				read.next(); 
				System.out.println("Error, give me a number. ");
			}
		} 
		return boardsize; 
	}
	
	static class GameBoard {
		private final char QueenLocation = 'Q'; 
		private final int SIZEOFN; 
		private final int BOARDSIZE; 
		private Queens[] QueensData; 
		private char[][] GameBoard; 
		private boolean[] AttackFlags; 
		private boolean SolutionAvailable; 
		
		public GameBoard ( Queens[] queens, int sizeofn ){
			QueensData = queens; 
			SolutionAvailable = false; 
			SIZEOFN = sizeofn; 
			BOARDSIZE = sizeofn + 1; 
			GameBoard = new char[BOARDSIZE + 1][BOARDSIZE + 1]; 
			AttackFlags = new boolean[QueensData.length]; 
			for ( int i = 0; i < QueensData.length; i++ ){ AttackFlags[i] = false; }
			GenerateGameBoard(); 
			//UpdateBoard (this.QueensData); 			//If you just want to print first board. 
			RunGame(); 
		}
		
		public void RunGame() {
			while (SolutionAvailable == false) {
				WipeBoard(); 
				FixOverlappingQueens(); 
				AddQueens(); 
				SetAttackFlagsData();
				CheckIfSolution(); 
				PrintBoard();
				//RandomizedAnswers(); 
				KnightsAlgo(); 
			}
			System.out.println("Solution found!"); 
		}
		
		public void UpdateBoard(Queens[] queens) {
			QueensData = queens; 
			WipeBoard(); 
			FixOverlappingQueens(); 
			AddQueens(); 
			SetAttackFlagsData();
			PrintBoard(); 
		}
		
		public void KnightsAlgo() {
			if (SIZEOFN % 2 == 0 ){ KnightsAlgoEven (2, 1); }
			else { KnightsAlgoOdd (1, 1); }
		}
		
		public void KnightsAlgoOdd(int startingx, int startingy) {
			int XINDEX = startingx;					//These should be between 1 and N. 
			int YINDEX = startingy;					//These should be between 1 and N.  
			int queenIndex = 0; 
			while (queenIndex < QueensData.length) {
				QueensData[queenIndex] = QueensData[queenIndex].setCoordinates ( XINDEX, YINDEX); 
				XINDEX++; 
				if ( XINDEX >= BOARDSIZE ) { XINDEX = 1; }
				XINDEX++; 
				if ( XINDEX >= BOARDSIZE ) { XINDEX = 1; }
				YINDEX++; 
				if ( YINDEX >= BOARDSIZE ) { YINDEX = 1; }
				queenIndex++; 
			}
		}
		
		public void KnightsAlgoEven(int startingx, int startingy) {
			int XINDEX = startingx;					//These should be between 1 and N. 
			int YINDEX = startingy;					//These should be between 1 and N.  
			int queenIndex = 0; 
			while (queenIndex < QueensData.length) {
				QueensData[queenIndex] = QueensData[queenIndex].setCoordinates ( XINDEX, YINDEX); 
				XINDEX++; 
				XINDEX++; 
				YINDEX++; 
				if ( XINDEX >= BOARDSIZE ) { XINDEX = 1; }
				queenIndex++; 
			}
		}
		
		private void CheckIfSolution() {
			SolutionAvailable = true; 
			for (int i = 0; i < AttackFlags.length; i++) {
				if ( AttackFlags[i] == true ) { SolutionAvailable = false; return; }
			}
		}
	
		private void PrintBoard() { 
			for (int y = 0; y <= BOARDSIZE; y++) {
				for (int x = 0; x <= BOARDSIZE; x++) {
					System.out.print( " " + GameBoard[y][x] + " " ); 
					if ( x == BOARDSIZE ) { System.out.println("\n"); }
				}
			}
		}
		
		private void WipeBoard() {
			for (int y = 0; y <= BOARDSIZE; y++) {
				for (int x = 0; x <= BOARDSIZE; x++) {
					if ( GameBoard[y][x] == QueenLocation ) {
						GameBoard[y][x] = ' '; 
					}
				}
				if ( y < QueensData.length) {
					AttackFlags[y] = false; 					//Be sure to reset the flags!
				}
			}
		}
		
		private void GenerateGameBoard() {
			for (int y = 0; y <= BOARDSIZE; y++) {
				for (int x = 0; x <= BOARDSIZE; x++) {
					if ( y == 0 ) {
						GameBoard[0][x] = '_' ;
					}
					if ( y == BOARDSIZE ) {
						GameBoard[BOARDSIZE][x] = '_' ;
					}
					if ( x == 0 ) {
						GameBoard[y][0] = '|' ; 
					}
					if ( x == BOARDSIZE ) {
						GameBoard[y][BOARDSIZE] = '|' ; 
					}
				}
			}
		}
		
		private void AddQueens() {
			for (int y = 1; y < BOARDSIZE; y++) {
				for (int x = 1; x < BOARDSIZE; x++) {
					for (int k = 0; k < QueensData.length; k++) {
						if (QueensData[k].getXCoor() == x && QueensData[k].getYCoor() == y  ) {
							GameBoard[y][x] = QueenLocation; 
						}
					} 
				}
			}
		}
		
		private void RandomizedAnswers() {
			for (int i = 0; i < AttackFlags.length; i++) {
				if (AttackFlags[i] == true) { QueensData[i] = QueensData[i].RequestNewCoordinates(); }
			}
		}
		
		private void FixOverlappingQueens() {
			boolean[] overlap = new boolean[QueensData.length];
			boolean isThereOverlap ; 
			do {
				overlap = CheckOverlappingQueens(); 
				for (int i = 0; i < overlap.length; i++) {
				}
				for (int i = 0; i < overlap.length; i++) {
					if (overlap[i] == true) { QueensData[i] = QueensData[i].RequestNewCoordinates(); }
				}
				overlap = CheckOverlappingQueens(); 
				isThereOverlap = false; 
				for (int i = 0; i < overlap.length; i++) {
					if (overlap[i] == true) {
						isThereOverlap = true; 
						break; 
					}
				}
			} while (isThereOverlap == true); 
		}
		
		private boolean[] CheckOverlappingQueens() {
			boolean[] overlap = new boolean[QueensData.length]; 
			for (int i = 0; i < overlap.length; i++) { overlap[i] = false; }
			for (int i = 0; i < QueensData.length; i++) {
				for (int j = i + 1; j < QueensData.length; j++) {
						if ( QueensData[i].getXCoor() == QueensData[j].getXCoor() ){
							if ( QueensData[i].getYCoor() == QueensData[j].getYCoor() ) { overlap[i] = true; } 
						}
						else { 
							if ( overlap[i] == true ) { overlap[i] = true; } 
							else { overlap[i] = false; }
						}
				}
			}
			return overlap; 
		}
		
		private void SetAttackFlagsData() {
			for (int i = 0; i < QueensData.length; i++) {
				for (int j = 0; j < QueensData.length; j++) {
					if ( i == j ) { continue; }
					if ( AttackFlags[i] == true ) { 
						AttackFlags[i] = true ;
					} else {
						QueensData[i].setAttacked( QueensData[j].getXCoor(), QueensData[j].getYCoor() ); 
						if ( QueensData[i].getAttacked() == true ){
							AttackFlags[i] = true; 
						} 
						else {
							AttackFlags[i] = false; 
						}
					}
				}
			}
		}
	}
	
	static class Queens{
		private final int AmountOfQueens; 
		private final int XCOOR; 				//The coordinates do not start their count from 0!
		private final int YCOOR; 				//The coordinates do not start their count from 0! 
		private boolean ATTACKED; 				//If this Queen is being attacked, this is true. 
		
		public Queens(int amountofqueens) {
			XCOOR = 0; 
			YCOOR = 0; 
			AmountOfQueens = amountofqueens; 
			ATTACKED = false; 
		}
		
		public Queens(int xcoor, int ycoor, int amountofqueens) {
			XCOOR = xcoor; 
			YCOOR = ycoor; 
			AmountOfQueens = amountofqueens; 
			ATTACKED = false; 
		}
		
		public Queens setCoordinates ( int theirxcoor, int theirycoor) {
			if ((theirxcoor >= 1 && theirxcoor <= this.AmountOfQueens) && (theirycoor >= 1 && theirycoor <= this.AmountOfQueens)) {
				Queens temporaryQueen = new Queens (theirxcoor, theirycoor, AmountOfQueens); 
				return temporaryQueen; 
			}
			System.out.println("Critical Error; NULL sent. ");
			return null; 
		}
		
		public String PrintQueenData() {
			String info = " (" + XCOOR + ", " + YCOOR + "). "; 
			return info; 
		}
		
		public void setAttacked (int theirxcoor, int theirycoor) { 
			if ((theirxcoor >= 1 && theirxcoor <= AmountOfQueens) && (theirycoor >= 1 && theirycoor <= AmountOfQueens)) {
				ATTACKED = AmIBeingAttacked (theirxcoor, theirycoor); 
			}
		}
		
		public Queens RequestNewCoordinates() {
			Random rand = new Random(); 
			Queens temporaryQueen = new Queens ( rand.nextInt(AmountOfQueens) + 1, rand.nextInt(AmountOfQueens) + 1, AmountOfQueens); 
			return temporaryQueen; 
		}
		
		public int getXCoor() { return XCOOR; }
		public int getYCoor() { return YCOOR; }
		public int getBOARDSIZE() { return AmountOfQueens; }
		public boolean getAttacked() { return ATTACKED; }
		
		private boolean AmIBeingAttacked(int theirxcoor, int theirycoor) {
			boolean isAttacked = false; 
			isAttacked = checkCross(theirxcoor, theirycoor); 
			if (isAttacked == true) { return isAttacked; }
			isAttacked = checkDiagonalNegativeBackward(theirxcoor, theirycoor); 
			if (isAttacked == true) { return isAttacked; }
			isAttacked = checkDiagonalNegativeForward (theirxcoor, theirycoor); 
			if (isAttacked == true) { return isAttacked; }
			isAttacked = checkDiagonalPositiveBackward (theirxcoor, theirycoor);
			if (isAttacked == true) { return isAttacked; }
			isAttacked = checkDiagonalPositiveForward (theirxcoor, theirycoor); 
			return isAttacked; 
		}
		
		private boolean checkDiagonalPositiveForward( int theirxcoor, int theirycoor ) {
			boolean isAttacked = false; 
			int x = theirxcoor; 
			int y = theirycoor; 
			while ( y >= 1 && x <= AmountOfQueens) {
				if ( x == this.XCOOR && y == this.YCOOR) {
					isAttacked = true; 
					return isAttacked; 
				}
				x++; 
				y--; 
			}
			return isAttacked; 
		}
		
		private boolean checkDiagonalPositiveBackward( int theirxcoor, int theirycoor ) {
			boolean isAttacked = false; 
			int x = theirxcoor; 
			int y = theirycoor; 
			while ( y <= AmountOfQueens && x >= 1 ) {
				if ( x == this.XCOOR && y == this.YCOOR) {
					isAttacked = true; 
					return isAttacked; 
				}
				x--; 
				y++; 
			}
			return isAttacked; 
			
		}
		
		private boolean checkDiagonalNegativeForward( int theirxcoor, int theirycoor ) {
			boolean isAttacked = false; 
			int x = theirxcoor; 
			int y = theirycoor; 
			while ( y <= AmountOfQueens && x <= AmountOfQueens) {
				if ( x == this.XCOOR && y == this.YCOOR) {
					isAttacked = true; 
					return isAttacked; 
				}
				x++; 
				y++; 
			}
			return isAttacked; 
		}
		
		private boolean checkDiagonalNegativeBackward( int theirxcoor, int theirycoor ) {
			boolean isAttacked = false; 
			int x = theirxcoor; 
			int y = theirycoor; 
			while ( y >= 1 && x >= 1 ) {
				if ( x == this.XCOOR && y == this.YCOOR) {
					isAttacked = true; 
					return isAttacked; 
				}
				x--; 
				y--; 
			}
			return isAttacked; 
		}
		
		private boolean checkCross( int theirxcoor, int theirycoor ) {
			boolean isAttacked = false; 
			if (theirxcoor == this.XCOOR || theirycoor == this.YCOOR) { isAttacked = true; }
			return isAttacked; 
		}
	}
}

