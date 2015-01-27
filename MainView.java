import java.util.Random;
import java.util.Arrays;
import java.util.List;

/**
* マインスイーパーのビュークラス 
* @author 木藤紘介
* @version 1.0
*/
class MainView {
	private int numOfLine;
	private int numOfMine;
	private Square[] squares;
	private int[] mineNums;

	/**
	* コンストラクタ 
	* @param numOfLine 行数
	* @param numOfMine 地雷数
	*/
	public MainView(int numOfLine, int numOfMine) {
		this.numOfLine = numOfLine;
		this.numOfMine = numOfMine;

		setupMines();
		setupSquares();
	}

	/**
	* 地雷の位置を設定するメソッド
	*/
	private void setupMines() {
		Random rand = new Random();
        mineNums = new int[numOfMine];
        for (int i = 0; i < numOfMine; i++) {
        	mineNums[i] = rand.nextInt(numOfLine * numOfLine);
        }
	}

	/**
	* それぞれのマスの内容を設定するメソッド
	*/
	private void setupSquares() {
        squares = new Square[numOfLine * numOfLine];
		for (int i = 0; i < numOfLine * numOfLine; i++) {
			if (isContainsInMineNums(i)) {
				squares[i] = new Square();
				System.out.println(i);
			} else {
				squares[i] = new Square(countNeighboringMine(i));
			}
		}
	}

	/**
	* 地雷の番号の配列に数字が含まれているかどうかを判定するメソッド
	* @param num 対象となるマスの配列における番号
	* @return true: 含まれている false: 含まれていない
	*/
	private boolean isContainsInMineNums(int num) {
		for (int i : mineNums) {
			if (i == num) {
				return true;
			}
		}

		return false;
	}

	/**
	* 隣接する地雷数を数えるメソッド
	* @param squareNum 対象となるマスの配列における番号
	* @return 隣接する地雷数
	*/
	private int countNeighboringMine(int squareNum) {
		int result = 0;
		for (int num : mineNums) {
			if (isNeighboring(squareNum, num)) {
				result++;
			}
		}

		return result;
	}

	/**
	* マスと地雷が隣接しているかどうかを判定するメソッド
	* @param squareNum 対象となるマスの配列における番号
	* @param mineNum 対象となる地雷の配列における番号
	* @return true: 隣接　false: 隣接していない
	*/
	private boolean isNeighboring(int squareNum, int mineNum) {
		switch (squareNum) {
			// 角
			case 0:
				return mineNum == 1
					|| mineNum == numOfLine
					|| mineNum == numOfLine + 1;
			case numOfLine - 1:
				return mineNum == numOfLine - 2
					|| mineNum == 2 * numOfLine - 2
					|| mineNum == 2 * numOfLine - 1;
			case numOfLine * (numOfLine - 1):
				return mineNum == numOfLine * (numOfLine - 2)
					|| mineNum == numOfLine * (numOfLine - 2) + 1
					|| mineNum == numOfLine * (numOfLine - 1) + 1;
			case numOfLine * numOfLine - 1:
				return mineNum == numOfLine * (numOfLine - 1) - 2
					|| mineNum == numOfLine * (numOfLine - 1) - 1
					|| mineNum == numOfLine * numOfLine - 2;
			// 端
			case 
		}
	}

	/**
	* ビューを表示するメソッド
	*/
	public void showView() {
		System.out.println("  a b c d e");
		for (int i = 0; i < numOfLine; i++) {
			System.out.print(i);
			for (int j = i * numOfLine; j < i * numOfLine + numOfLine; j++) {
				System.out.print(" ");
				squares[j].showState();
			}

			System.out.println("");
		}
	}

	/**
	* マスを開くメソッド
	* @param num 対象となるマスの配列における番号
	*/
	public void openSquare(int num) {
		squares[num].hasOpened = true;
	}

	/**
	* マスに地雷チェックを入れるメソッド
	* @param num 対象となるマスの配列における番号
	*/
	public void checkSquare(int num) {
		squares[num].hasChecked = true;
	}

}