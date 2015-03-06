import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.Point;

import processing.core.*;

public class StackGUI extends PApplet 
{
	private boolean dragging = false;
	int mx = 0, my = 0;
	PImage bg;
	PImage close;
	PImage close_i;
	PImage arrow;
	PImage convertB;
	PImage popB;
	PImage convertB_i;
	PImage popB_i;
	
	PFont font;
	GUIstackNode[] nodes = new GUIstackNode[10];
	int stackSize = 0;
	int closeX = 361, closeY = 5;
	int convertX = 312, convertY = 51;
	int popX = 319, popY = 99;
	String input = "";
	String typing = "";
	int index;
	
	LinkedStack<String> l1;
	/**
	 * program insertion point
	 */
	public void init()
	{
		if (frame != null)
		{
			frame.removeNotify();// make the frame not displayable
			frame.setResizable(false);
			frame.setUndecorated(true); //remove frame borders
			frame.addNotify();
		}
		super.init(); //initialize the window and begin the program
	}
	/**
	 * ensures the program can run as a java application
	 * @param args
	 */
	public static void main(String args[])
	{
		PApplet.main(new String[] { "StackGUI" });
	}
	/**
	 * sets up all variables including the stack
	 */
	public void setup()
	{
		size(400, 800, P2D); //make a 400x800 window with OpenGL
		bg = loadImage("../Data/bg.png");
		close = loadImage("../Data/close.png");
		close_i = loadImage("../Data/close_i.png");
		arrow = loadImage("../Data/arrow.png");
		convertB = loadImage("../Data/convert.png");
		popB = loadImage("../Data/pop.png");
		convertB_i = loadImage("../Data/convert_i.png");
		popB_i = loadImage("../Data/pop_i.png");
		font = createFont("../Data/arialbd.ttf", 24);
		textFont(font, 24);
		
		l1 = new LinkedStack<String>(this);
		l1.push("20");
		
		CreateVisualStack(l1.toString());
		
		InfixToPostfix converter = new InfixToPostfix();
		converter.convert("( 6 + 20 )* 5 - 12 / 4)");

	}
	/**
	 * the infinite loop used to execute all commands in the gui while running
	 */
	public void draw()
	{
		image(bg, 0, 0);
		image(close, closeX, closeY);
		image(convertB, convertX, convertY);
		image(popB, popX, popY);
		
		if (mouseX > 360 && mouseX < 360+close.width && mouseY > 5 && mouseY < 5+close.height)
		{
			image(close_i, closeX, closeY);
		}
		if (mouseX > convertX && mouseX < convertX+convertB.width && mouseY > convertY && mouseY < convertY+convertB.height)
		{
			image(convertB_i, convertX, convertY);
		}
		if (mouseX > popX && mouseX < popX+popB.width && mouseY > popY && mouseY < popY+popB.height)
		{
			image(popB_i, popX, popY);
		}
		DrawVisualStack();
		textAlign(RIGHT);
		text(typing,298,80);
	}
	
	/**
	 * creates a linked list of stack node objects that can be drawn to the screen
	 * @param stack: a string representation of the stack
	 */
	public void CreateVisualStack(String stack)
	{
		//parse the stack string to create the nodes
		String[] s_nodes = stack.split("\n");
		stackSize = s_nodes.length;
		index = 0;
		//create top node
		nodes[9] = new GUIstackNode(this, "top");
		//create nodes
		for (int i = stackSize-1; i >= 0; i--)
		{
			nodes[index] = new GUIstackNode(this, index, s_nodes[i]);
			index++;
		}
	}
	
	public void UpdateVisualStack()
	{
		String[] s_nodes = l1.toString().split("\n");
		stackSize = s_nodes.length;
		nodes[index] = new GUIstackNode(this, index, s_nodes[0]); //add the element on top of stack
		index++;
	}
	/**
	 * calls the display function of every stack node
	 */
	public void DrawVisualStack()
	{
		nodes[9].display();
		for (int i = 0; i < stackSize; i++)
		{
			nodes[i].display();
		}
	}
	/**
	 * called every time a key is pressed
	 */
	public void keyPressed() 
	{
		  // If the return key is pressed, save the String and clear it
		  if (key == '\n' ) 
		  {
			  inputText();
		  } 
		  else if (key == BACKSPACE)
		  {
			  if (typing.length() > 0)
				  typing = typing.substring(0, typing.length()-1);
		  }
		  else 
		  {
			  if (keyCode != SHIFT)
				  typing += key; 
		  }
	}
		  
	public void mouseClicked()
	{
		if (mouseX > 360 && mouseX < 360+close.width && mouseY > 5 && mouseY < 5+close.height)
		{
			exit();
		}
		if (mouseX > convertX && mouseX < convertX+convertB.width && mouseY > convertY && mouseY < convertY+convertB.height)
		{
			inputText();
		}
		if (mouseX > popX && mouseX < popX+popB.width && mouseY > popY && mouseY < popY+popB.height)
		{
			//pop the stack and update
		}
	}
	
	public void mousePressed()
	{
		mx = mouseX;
		my = mouseY;
		if (mouseX > 360 && mouseX < 360+close.width && mouseY > 5 && mouseY < 5+close.height)
		{
			closeX = 362;
			closeY = 6;
		}
		if (mouseX > convertX && mouseX < convertX+convertB.width && mouseY > convertY && mouseY < convertY+convertB.height)
		{
			convertX = 313;
			convertY = 52;
			
		}
		if (mouseX > popX && mouseX < popX+popB.width && mouseY > popY && mouseY < popY+popB.height)
		{
			popX = 320;
			popY = 100;
		}
		
	}
	
	public void mouseReleased()
	{
		dragging = false;
		closeX = 361;
		closeY = 5;
		convertX = 312;
		convertY = 51;
		popX = 319;
		popY = 99;
	}
	
	public void mouseDragged()
	{
		Point point = frame.getLocation();
		Dimension dimension = frame.getSize();
		frame.setLocation(point.x + (mouseX - mx), point.y + (mouseY - my));
		dragging = true;
	}
	
	/**
	 * draws a node in the stack visualizer gui
	 * @param contents of the node
	 */
	public void drawNode(String contents)
	{
		fill(2,181,230);
		noStroke();
		rect(75,150, 100,100);
		fill(255);
		text(contents, 100, 200);
	}
	/**
	 * takes the input and passes it on to the stack updater
	 */
	public void inputText()
	{
		//make sure there is text
		if (typing.length() > 0)
		{
			input = typing;
			l1.push(input);
			input = ""; 
			typing = "";
			UpdateVisualStack();
		}
	}
	public StackGUI()
	{
		// TODO Auto-generated constructor stub
	}

}
