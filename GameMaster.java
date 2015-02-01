import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* マインスイーパーのマスタークラス 
* @author 木藤紘介
* @version 1.0
*/
public class GameMaster {
	private static final int NUM_OF_LINE = 5;
	private static final int NUM_OF_MINE = 5;
	private static final int CODE_OF_A = 97; // aの文字コード

	private static MainView view;

	/**
	* メインメソッド 
	* @param args 入力
	*/
	public static void main(String[] args) {
		showStartView();
		playGame();

		return;	
	}

	/**
	* スタート画面を表示するメソッド 
	*/
	private static void showStartView() {
		System.out.println("#############");
		System.out.println(" Minesweeper");
		System.out.println("#############");
		System.out.println("");
		System.out.println("Please input Enter!");

		// Enter押されたらスタート
		startGame();
	}

	/**
	* ゲーム開始の合図となるメソッド
	* Enterが押されるまで入力待ちにする
	*/
	private static void startGame() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			int ch = reader.read();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	* ゲームの一連の流れを行うメソッド
	*/
	private static void playGame() {
		view = new MainView(NUM_OF_LINE, NUM_OF_MINE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputRegex = "^([a-z])(\\d+)([ox])$";
		Pattern pat = Pattern.compile(inputRegex);

		try {
			while (!isClear()) {
				view.showView();
				showDescription();

				String input = reader.readLine();
				Matcher mat = pat.matcher(input);

				if (!mat.find()) {
					System.out.println("入力が正しくありません。");
					continue;
				}

				char column = mat.group(1).charAt(0);
				int row = Integer.parseInt(mat.group(2));
				char action = mat.group(3).charAt(0);

				// アルファベットからaの文字コードを引くことでaを0とする数字に変換
				int columnNum = column - CODE_OF_A;

				// エラーチェック
				if (containsInvalidInput(row, columnNum)) {
					continue;
				}

				switch (action) {
					case 'o':
						handleOpenAction(row, columnNum);
						break;
					case 'x':
						view.checkSquare(row, columnNum);
						break;
					default:
						// ここに来ることはありえない
						break;
				}
			}

			view.showView();
			showGameClear();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	* ゲームがクリアされたかどうかを判定するメソッド
	* @return true:クリア false:クリアでない
	*/
	private static boolean isClear() {
		int closedSquares = view.getClosedSquares();

		if (closedSquares == NUM_OF_MINE) {
			return true;
		}

		return false;
	}

	/**
	* 入力の説明を記述するメソッド
	*/
	private static void showDescription() {
		System.out.println("");
		System.out.println("選択する列・行・アクション（開く:o or 地雷チェック:x）を順に入力してください。");
		System.out.println("例：a0o");
	}

	/**
	* 範囲を超えた入力がされていないかを判定するメソッド
	* @param row マスの行番号
	* @param column マスの列番号
	* @return true:範囲を超えた入力 false:正しい入力
	*/
	private static boolean containsInvalidInput(int row, int column) {
		if (column >= NUM_OF_LINE) {
			System.out.println("入力が列の範囲を超えています。");
			return true;
		}

		if (row >= NUM_OF_LINE) {
			System.out.println("入力が行の範囲を超えています。");
			return true;
		}

		return false;
	}

	/**
	* マスを開く際の挙動を操作するメソッド
	* @param row マスの行番号
	* @param column マスの列番号
	*/
	private static void handleOpenAction(int row, int column) {
		if (view.hasChecked(row, column)) {
			return;
		}

		if (view.isMine(row, column)) {
			showGameOver();
			System.exit(0);
		} else {
			view.openSquare(row, column);
		}
	}

	/**
	* ゲームオーバー画面を表示するメソッド
	*/
	private static void showGameOver() {
		System.out.println("");
		System.out.println("Game Over...");
	}

	/**
	* ゲームクリア画面を表示するメソッド
	*/
	private static void showGameClear() {
		System.out.println("");
		System.out.println("Game Cleared!!!");
	}

}