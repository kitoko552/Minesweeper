import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
* マインスイーパーのマスタークラス 
* @author 木藤紘介
* @version 1.0
*/
public class GameMaster {
	private static final int NUM_OF_LINE = 5;
	private static final int NUM_OF_MINE = 5;

	/**
	* メインメソッド 
	* @param args 入力
	*/
	public static void main(String[] args) {
		showStart();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			int ch = reader.read();

			MainView view = new MainView(NUM_OF_LINE, NUM_OF_MINE);

			while (true) { // TODO: isClear()
				view.showView();
				System.out.println("");
				
				System.out.println("選択する列・行を入力してください。");
				String place = reader.readLine();

				// TODO: エラーチェック
				if (place.length() != 2) {
					System.out.println("入力が正しくありません。");
					continue;
				}

				char column = place.charAt(0);
				int row = Character.getNumericValue(place.charAt(1));

				System.out.println("開く：o 地雷チェック：x");
				String action = reader.readLine();

				// TODO: エラーチェック
				if (action.length() != 1) {
					System.out.println("入力が正しくありません。");
					continue;
				}

				int targetNum = transformColumnToNum(column) + transformRowToNum(row); 
				if (action.equals("o")) {
					view.openSquare(targetNum);
				} else if (action.equals("x")) {
					view.checkSquare(targetNum);
				} else {
					// error
				}

				System.out.println("");



			}

		} catch (IOException e) {
			System.out.println(e);
		}


		
	}

	private static int transformColumnToNum(char column) {
		switch (column) {
			case 'a':
				return 0;
			case 'b':
				return 1;
			case 'c':
				return 2;
			case 'd':
				return 3;
			case 'e':
				return 4;

			default:
				return -1;
		}
	}

	private static int transformRowToNum(int row) {
		return NUM_OF_LINE * row;
	}

	/**
	* スタート画面を表示するメソッド 
	*/
	private static void showStart() {
		System.out.println("#############");
		System.out.println(" Minesweeper");
		System.out.println("#############");
		System.out.println("");
		System.out.println("Please input Enter!");
	}
}