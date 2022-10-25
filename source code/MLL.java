import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MLL {
	ParagraphNode head;	

	public void addParagraph(int dataToAdd) {
		ParagraphNode temp;
		if (head == null) {
			temp = new ParagraphNode(dataToAdd); 
			head = temp;
		}
		else {		     
			temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			ParagraphNode newnode = new ParagraphNode(dataToAdd);
			temp.setDown(newnode);
		}
	}
	
	public void addLetter(int Paragraph, char Letter) {
		if (head == null)
			System.out.println("Add a Paragraph before Letter");
		else {
			ParagraphNode temp = head;
			while (temp != null)
			{	    	 
				if (Paragraph==temp.getParagraphNode()) {
					LetterNode temp2 = temp.getRight(); 
					if (temp2 == null) {
						temp2 = new LetterNode(Letter); 
						temp.setRight(temp2);
					}
					else {				 
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						LetterNode newnode = new LetterNode(Letter);
						temp2.setNext(newnode);
					}
				}
				temp = temp.getDown();
			}
		}
	}
	
	public void DeleteLetter(int Paragraph, int Location) {
		if (head == null)
			System.out.println("error delet letter");
		else
		{
			ParagraphNode temp = head;
			while(temp!=null)
			{
				if(Paragraph==temp.getParagraphNode())
				{
					LetterNode temp2 = temp.getRight();
					LetterNode previous=null;
					for (int i = 1; i <= Location; i++) {
						if(i==Location&&temp2!=null)
						{
							previous.setNext(temp2.getNext());
							temp2 = previous;
						}
						else
						{
							previous = temp2;
							temp2 = temp2.getNext();
						}
						
					}
				}
				temp=temp.getDown();
				
			}
		}
	}
	
	public void Overwrite(int Paragraph, int Location,char letter) {
		if (head == null)
			System.out.println("error delet letter");
		else
		{
			ParagraphNode temp = head;
			while(temp!=null)
			{
				if(Paragraph==temp.getParagraphNode())
				{
					LetterNode temp2 = temp.getRight();
					for (int i = 1; i <= Location; i++) {
						if(i==Location&&temp2!=null)
						{
							temp2.setLetter(letter);
							break;
						}
						temp2 = temp2.getNext();
						
					}
				}
				temp=temp.getDown();
				
			}
		}
	}
	
	public char Selection(int Paragraph, int Location) {
		char letter =(Character) null;
		if (head == null)
			System.out.println("selection");
		else
		{
			ParagraphNode temp = head;
			while(temp!=null)
			{
				if(Paragraph==temp.getParagraphNode())
				{
					LetterNode temp2 = temp.getRight();
					for (int i = 1; i <= Location; i++) {
						if(i==Location&&temp2!=null)
						{
							letter=temp2.getLetter();
							break;
						}
						temp2 = temp2.getNext();
						
					}
				}
				temp=temp.getDown();
				
			}
		}
		return letter;
		
	}
	
	
	public void deleteParagraph(int Paragraph) {
		ParagraphNode temp;
		ParagraphNode previous;
		if(head==null)
		{
			System.out.println("error delete parag");
		}
		else {
			temp=head;
			previous=head;
			if(head.getParagraphNode()==Paragraph) {
				head=head.getDown();
				return;
			}
			else {
				temp=temp.getDown();
				while(temp!=null) {
					if(temp.getParagraphNode()==Paragraph) {
						previous.setDown(temp.getDown());
						return;
					}
					else {
						previous=temp;
						temp=temp.getDown();
					}
				}
				if(temp==null) {
					System.out.println("delete parag");
					return;
				}
			}
		}
	}

	public int sizeParagraph()
	{
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			ParagraphNode temp = head;
			while (temp != null)
			{
				count++;
				temp=temp.getDown();
			}
		}
		return count;   
	}

	public int sizeLetter(int Paragraph_number)
	{
		int count = 0;
		if (head == null)
			return 0;
		else {
			ParagraphNode temp = head;
			for (int i = 0; i < Paragraph_number-1; i++) {
				temp=temp.getDown();
			}
			LetterNode temp2 = temp.getRight();
			while (temp2 != null)
			{
				count++;
				temp2 = temp2.getNext();
			}
		}
		return count;   
	}

	public void display()
	{
		if (head == null)    
			System.out.println("linked list is empty");
		else {
			ParagraphNode temp = head;
			while (temp != null)
			{
				LetterNode temp2 = temp.getRight();
				while (temp2 != null)
				{
					System.out.print(temp2.getLetter());
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				System.out.println();
			}
		}
	}
	
	public void Save() throws IOException
	{
		PrintWriter outputStream=null;
		File f3 = new File((String) "Editor.txt");
	    f3.delete();
	    File f4 = new File((String) "Editor.txt");
	    f4.createNewFile();
	    outputStream = new PrintWriter(new FileWriter("Editor.txt"));
	    if (head == null)    
			System.out.println("linked list is empty");
		else {
			ParagraphNode temp = head;
			while (temp != null)
			{
				LetterNode temp2 = temp.getRight();
				while (temp2 != null)
				{
					String s=Character.toString(temp2.getLetter());
					outputStream.print(s);
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
				outputStream.println();
			}
		}
	    outputStream.close();
	}
}

