
public class LetterNode {
	private char Letter;
	private LetterNode next;
	
	
	public LetterNode(char letter) {
		Letter = letter;
		next=null;
	}


	public char getLetter() {
		return Letter;
	}


	public void setLetter(char data) {
		Letter = data;
	}


	public LetterNode getNext() {
		return next;
	}


	public void setNext(LetterNode next) {
		this.next = next;
	}
	
	
	
}
