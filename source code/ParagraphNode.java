
public class ParagraphNode {
	private int ParagraphNode;
	private ParagraphNode down;
	private LetterNode right;
	
	
	public ParagraphNode(int DataToAdd) {
		ParagraphNode = DataToAdd;
		down = null;
		right = null;
	}


	public int getParagraphNode() {
		return ParagraphNode;
	}


	public void setParagraphNode(int data) {
		ParagraphNode = data;
	}


	public ParagraphNode getDown() {
		return down;
	}


	public void setDown(ParagraphNode down) {
		this.down = down;
	}


	public LetterNode getRight() {
		return right;
	}


	public void setRight(LetterNode right) {
		this.right = right;
	}

	
}
