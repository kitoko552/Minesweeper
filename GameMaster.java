import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* マインスイーパーのマスタークラス 
* @author 木藤紘介
* @version 3.0
*/
public class GameMaster {
	private static final int CODE_OF_A = 97; // aの文字コード

	private static Field field;
	private static int numOfLines;
	private static int numOfMines;

	/**
	* メインメソッド 
	* @param args 入力
	*/
	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("コマンドライン引数は不必要です。");
			return;
		}

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
		System.out.println("フィールドの大きさと地雷の数を順に入力してください。");
		System.out.println("フィールドの大きさ:5~10 地雷の数:5~50");
		System.out.println("フィールドの大きさ:5x5 地雷の数:5 の場合の入力 -> 5 5");

		setupGame();
	}

	/**
	* ゲームの設定をするメソッド
	*/
	private static void setupGame() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputRegex = "^(\\d+)\\s(\\d+)$";
		Pattern pat = Pattern.compile(inputRegex);

		try {
			String input = reader.readLine();
			Matcher mat = pat.matcher(input);

			if (!mat.find()) {
				System.out.println("入力が正しくありません。");
				System.exit(0);
			}

			numOfLines = Integer.parseInt(mat.group(1));
			numOfMines = Integer.parseInt(mat.group(2));

			if (containsInvalidSettings(numOfLines, numOfMines)) {
				System.exit(0);
			}

			field = new Field(numOfLines, numOfMines);

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	* ゲーム設定の入力が範囲を超えていないかを判定するメソッド
	* @param lines 行（列）の数
	* @param mines 地雷の数
	* @return true:範囲を超えた入力 false:正しい入力
	*/
	private static boolean containsInvalidSettings(int lines, int mines) {
		if (lines < 5 || lines > 10) {
			System.out.println("フィールドの大きさは5~10（5x5 ~ 10x10）です。");
			return true;
		} else if (mines < 5 || mines > 50) {
			System.out.println("地雷の数は5~50です。");
			return true;
		} else if (lines * lines <= mines) {
			System.out.println("地雷数がマス数を超えています。");
			return true;
		}

		return false;
	}

	/**
	* ゲームの一連の流れを行うメソッド
	*/
	private static void playGame() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputRegex = "^([a-z])(\\d+)([ox])$";
		Pattern pat = Pattern.compile(inputRegex);

		try {
			while (!isClear()) {
				field.show();
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
						field.checkSquare(row, columnNum);
						break;
					default:
						// ここに来ることはありえない
						break;
				}
			}

			field.show();
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
		int closedSquares = field.getClosedSquares();

		if (closedSquares == numOfMines) {
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
		if (column >= numOfLines) {
			System.out.println("入力が列の範囲を超えています。");
			return true;
		}

		if (row >= numOfLines) {
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
		if (field.hasChecked(row, column)) {
			return;
		}

		if (field.isMine(row, column)) {
			showGameOver();
			System.exit(0);
		} else {
			field.openSquare(row, column);
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