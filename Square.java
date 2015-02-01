/**
* マインスイーパーのマスクラス 
* @author 木藤紘介
* @version 4.0
*/
class Square {
	private static final char wrap = '@';
	private static final char checker = 'x';
	public boolean hasOpened = false;
	public boolean hasChecked = false;
	private boolean isMine;
	private int content;

	/**
	* マスが地雷のときに呼ばれるコンストラクタ 
	*/
	public Square() {
		this.isMine = true;
		this.content = -1;
	} 

	/**
	* マスが地雷じゃないときに呼ばれるコンストラクタ 
	* @param content 隣接する地雷数
	*/
	public Square(int content) {
		this.isMine = false;
		this.content = content;
	}

	/**
	* contentのゲッター
	* @return content
	*/
	public int getContent() {
		return this.content;
	}

	public boolean isMine() {
		return this.isMine;
	}

	/**
	* ビューに現在の状態を表示するメソッド 
	*/
	public void showState() {
		if (hasOpened) {
			System.out.print(content);
		} else if (hasChecked) {
			System.out.print(checker);
		} else {
			System.out.print(wrap);
		}
	}
}