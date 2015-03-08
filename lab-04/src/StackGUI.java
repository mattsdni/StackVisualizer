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
	PImage nextB;
	PImage convertB_i;
	PImage nextB_i;
	
	PFont font;
	GUIstackNode[] nodes = new GUIstackNode[10];
	String [][] nodeStatusArray;
	int stackSize = 0;
	int closeX = 361, closeY = 5;
	int convertX = 312, convertY = 51;
	int nextX = 319, nextY = 99;
	String input = "";
	String typing = "";
	int index;
	int step = 0; //which step in the stack conversion are we on
	boolean stackExists = false;
	boolean showResult = false;
	String result = ""; //result of the conversion
	
	LinkedStack<String> l1;
	InfixToPostfix converter;
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
		nextB = loadImage("../Data/pop.png");
		convertB_i = loadImage("../Data/convert_i.png");
		nextB_i = loadImage("../Data/pop_i.png");
		font = createFont("../Data/arialbd.ttf", 24);
		textFont(font, 24);
		
		converter = new InfixToPostfix();
	}
	/**
	 * the infinite loop used to execute all commands in the gui while running
	 */
	public void draw()
	{
		image(bg, 0, 0);
		image(close, closeX, closeY);
		image(convertB, convertX, convertY);
		image(nextB, nextX, nextY);
		
		checkMouse();
		DrawVisualStack();
		textAlign(RIGHT);
		text(typing,298,80);
		if (showResult)
		{
			textAlign(LEFT);
			text("Postfix: " + result, 10, 120);
		}
	}
	
	/**
	 * creates a linked list of stack node objects that can be drawn to the screen
	 * @param stack: a string representation of the stack
	 */
	public void CreateVisualStack()
	{
		index = 0;
		//create top node
		nodes[9] = new GUIstackNode(this, "top");
		stackExists = true;
	}
	
	/**
	 * updates the nodes array to match current stack state
	 */
	public void UpdateVisualStack()
	{
		//erase gui nodes
		for (int i = 0; i < 9; i++)
			nodes[i] = null;
		//add current state elements to gui stack
		for(int i = nodeStatusArray[step].length-1; i >= 0; i--)
			nodes[i] = new GUIstackNode(this, i, nodeStatusArray[step][nodeStatusArray[step].length-1-i]); 
		stackSize = nodeStatusArray[step].length;
		step++;
		if (step >= nodeStatusArray.length)
		{
			stackSize = 0;
			showResult = true;
		}
	}
	
	/**
	 * calls the display function of every stack node
	 */
	public void DrawVisualStack()
	{
		if(stackExists)
		{
			if (step < nodeStatusArray.length)
			{
				nodes[9].display();
			}
			for (int i = 0; i < stackSize; i++)
			{
				nodes[i].display();
			}
		}
	}
	
	/**
	 * initializes a 2d string array containing all states of the stack
	 */
	public void initStackState()
	{
		String[] s_nodes = converter.stackTrace;
		int sNodeSize = 0;
		//find length of array not including null values
		for (int i = 0; i < s_nodes.length; i++)
		{
			if (s_nodes[i] != null)
				sNodeSize++;
		}
		//copy non null values into new array
		String[] sArray = new String[sNodeSize];
		for (int i = 0; i <sNodeSize; i++)
		{
			sArray[i] = s_nodes[i];
		}
		nodeStatusArray = new String[sArray.length][]; //global 2d string array to hold stack states
		
		for (int i = 0; i < sArray.length; i++)
		{
			nodeStatusArray[i] = sArray[i].split("\n");
		}
	}
	/**
	 * checks whether the mouse is over a button
	 */
	public void checkMouse()
	{
		if (mouseX > 360 && mouseX < 360+close.width && mouseY > 5 && mouseY < 5+close.height)
		{
			image(close_i, closeX, closeY);
		}
		if (mouseX > convertX && mouseX < convertX+convertB.width && mouseY > convertY && mouseY < convertY+convertB.height)
		{
			image(convertB_i, convertX, convertY);
		}
		if (mouseX > nextX && mouseX < nextX+nextB.width && mouseY > nextY && mouseY < nextY+nextB.height)
		{
			image(nextB_i, nextX, nextY);
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
			  if (input.length() == 0)
			  {
				  inputText();
				  CreateVisualStack();
			  }
			  else
				  if (step < nodeStatusArray.length)
					{
						initStackState();
						UpdateVisualStack();
					}
				  
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
			if (step < nodeStatusArray.length)
			{
				inputText();
				CreateVisualStack();
			}
		}
		if (mouseX > nextX && mouseX < nextX+nextB.width && mouseY > nextY && mouseY < nextY+nextB.height)
		{
			//go to the next step in the stack process if there is one
			if (step < nodeStatusArray.length)
			{
				initStackState();
				UpdateVisualStack();
			}
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
		if (mouseX > nextX && mouseX < nextX+nextB.width && mouseY > nextY && mouseY < nextY+nextB.height)
		{
			nextX = 320;
			nextY = 100;
		}
		
	}
	
	public void mouseReleased()
	{
		dragging = false;
		closeX = 361;
		closeY = 5;
		convertX = 312;
		convertY = 51;
		nextX = 319;
		nextY = 99;
	}
	
	public void mouseDragged()
	{
		Point point = frame.getLocation();
		Dimension dimension = frame.getSize();
		frame.setLocation(point.x + (mouseX - mx), point.y + (mouseY - my));
		dragging = true;
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
			result = converter.convert(input);
			initStackState();
			UpdateVisualStack();
		}
	}
	public StackGUI()
	{
	}
}
