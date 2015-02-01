import java.util.Random;
import java.util.Arrays;
import java.util.List;

/**
* マインスイーパーのフィールドクラス 
* @author 木藤紘介
* @version 3.0
*/
class Field {
	private static final int CODE_OF_A = 97; // aの文字コード
	private int numOfLines;
	private int numOfMines;
	private Square[][] squares;
	private int[] mineNums;

	/**
	* コンストラクタ 
	* @param numOfLines 行数
	* @param numOfMines 地雷数
	*/
	public Field(int numOfLines, int numOfMines) {
		this.numOfLines = numOfLines;
		this.numOfMines = numOfMines;

		setupMines();
		setupSquares();
	}

	/**
	* 地雷の位置を設定するメソッド
	*/
	private void setupMines() {
		Random rand = new Random();
        mineNums = new int[numOfMines];
        int randNum;

        for (int i = 0; i < numOfMines; i++) {
        	randNum = rand.nextInt(numOfLines * numOfLines);

        	// 重複しないようにするための処理
        	for (int j = 0; j < i; j++) {
        		if (mineNums[j] == randNum) {
        			randNum = rand.nextInt(numOfLines * numOfLines);
        			j = -1; // 重複があったら最初から確認し直す
        			continue;
        		}
        	}

        	mineNums[i] = randNum;
        }
	}

	/**
	* それぞれのマスの内容を設定するメソッド
	*/
	private void setupSquares() {
        squares = new Square[numOfLines][numOfLines];

		for (int i = 0; i < numOfLines; i++) {
			for (int j = 0; j < numOfLines; j++) {
				int serialNum = i * numOfLines + j; // 通し番号

				if (containsInMineNums(serialNum)) {
					squares[i][j] = new Square();
				} else {
					squares[i][j] = new Square(countNeighboringMine(i, j));
				}
			}
		}
	}

	/**
	* 地雷番号の配列に数字が含まれているかどうかを判定するメソッド
	* @param serialNum 対象となるマスの通し番号
	* @return true:含まれている false:含まれていない
	*/
	private boolean containsInMineNums(int serialNum) {
		for (int i : mineNums) {
			if (i == serialNum) {
				return true;
			}
		}

		return false;
	}

	/**
	* 隣接する地雷数を数えるメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	* @return 隣接する地雷数
	*/
	private int countNeighboringMine(int row, int column) {
		int result = 0;

		for (int num : mineNums) {
			if (isNeighboring(row, column, num)) {
				result++;
			}
		}

		return result;
	}

	/**
	* マスと地雷が隣接しているかどうかを判定するメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	* @param mineNum 対象となる地雷の配列における番号
	* @return true:隣接　false:隣接していない
	*/
	private boolean isNeighboring(int row, int column, int mineNum) {
		// 通し番号から行と列に変換
		int rowOfMine = mineNum / numOfLines; // 商が行番号になる
		int columnOfMine = mineNum % numOfLines; // 余りが列番号になる
		
		return row - 1 == rowOfMine && column - 1 == columnOfMine
			|| row - 1 == rowOfMine && column == columnOfMine
			|| row - 1 == rowOfMine && column + 1 == columnOfMine
			|| row == rowOfMine && column - 1 == columnOfMine
			|| row == rowOfMine && column + 1 == columnOfMine
			|| row + 1 == rowOfMine && column - 1 == columnOfMine
			|| row + 1 == rowOfMine && column == columnOfMine
			|| row + 1 == rowOfMine && column + 1 == columnOfMine;
	}

	/**
	* ビューを表示するメソッド
	*/
	public void show() {
		System.out.println("");
		System.out.print(" ");

		// 数字からアルファベットに変換する処理
		for (int i = 0; i < numOfLines; i++) {
			char alpha = (char)(i + CODE_OF_A);
			System.out.print("  " + alpha);
		}

		System.out.println("");
		
		for (int i = 0; i < numOfLines; i++) {
			System.out.print(i);

			for (int j = 0; j < numOfLines; j++) {
				System.out.print("  ");
				squares[i][j].showState();
			}

			System.out.println("");
		}
	}

	/**
	* マスを開くメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	*/
	public void openSquare(int row, int column) {
		squares[row][column].hasOpened = true;
	}

	/**
	* マスに地雷チェックを入れるメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	*/
	public void checkSquare(int row, int column) {
		if (squares[row][column].hasChecked) {
			squares[row][column].hasChecked = false;
		} else {
			squares[row][column].hasChecked = true;
		}
	}

	/**
	* マスが地雷かどうかを判定するメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	* @return true:地雷 false:地雷でない 
	*/
	public boolean isMine(int row, int column) {
		if (squares[row][column].isMine) {
			return true;
		}

		return false;
	}

	/**
	* マスが地雷チェックされているかどうかを判定するメソッド
	* @param row 対象となるマスの行番号
	* @param column 対象となるマスの列番号
	* @return true:地雷チェック済み false:地雷チェックされていない 
	*/
	public boolean hasChecked(int row, int column) {
		if (squares[row][column].hasChecked) {
			return true;
		}

		return false;
	}

	/**
	* 開いていないマス数を求めるメソッド
	* @return 開いていないマス数
	*/
	public int getClosedSquares() {
		int closedSquares = numOfLines * numOfLines;

		// 開いているマス数を全マス数から引く
		for (int i = 0; i < numOfLines; i++) {
			for (int j = 0; j < numOfLines; j++) {
				if (squares[i][j].hasOpened) {
					closedSquares--;
				}
			}
		}

		return closedSquares;
	}

}