/**
* マインスイーパーのマスクラス 
* @author 木藤紘介
* @version 1.0
*/
class Square {
	private static final char wrap = '?';
	private static final char checker = 'x';
	public boolean hasOpened;
	public boolean hasChecked;
	private boolean isMine;
	private int content;

	/**
	* マスが地雷のときに呼ばれるコンストラクタ 
	*/
	public Square() {
		this.hasOpened = false;
		this.hasChecked = false;
		this.isMine = true;
		this.content = -1;
	} 

	/**
	* マスが地雷じゃないときに呼ばれるコンストラクタ 
	* @param content 隣接する地雷数
	*/
	public Square(int content) {
		this.hasOpened = false;
		this.hasChecked = false;
		this.isMine = false;
		this.content = content;
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