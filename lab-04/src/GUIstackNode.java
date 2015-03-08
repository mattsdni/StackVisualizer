import processing.core.PApplet;
import processing.core.PConstants;

/**
 * An object that represents a node in a stack
 * @author Matt
 */
public class GUIstackNode
{
	PApplet parent;
	int x, y;
	String contents;
	int sizeX, sizeY;

	/**
	 * creates a regular node
	 * @param p: a reference to the PApplet that the node will be drawn onto
	 * @param _y: used to calculate the screen position of the node
	 * @param _contents: the contents of the node. will be drawn on the node
	 */
	GUIstackNode(PApplet p, int _y, String _contents)
	{
		parent = p;
		x = 100;
		y = parent.height - (_y*120) - 150;
		sizeX = 100;
		sizeY = 80;
		contents = _contents;
	}
	/**
	 * creates the top node
	 * @param p: a reference to the PApplet that the node will be drawn onto
	 * @param _contents: the contents of the node. will be drawn on the node
	 */
	GUIstackNode(PApplet p, String _contents)
	{
		parent = p;
		x = parent.width - 100;
		y = parent.height - 121;
		sizeX = 50;
		sizeY = 50;
		contents = _contents;
	}
	
	void display()
	{
		parent.fill(2,181,230);
		parent.noStroke();
		parent.rect(x, y, sizeX, sizeY);
		parent.fill(255);
		parent.textAlign(PConstants.CENTER);
		parent.text(contents, x+sizeX/2, (int)(y+sizeY/1.5));
	}
}
