
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Scanner;

import enigma.console.TextAttributes;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

public class Editor {
	public enigma.console.Console cn = Enigma.getConsole("Ceng Editor", 90, 25, 20, 0);
	public enigma.console.TextWindow cnt = cn.getTextWindow();
	public static TextAttributes red = new TextAttributes(Color.red);
	public static TextAttributes blue = new TextAttributes(Color.blue);
	public static TextAttributes att0 = new TextAttributes(Color.white, Color.black);  //foreground, background color
	public TextMouseListener tmlis;
	public KeyListener klis;
	public int keypr;
	public int rkey;
	int cursorx=0;
	int cursory=1;
	int paragraph_number=1;
	public int rkeymod;      // key modifiers
	public int capslock=0;   // 0:off    1:on
	MLL CengEditor=new MLL();
	int [] coordinate = new int[100];
	int i=0;
	int paragraph_number_overwrite;
	
	
	Boolean Mod=true;
	Boolean Alignment;
	
	
	Editor() throws Exception {   // --- Contructor
	      cn.getTextWindow().addTextMouseListener(tmlis);
	    
	      klis=new KeyListener() {
	         public void keyTyped(KeyEvent e) {}
	         public void keyPressed(KeyEvent e) {
	            if(keypr==0) {
	               keypr=1;
	               rkey=e.getKeyCode();
	               rkeymod=e.getModifiersEx();
	               if(rkey == KeyEvent.VK_CAPS_LOCK) {
		                 if(capslock==0) capslock=1;
		                 else capslock=0;
	               }
	            }
	         }
	         public void keyReleased(KeyEvent e) {}
	      };
	      cn.getTextWindow().addKeyListener(klis);
	      // --------------------------------------------------------------------------
	      
	      int curtype;
	      curtype=cnt.getCursorType();   // default:2 (invisible)       0-1:visible
	      cnt.setCursorType(1);
	      cn.setTextAttributes(att0);
		
		Board.Print();
		cn.getTextWindow().setCursorPosition(64, 0);
		cn.getTextWindow().output("F1:Selection start");
		cn.getTextWindow().setCursorPosition(64, 1);
		cn.getTextWindow().output("F2:Selection end ");
		cn.getTextWindow().setCursorPosition(64, 2);
		cn.getTextWindow().output("F3:Cut");
		cn.getTextWindow().setCursorPosition(64, 3);
		cn.getTextWindow().output("F4:Copy");
		cn.getTextWindow().setCursorPosition(64, 4);
		cn.getTextWindow().output("F5:Paste");
		cn.getTextWindow().setCursorPosition(64, 5);
		cn.getTextWindow().output("F6:Find");
		cn.getTextWindow().setCursorPosition(64, 6);
		cn.getTextWindow().output("F7:Replace");
		cn.getTextWindow().setCursorPosition(64, 7);
		cn.getTextWindow().output("F8:Next");
		cn.getTextWindow().setCursorPosition(64, 8);
		cn.getTextWindow().output("F9:Align Left");
		cn.getTextWindow().setCursorPosition(64, 9);
		cn.getTextWindow().output("F10:Justify");
		cn.getTextWindow().setCursorPosition(64, 10);
		cn.getTextWindow().output("F11:Load");
		cn.getTextWindow().setCursorPosition(64, 11);
		cn.getTextWindow().output("F12:Save");
		cn.getTextWindow().setCursorPosition(64, 13);
		cn.getTextWindow().output("Mode:");
		cn.getTextWindow().setCursorPosition(64, 15);
		cn.getTextWindow().output("Alignment:");
		
		CengEditor.addParagraph(1);
		coordinate[i]=1;
		i++;
		boolean selection=false;
		int selection_x=-1;
		int selection_y=-1;
		String Selection=null;
		String Copy=null;
		while(true)
		{
			if(Mod)
			{
				cn.getTextWindow().setCursorPosition(71, 13);
				cn.getTextWindow().output("Insert   ",red);
			}
			else
			{
				cn.getTextWindow().setCursorPosition(71, 13);
				cn.getTextWindow().output("Overwrite",blue);
			}
			if(keypr==1)
			{
				if(rkey==KeyEvent.VK_INSERT)
				{
					if(Mod)
					{
						Mod=false;
					}
					else
					{
						Mod=true;
					}
				}
				if(rkey==KeyEvent.VK_LEFT)
					{
					cursorx--;
					
					if(cursorx==0)
					{
						cursorx=60;
						cursory--;
					}
					
					}
		        if(rkey==KeyEvent.VK_RIGHT)
		        	{
		        	cursorx++;
		        	
		        	if(cursorx==60)
					{
						cursorx=0;
						cursory++;
					}
		        	
		        	}
		        if(rkey==KeyEvent.VK_UP)
		        	{
		        	
		        	if(cursory!=1)
		        	{
		        		cursory--;
		        	}
		        	
		        	}
		        if(rkey==KeyEvent.VK_DOWN)
		        	{
		        	if(cursory!=20)
		        	{
		        		cursory++;
		        	}
		        	}
		        
		        if(rkey==KeyEvent.VK_ENTER)
		        {
		        	cursory++;
		        	cursorx=0;
		        	paragraph_number++;
		        	CengEditor.addParagraph(paragraph_number);
		        	coordinate[i]=cursory;
		        	i++;
		        }
		        if(rkey==KeyEvent.VK_SPACE)
		        {
		        	if(Mod)
		        	{
		        		cursorx++;
			        	cn.getTextWindow().setCursorPosition(cursorx, cursory);
		  				cn.getTextWindow().output(' ');
		  				CengEditor.addLetter(paragraph_number, ' ');
		  				if(cursorx==60)
						{
							cursorx=0;
							cursory++;
						}
		        	}
		        	else
		        	{
		        		
		        		int temp=0;
		        		int parag=0;
		        		for (int i = 0; i < coordinate.length-1; i++) {
							if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
							{
								temp=coordinate[i];
								parag=i+1;
								break;
							}
						}
		        		int coor=(cursory-temp)*60;
		        		coor=coor+cursorx+1;
		        		
		        		cursorx++;
			        	cn.getTextWindow().setCursorPosition(cursorx, cursory);
		  				cn.getTextWindow().output(' ');
		  				CengEditor.Overwrite(parag, coor, ' ');
		  				if(cursorx==60)
						{
							cursorx=0;
							cursory++;
						}
		        	}
		        	
		        }
				
				char rckey=(char)rkey;
				
	            //        left          right          up            down
	            if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') {    // test without using VK (Virtual Keycode)
	              cnt.setCursorPosition(cursorx, cursory);
	            }
	            else
	            {
	            	
	              if(rckey>='0' && rckey<='9')
	              {
	            	  if(Mod)
	            	  {
	            		  cursorx++;
		            	  cn.getTextWindow().setCursorPosition(cursorx, cursory);
		  				  cn.getTextWindow().output(rckey);
		  				  CengEditor.addLetter(paragraph_number, rckey);
		  				if(cursorx==60)
						{
							cursorx=0;
							cursory++;
						}
	            	  }
	            	  else
	            	  {
	            		  int temp=0;
			        		int parag=0;
			        		for (int i = 0; i < coordinate.length-1; i++) {
								if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
								{
									temp=coordinate[i];
									parag=i+1;
									break;
								}
							}
			        		int coor=(cursory-temp)*60;
			        		coor=coor+cursorx+1;
			        		
			        		cursorx++;
				        	cn.getTextWindow().setCursorPosition(cursorx, cursory);
			  				cn.getTextWindow().output(rckey);
			  				CengEditor.Overwrite(parag, coor, rckey);
			  				if(cursorx==60)
							{
								cursorx=0;
								cursory++;
							}
	            	  }
	              }
	              if((rckey>='A' && rckey<='Z')||(rckey>='a' && rckey<'z')) {
	            	  if(Mod)
	            	  {
	            		  if(capslock==1)
		            	  {
		            		  cursorx++;
		            		  cn.getTextWindow().setCursorPosition(cursorx, cursory);
			    			  cn.getTextWindow().output(rckey);
			    			  CengEditor.addLetter(paragraph_number, rckey);
			    			  if(cursorx==60)
			  				{
			  					cursorx=0;
			  					cursory++;
			  				}
		            	  }
		            	  else
		            	  {
		            		  if(rkey==KeyEvent.VK_F1||rkey==KeyEvent.VK_F2||rkey==KeyEvent.VK_F3||rkey==KeyEvent.VK_F4||rkey==KeyEvent.VK_F5)
		            		  {
		            			  cursorx++;
			            		  cn.getTextWindow().setCursorPosition(cursorx, cursory);
				    			  cn.getTextWindow().output((char)(rckey+32));
				    			  CengEditor.addLetter(paragraph_number, (char)(rckey+32));
				    			  if(cursorx==60)
				  				{
				  					cursorx=0;
				  					cursory++;
				  				}
			            	  }
		            		  }
		            		  
	            	  }
	            	  else
	            	  {
	            		  int temp=0;
			        		int parag=0;
			        		for (int i = 0; i < coordinate.length-1; i++) {
								if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
								{
									temp=coordinate[i];
									parag=i+1;
									break;
								}
							}
			        		int coor=(cursory-temp)*60;
			        		coor=coor+cursorx+1;
			        		
			        		cursorx++;
			        		if(capslock==1)
			        		{
			        			cn.getTextWindow().setCursorPosition(cursorx, cursory);
				  				cn.getTextWindow().output(rckey);
				  				CengEditor.Overwrite(parag, coor, rckey);
			        		}
			        		else
			        		{
			        			cn.getTextWindow().setCursorPosition(cursorx, cursory);
				    			cn.getTextWindow().output((char)(rckey+32));
				    			CengEditor.Overwrite(parag,coor, (char)(rckey+32));
			        		}
				        	
			  				if(cursorx==60)
							{
								cursorx=0;
								cursory++;
							}
	            	  }
	            	  
	            	  
	              }
	              if((rkeymod & KeyEvent.SHIFT_DOWN_MASK) == 0) {
	            	  if(Mod)
	            	  {
	            		  if(rckey=='.' || rckey==',' || rckey==':'|| rckey==';'|| rckey=='!'|| rckey=='?'|| rckey=='-'|| rckey=='*'|| rckey=='+') 
	  	                {
	  	                	cursorx++;
	  	                	cn.getTextWindow().setCursorPosition(cursorx, cursory);
	  	    				cn.getTextWindow().output(rckey);
	  	    				CengEditor.addLetter(paragraph_number, rckey);
	  	    				if(cursorx==60)
	  	    				{
	  	    					cursorx=0;
	  	    					cursory++;
	  	    				}
	  	    				
	  	                }
	            	  }
	            	  else
	            	  {
	            		  if(rckey=='.' || rckey==',' || rckey==':'|| rckey==';'|| rckey=='!'|| rckey=='?'|| rckey=='-'|| rckey=='*'|| rckey=='+') 
	            		  {
	            			  int temp=0;
				        		int parag=0;
				        		for (int i = 0; i < coordinate.length-1; i++) {
									if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
									{
										temp=coordinate[i];
										parag=i+1;
										break;
									}
								}
				        		int coor=(cursory-temp)*60;
				        		coor=coor+cursorx+1;
				        		
				        		cursorx++;
					        	cn.getTextWindow().setCursorPosition(cursorx, cursory);
				  				cn.getTextWindow().output(rckey);
				  				CengEditor.Overwrite(parag, coor, rckey);
				  				if(cursorx==60)
								{
									cursorx=0;
									cursory++;
								}
	            		  }
	            		  
	            	  }
	                
	              }
	              
	            }
	            if(rkey==KeyEvent.VK_BACK_SPACE)
	            {
	            	cn.getTextWindow().setCursorPosition(10, 23);
	    			cn.getTextWindow().output("                               ");
	            	if(cursorx==0)
	            	{
	            		cn.getTextWindow().setCursorPosition(10, 23);
		    			cn.getTextWindow().output("You must enter a letter first",red);
	            	}
	            	if(cursorx==1&&CengEditor.sizeLetter(paragraph_number)>1)
	            	{
	            		cn.getTextWindow().setCursorPosition(cursorx, cursory);
		    			cn.getTextWindow().output(' ');
		    			CengEditor.DeleteLetter(paragraph_number, CengEditor.sizeLetter(paragraph_number));
		    			cursory--;
		    			if(CengEditor.sizeLetter(paragraph_number)%60==0&&CengEditor.sizeLetter(paragraph_number)!=0)
		    				cursorx=60;
		    			else
		    				cursorx=CengEditor.sizeLetter(paragraph_number)%60;
	            	}
	            	else if(cursorx==1)
	            	{
	            		cn.getTextWindow().setCursorPosition(cursorx, cursory);
		    			cn.getTextWindow().output(' ');
		    			CengEditor.deleteParagraph(paragraph_number);
		    			paragraph_number--;
		    			cursory--;
		    			if(CengEditor.sizeLetter(paragraph_number)%60==0&&CengEditor.sizeLetter(paragraph_number)!=0)
		    				cursorx=60;
		    			else
		    				cursorx=CengEditor.sizeLetter(paragraph_number)%60;
	            	}
	            	else
	            	{
	            		cn.getTextWindow().setCursorPosition(cursorx, cursory);
		    			cn.getTextWindow().output(' ');
		    			CengEditor.DeleteLetter(paragraph_number, CengEditor.sizeLetter(paragraph_number));
		    			cursorx--;
	            	}
	            }
	            if(rkey==KeyEvent.VK_F11)//LOAD
	            {
	            	String input = "Editor.txt";
	            	File file = new File(input);
		            Scanner sc = new Scanner(file);
		            
		            while(sc.hasNextLine())
		            {
		                String line = sc.nextLine();
		                if(paragraph_number!=1)
		                	CengEditor.addParagraph(paragraph_number);
		                for (int i = 0; i < line.length(); i++) {
							char letter=line.charAt(i);
							cursorx++;
		                	cn.getTextWindow().setCursorPosition(cursorx, cursory);
		    				cn.getTextWindow().output(letter);
		    				CengEditor.addLetter(paragraph_number, letter);
		    				if(cursorx==60)
		    				{
		    					cursorx=0;
		    					cursory++;
		    				}
						}
		                if(sc.hasNextLine()==true)
		                {
		                	paragraph_number++;
			                cursorx=0;
			                cursory++;
		                }
		            }
	            }
	            if(rkey==KeyEvent.VK_F12)//SAVE
	            {
	            	 CengEditor.Save();
	            }
	            if(rkey==KeyEvent.VK_HOME)
	            {
	            	int temp=0;
	        		int parag=0;
	        		for (int i = 0; i < coordinate.length-1; i++) {
						if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
						{
							temp=coordinate[i];
							parag=i+1;
							break;
						}
					}
	        		cursory=temp;
	        		cursorx=0;
	            }
	            if(rkey==KeyEvent.VK_END)
	            {
	            	int temp=0;
	        		int parag=0;
	        		for (int i = 0; i < coordinate.length-1; i++) {
						if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
						{
							temp=coordinate[i];
							parag=i+1;
							break;
						}
					}
	        		int coor=CengEditor.sizeLetter(parag);
	        		while(coor>60)
	        		{
	        			coor=coor-60;
	        			temp++;
	        		}
	        		if(coor==60)
	        		{
	        			coor=0;
	        			temp++;
	        		}
	        		
	        		cursory=temp;
	        		
	        		cursorx=coor;
	            }
	            if(rkey==KeyEvent.VK_F1)
	            {
	            	selection=true;
	            	
	            	selection_x=cursorx;
	            	selection_y=cursory;
	            	Selection=null;
	            }
	            if(rkey==KeyEvent.VK_F2 && selection==true)
	            {
	            	int temp=0;
	        		int parag=0;
	        		for (int i = 0; i < coordinate.length-1; i++) {
						if((coordinate[i+1]==0 || selection_y<coordinate[i+1])&&selection_y>=coordinate[i])
						{
							temp=coordinate[i];
							parag=i+1;
							break;
						}
					}
	        		int coor=(selection_y-temp)*60;
	        		coor=coor+selection_x+1;
	        		
	        		/////////////////////////////////////////////////////////////////////////////////////////////////////
	        		
	        		int temp1=0;
	        		int parag1=0;
	        		for (int i = 0; i < coordinate.length-1; i++) {
						if((coordinate[i+1]==0 || cursory<coordinate[i+1])&&cursory>=coordinate[i])
						{
							temp1=coordinate[i];
							parag1=i+1;
							break;
						}
					}
	        		int coor1=(cursory-temp1)*60;
	        		coor1=coor1+cursorx+1;
	        		
	        		if(parag1==parag)
	        		{
	        			for (int i = coor; i <= coor1; i++)
	        			{
							Selection=Selection+Character.toString(CengEditor.Selection(parag1, i));
						}
	        		}
	        		else
	        		{
	        			for (int i = coor; i <= CengEditor.sizeLetter(parag); i++) 
        				{
        					Selection=Selection+Character.toString(CengEditor.Selection(parag1, i));
						}
	        			parag++;
	        			int paragtemp=parag;
	        			for (int i = parag; i <= parag1; i++)
	        			{
							if(i==parag1)
							{
								for (int j = 1; j <= coor1; j++) {
									Selection=Selection+Character.toString(CengEditor.Selection(parag1, j));
								}
							}
							else
							{
								for (int j = 1; j < CengEditor.sizeLetter(paragtemp); j++) {
									Selection=Selection+Character.toString(CengEditor.Selection(paragtemp, j));
								}
								paragtemp++;
							}
						}
	        		}
	        		
            	    selection=false;
	            }
	            if(rkey==KeyEvent.VK_F3)//CUT
	            {
	            	for (int i = selection_x; i <= cursorx; i++) 
	            	{
	            		cn.getTextWindow().setCursorPosition(i, selection_y);
		  				cn.getTextWindow().output(' ');
				    }
	            	Copy=Selection;
	            }
	            if(rkey==KeyEvent.VK_F4)//COPY
	            {
	            	Copy =Selection;
	            }
	            if(rkey==KeyEvent.VK_F5)//PASTE
	            {
	            	for (int i = 0; i < Copy.length(); i++) {
	            		cursorx++;
	                	cn.getTextWindow().setCursorPosition(cursorx, cursory);
	    				cn.getTextWindow().output(Copy.charAt(i));
	    				CengEditor.addLetter(paragraph_number, Copy.charAt(i));
	    				if(cursorx==60)
	    				{
	    					cursorx=0;
	    					cursory++;
	    				}
					}
	            	
	            }
	            keypr=0;
	         }
			Thread.sleep(50);
		}
}
}